package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @描述 用户原子处理器，所有与用户相关的原子操作都在此处理器中执行
 **/
@Component
public class UserProcessor {
    @Autowired
    MongoTemplate mongoTemplate;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /** 根据用户名从数据库中获取用户 */
    public User getUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        return mongoTemplate.findOne(query, User.class);
    }
    /* 创建根据用户名创建新用户 */
    public void createUserByUsername(String username, String password) throws Exception {
        String encoded_password = passwordEncoder.encode(password);
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        if(mongoTemplate.findOne(query, User.class) != null)
            throw new CourseWarn(UserWarnEnum.USERNAME_DOUBLED);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User new_user = new User();
        new_user.setUsername(username);
        new_user.setPassword(encoded_password);
//        User.Avatar avatar = new User.Avatar("default",
//                "default", 100, "0",
//                "http://42.193.117.251:80/2021-06-22/8191E0A6-F400-4E53-A6E3-6AAB3AC19A7A.jpeg");
//        List<User.Avatar> tmp = new ArrayList<>();
//        tmp.add(avatar);
//        new_user.setAvatars(tmp.toArray(new User.Avatar[tmp.size()]));
        new_user.setTime(simpleDateFormat.format(new Date()));
        mongoTemplate.insert(new_user);
    }
}
