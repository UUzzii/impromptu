package com.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MinIO工具类
 * 提供文件上传、下载、预览等功能
 */
@Component
@PropertySource("classpath:minio.properties")
public class MinioUtil {

    /**
     * MinIO服务地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * MinIO访问密钥
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * MinIO密钥
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 默认存储桶
     */
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * MinIO客户端
     */
    private MinioClient minioClient;

    /**
     * 初始化MinIO客户端
     */
    @PostConstruct
    public void init() {
        try {
            minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
            // 检查存储桶是否存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                // 创建存储桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化MinIO客户端失败", e);
        }
    }

    /**
     * 上传文件
     *
     * @param inputStream   文件输入流
     * @param originalFilename 原始文件名
     * @param contentType   内容类型
     * @return 文件访问路径
     */
    public String uploadFile(InputStream inputStream, String originalFilename, String contentType) {
        return this.uploadFile(bucketName, inputStream, originalFilename, contentType);
    }

    /**
     * 上传文件到指定存储桶
     *
     * @param bucketName    存储桶名称
     * @param inputStream   文件输入流
     * @param originalFilename 原始文件名
     * @param contentType   内容类型
     * @return 文件访问路径
     */
    public String uploadFile(String bucketName, InputStream inputStream, String originalFilename, String contentType) {
        try {
            // 生成文件名
            String fileName = this.generateFileName(originalFilename);
            
            // 获取文件大小
            long size = inputStream.available();
            
            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .contentType(contentType)
                            .stream(inputStream, size, -1)
                            .build());
            
            return "/" + bucketName + fileName;
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败", e);
        }
    }

    /**
     * 生成文件名
     *
     * @param originalFilename 原始文件名
     * @return 新文件名
     */
    private String generateFileName(String originalFilename) {
        // 获取文件后缀
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 获取当前日期作为目录,使用UUID生成文件名
        return "/" +
                DateUtil.date().toDateStr() + "/" +
                UUID.randomUUID().toString().replaceAll("-", "") +
                suffix;
    }

    /**
     * 下载文件
     *
     * @param fileName     文件名
     * @param outputStream 输出流
     */
    public void downloadFile(String fileName, OutputStream outputStream) {
        this.downloadFile(bucketName, fileName, outputStream);
    }

    /**
     * 从指定存储桶下载文件
     *
     * @param bucketName   存储桶名称
     * @param fileName     文件名
     * @param outputStream 输出流
     */
    public void downloadFile(String bucketName, String fileName, OutputStream outputStream) {
        try (InputStream inputStream = minioClient.getObject(GetObjectArgs
                .builder()
                .bucket(bucketName)
                .object(fileName)
                .build())) {
            // 写入输出流
            IoUtil.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException("下载文件失败", e);
        }
    }

    /**
     * 获取文件预览URL
     *
     * @param fileName 文件名
     * @return 预览URL
     */
    public String getPreviewUrl(String fileName) {
        return getPreviewUrl(bucketName, fileName);
    }

    /**
     * 获取指定存储桶中文件的预览URL
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名
     * @return 预览URL
     */
    public String getPreviewUrl(String bucketName, String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .method(Method.GET)
                            .expiry(7, TimeUnit.DAYS) // URL有效期7天
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("获取文件预览URL失败", e);
        }
    }

    /**
     * 获取文件外链（永久）
     *
     * @param fileName 文件名
     * @return 文件外链
     */
    public String getFileUrl(String fileName) {
        return getFileUrl(bucketName, fileName);
    }

    /**
     * 获取指定存储桶中文件的外链（永久）
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名
     * @return 文件外链
     */
    public String getFileUrl(String bucketName, String fileName) {
        return endpoint + "/" + bucketName + "/" + fileName;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    public void deleteFile(String fileName) {
        deleteFile(bucketName, fileName);
    }

    /**
     * 从指定存储桶删除文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名
     */
    public void deleteFile(String bucketName, String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败", e);
        }
    }

    /**
     * 获取文件列表
     *
     * @return 文件信息列表
     */
    public List<Map<String, Object>> listFiles() {
        return listFiles(bucketName);
    }

    /**
     * 获取指定存储桶中的文件列表
     *
     * @param bucketName 存储桶名称
     * @return 文件信息列表
     */
    public List<Map<String, Object>> listFiles(String bucketName) {
        List<Map<String, Object>> fileList = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .build());
            
            for (Result<Item> result : results) {
                Item item = result.get();
                Map<String, Object> fileInfo = new HashMap<>(4);
                fileInfo.put("fileName", item.objectName());
                fileInfo.put("size", item.size());
                fileInfo.put("lastModified", item.lastModified());
                fileInfo.put("url", getFileUrl(bucketName, item.objectName()));
                fileList.add(fileInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException("获取文件列表失败", e);
        }
        return fileList;
    }

    /**
     * 检查文件是否存在
     *
     * @param fileName 文件名
     * @return 是否存在
     */
    public boolean isFileExist(String fileName) {
        return isFileExist(bucketName, fileName);
    }

    /**
     * 检查指定存储桶中的文件是否存在
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名
     * @return 是否存在
     */
    public boolean isFileExist(String bucketName, String fileName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 