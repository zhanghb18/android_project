package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.UserParams.Out.UserInfoOutParams;
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

    /** 根据邮箱从数据库中获取用户 */
    public User getUserByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    /** 创建新用户 */
    public void createUser(String email, String userID, String nickname, String password) throws Exception {
        System.out.println("createUser");
        String encoded_password = passwordEncoder.encode(password);
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERID).is(userID)); // userID查重
        if(mongoTemplate.findOne(query, User.class) != null)
            throw new CourseWarn(UserWarnEnum.USERID_DOUBLED);
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email)); // email查重
        if(mongoTemplate.findOne(query, User.class) != null)
            throw new CourseWarn(UserWarnEnum.EMAIL_DOUBLED);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User new_user = new User(email, userID, nickname, encoded_password);
//        User.Avatar avatar = new User.Avatar("default",
//                "default", 100, "0",
//                "http://42.193.117.251:80/2021-06-22/8191E0A6-F400-4E53-A6E3-6AAB3AC19A7A.jpeg");
//        List<User.Avatar> tmp = new ArrayList<>();
//        tmp.add(avatar);
//        new_user.setAvatars(tmp.toArray(new User.Avatar[tmp.size()]));
        new_user.setTime(simpleDateFormat.format(new Date()));
        mongoTemplate.insert(new_user);
    }

    /** 通过邮箱获取用户基本信息 */
    public UserInfoOutParams getUserInfo(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        String res = user.infoString();
        String avatar = "";
        return new UserInfoOutParams(res, avatar);
    }
}
