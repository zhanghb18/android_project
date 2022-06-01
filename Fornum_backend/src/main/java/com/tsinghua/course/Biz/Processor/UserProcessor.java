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
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.jws.soap.SOAPBinding;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        email = email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        return mongoTemplate.findOne(query, User.class);
    }
    public String getAvatarByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
//        return mongoTemplate.findOne(query, User.class).getAvatar_url();
        return "";
    }

    /** 创建新用户 */
    public void createUser(String email, String userID, String nickname, String password) throws Exception {
        System.out.println("createUser");
        String encoded_password = passwordEncoder.encode(password);
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERID).is(userID)); // userID查重
        if(mongoTemplate.findOne(query, User.class) != null)
            throw new CourseWarn(UserWarnEnum.USERID_DOUBLED);
        email = email.replace("@", "%40");
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email)); // email查重
        if(mongoTemplate.findOne(query, User.class) != null)
            throw new CourseWarn(UserWarnEnum.EMAIL_DOUBLED);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String aboutMe = "这个人很懒，什么也没有留下";
        User new_user = new User(email, userID, nickname, encoded_password, aboutMe);
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
        User user = getUserByEmail(email);
        String res = user.infoString();
        String avatar = "";
        return new UserInfoOutParams(res, avatar);
    }

    /** 用户修改密码 */
    public void modifyPassword(String email, String password) {
        email = email.replace("@", "%40");
        User user = getUserByEmail(email);
        String encoded_pw = passwordEncoder.encode(password);
        user.setPassword(encoded_pw);

        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        Update update = new Update();
        update.set("password", encoded_pw);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    /** 用户修改基本信息 */
    public void modifyInfo(String email, String userID, String nickname, String aboutMe) throws Exception {
        // userID查重
        email = email.replace("@", "%40");
        Query query0 = new Query();
        query0.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        if(mongoTemplate.findOne(query0, User.class).getUserID() != userID) {
            Query query1 = new Query();
            query1.addCriteria(Criteria.where(KeyConstant.USERID).is(userID));
            if (mongoTemplate.findOne(query1, User.class) != null)
                throw new CourseWarn(UserWarnEnum.USERID_DOUBLED);
        }


        // update
        User user = getUserByEmail(email);
        user.setUserID(userID);
        user.setNickname(nickname);
        user.setAboutMe(aboutMe);

        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        Update update = new Update();
        update.set("userID", userID);
        update.set("nickname", nickname);
        update.set("aboutMe", aboutMe);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    /** 用户新增关注 */
    public void addStar(String email, String star_email) throws Exception {
        email = email.replace("@", "%40");
        star_email = star_email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email).and("star.email").is(star_email));
        if(mongoTemplate.findOne(query, User.class) != null) {
            throw new CourseWarn(UserWarnEnum.STAR_FAILED);
        }
        Query query1 = new Query();
        query1.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        Query query2 = new Query();
        query2.addCriteria(Criteria.where(KeyConstant.EMAIL).is(star_email));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String friend_avatar = getAvatarByEmail(star_email);
        User.Stars star = new User.Stars(star_email, simpleDateFormat.format(new Date()), friend_avatar);
        Update update = new Update();
        update.push("star", star);
        mongoTemplate.updateFirst(query1, update, User.class);

        // 对关注的用户取消屏蔽
        if (isBlock(email, star_email) == true) {
            cancelBlock(email, star_email);
        }
    }

    /** 用户取消关注 */
    public void cancelStar(String email, String cancel_email) throws Exception {
        email = email.replace("@", "%40");
        cancel_email = cancel_email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email)
                .and("star.email").is(cancel_email));
        User.Stars[] stars = mongoTemplate.findOne(query, User.class).getStar();
        for (User.Stars star : stars) {
            if (star.getEmail().equals(cancel_email)) {
                Update update = new Update();
                update.pull("star", star);
                mongoTemplate.updateFirst(query, update, User.class);
                return;
            }
        }
    }

    /** 判断user_email用户是否是email用户关注的用户 */
    public boolean isStar(String email, String user_email) throws Exception {
        email = email.replace("@", "%40");
        user_email = user_email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if(user == null) throw new CourseWarn(UserWarnEnum.USER_FAILED);
        User.Stars[] stars = user.getStar();
        if (stars != null) {
            for (User.Stars star : stars) {
                if (star.getEmail().equals(user_email)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* 获取关注列表 */
    public String getStars(String email) throws Exception {
        email = email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null)
            throw new CourseWarn(UserWarnEnum.USER_FAILED);
        User.Stars[] stars = user.getStar();
        String res = "";
        if (stars != null) {
            for(User.Stars star: stars) {
                res = res + star.toString() + ",";
            }
        }
        return res;
    }

    /* 屏蔽用户 */
    public void blockUser(String email, String block_email) throws Exception {
        email = email.replace("@", "%40");
        block_email = block_email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email).and("black.email").is(block_email));
        if(mongoTemplate.findOne(query, User.class) != null) {
            throw new CourseWarn(UserWarnEnum.BLOCK_FAILED);
        }
        Query query1 = new Query();
        query1.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        Query query2 = new Query();
        query2.addCriteria(Criteria.where(KeyConstant.EMAIL).is(block_email));
        User.Blacks black = new User.Blacks(block_email);
        Update update = new Update();
        update.push("black", black);
        mongoTemplate.updateFirst(query1, update, User.class);

        // 对屏蔽的用户取消关注
        if (isStar(email, block_email) == true) {
            cancelStar(email, block_email);
        }
    }

    /** 用户取消屏蔽 */
    public void cancelBlock(String email, String cancel_email) throws Exception {
        email = email.replace("@", "%40");
        cancel_email = cancel_email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email)
                .and("black.email").is(cancel_email));
        User.Blacks[] blacks = mongoTemplate.findOne(query, User.class).getBlack();
        for (User.Blacks black : blacks) {
            if (black.getEmail().equals(cancel_email)) {
                Update update = new Update();
                update.pull("black", black);
                mongoTemplate.updateFirst(query, update, User.class);
                return;
            }
        }
    }

    /** 判断user_email用户是否是email用户屏蔽的用户 */
    public boolean isBlock(String email, String user_email) throws Exception {
        email = email.replace("@", "%40");
        user_email = user_email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if(user == null) throw new CourseWarn(UserWarnEnum.USER_FAILED);
        User.Blacks[] blacks = user.getBlack();
        if (blacks != null) {
            for (User.Blacks black : blacks) {
                if (black.getEmail().equals(user_email)) {
                    return true;
                }
            }
        }
        return false;
    }
}
