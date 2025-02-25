package com.impromptu.admin.controller;

import com.common.result.ResultVO;
import com.common.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件控制器
 * 提供文件上传、下载、预览等接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinioUtil minioUtil;


    /**
     * 上传单个文件
     *
     * @param file 文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    public ResultVO<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResultVO.success(minioUtil.uploadFile(file.getInputStream(), file.getOriginalFilename(), file.getContentType()));
    }

    /**
     * 上传多个文件
     *
     * @param files 文件列表
     * @return 上传结果
     */
    @PostMapping("/batch/upload")
    public ResultVO<?> batchUpload(@RequestParam("files") MultipartFile[] files) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            // 上传文件
            fileNames.add(minioUtil.uploadFile(file.getInputStream(), file.getOriginalFilename(), file.getContentType()));
        }
        return ResultVO.success(fileNames);
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param response HTTP响应
     */
    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) {
        try {
            minioUtil.downloadFile(fileName, response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败：" + e.getMessage());
        }
    }

    /**
     * 获取文件预览URL
     *
     * @param fileName 文件名
     * @return 预览URL
     */
    @GetMapping("/preview")
    public ResultVO<?> preview(String fileName) {
        return ResultVO.success(minioUtil.getPreviewUrl(fileName));
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @return 删除结果
     */
    @GetMapping("/delete")
    public ResultVO<Void> delete(String fileName) {
        minioUtil.deleteFile(fileName);
        return ResultVO.success();
    }
}