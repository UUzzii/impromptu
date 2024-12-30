package com.impromptu.outdoor;

import cn.hutool.crypto.SecureUtil;
import com.impromptu.outdoor.dao.UserDao;
import com.impromptu.outdoor.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author 石鹏
 * @date 2024/12/30 9:55
 */
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private UserDao userDao;

    @Test
    void a() {
        User user = new User();
        user.setAccount("sphs");
        user.setPassword(SecureUtil.md5("sphs"));
        user.setPhone("15111685573");
        user.setName("石鹏");
        user.setSex(1);
        user.setAvatar("https://img2.baidu.com/it/u=1310029438,409566289&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1541");
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
//        userDao.insertOrUpdate(user);

        List<User> userList = userDao.selectList(null);
        System.out.println(userList);
    }
}
