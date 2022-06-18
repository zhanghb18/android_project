package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.Moment;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.UserParams.Out.UserInfoOutParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.w3c.dom.ranges.DocumentRange;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tsinghua.course.Base.Enum.NoticeType.UPDATE;

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
    private String getNickname(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        return user.getNickname();
    }
    private String getAboutMe(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        return user.getAboutMe();
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
        String aboutMe = "这个人很懒，什么也没有留下...";
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
        if(mongoTemplate.findOne(query0, User.class).getUserID().equals(userID) == false) {
            Query query1 = new Query();
            query1.addCriteria(Criteria.where(KeyConstant.USERID).is(userID));
            if (mongoTemplate.findOne(query1, User.class) != null)
                throw new CourseWarn(UserWarnEnum.USERID_DOUBLED);
        }

        if (aboutMe.equals("")) {
            aboutMe = "这个人很懒，什么也没有留下...";
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

        // 成为关注用户的粉丝
        User.Fans fan = new User.Fans(email);
        Update update_fan = new Update();
        update_fan.push("fan", fan);
        Query query_fan = new Query();
        query_fan.addCriteria(Criteria.where(KeyConstant.EMAIL).is(star_email));
        mongoTemplate.updateFirst(query_fan, update_fan, User.class);
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
                break;
            }
        }

        // 取消关注用户的粉丝
        Query query_fan = new Query();
        query_fan.addCriteria(Criteria.where(KeyConstant.EMAIL).is(cancel_email)
                .and("fan.email").is(email));
        User.Fans[] fans = mongoTemplate.findOne(query_fan, User.class).getFan();
        for (User.Fans fan : fans) {
            if (fan.getEmail().equals(email)) {
                Update update_fan = new Update();
                update_fan.pull("fan", fan);
                mongoTemplate.updateFirst(query_fan, update_fan, User.class);
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
                String star_email = star.getEmail();
                String nickname = getNickname(star_email);
                String aboutMe = getAboutMe(star_email);
                res = res + star.starString(nickname, aboutMe) + ",";
            }
        }
        return res;
    }

    /* 获取通知列表---时间顺序 */
    public String getNotice(String email) throws Exception {
        email = email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null)
            throw new CourseWarn(UserWarnEnum.USER_FAILED);
        User.Notices[] notices = user.getNotice();
        String res = "";
        if (notices != null) {
            for(int i = notices.length-1; i >= 0; i--) {
                User.Notices notice = notices[i];
                String notice_email = notice.getEmail();
                String notice_nickname = getNickname(notice_email);
                res = res + notice.noticeString(notice_nickname) + ",";
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

    /* 获取黑名单列表 */
    public String getBlocks(String email) throws Exception {
        email = email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null)
            throw new CourseWarn(UserWarnEnum.USER_FAILED);
        User.Blacks[] blacks = user.getBlack();
        String res = "";
        if (blacks != null) {
            for(User.Blacks black: blacks) {
                String star_email = black.getEmail();
                String nickname = getNickname(star_email);
                String aboutMe = getAboutMe(star_email);
                res = res + black.blackString(nickname, aboutMe) + ",";
            }
        }
        return res;
    }

    /** 将通知设置为已读 */
    public void setNoticeRead(String email, String time) throws Exception {
        email = email.replace("@", "%40");
        time = URLDecoder.decode(time, "utf-8");
        Query query = new Query();
        query.addCriteria(Criteria.where("time").is(time));
        if (mongoTemplate.findOne(query, User.Notices.class) == null) {
            throw new CourseWarn(UserWarnEnum.NOTICE_FAILED);
        }
        Update update = new Update();
        update.set("ifRead", true);
        mongoTemplate.updateFirst(query, update, User.Notices.class);
    }

    /** 新增草稿 */
    public void addDraft(String email, String title, String content, String time) throws Exception {
        email = email.replace("@", "%40");
        if(title.length() >= 0 && title != null) {
            title = URLDecoder.decode(title, "UTF-8");
        }
        if(content.length() >= 0 && content != null) {
            content = URLDecoder.decode(content, "UTF-8");
        }
        time = URLDecoder.decode(time, "utf-8");
        User.Drafts draft = new User.Drafts(title, content, time);
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        Update update = new Update();
        update.push("draft", draft);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    /** 修改草稿 */
    public void modifyDraft(String email, String title, String content, String old_time, String new_time) throws Exception {
        email = email.replace("@", "%40");
        if(title.length() >= 0 && title != null) {
            title = URLDecoder.decode(title, "UTF-8");
        }
        if(content.length() >= 0 && content != null) {
            content = URLDecoder.decode(content, "UTF-8");
        }
        old_time = URLDecoder.decode(old_time, "utf-8");
        new_time = URLDecoder.decode(new_time, "utf-8");

        Query query = new Query();
        query.addCriteria(Criteria.where("time").is(old_time));
        if (mongoTemplate.findOne(query, User.Drafts.class) == null) {
            throw new CourseWarn(UserWarnEnum.DRAFT_FAILED);
        }

        Update update = new Update();
        update.set("title", title);
        update.set("content", content);
        update.set("time", new_time);
        mongoTemplate.updateFirst(query, update, User.Drafts.class);
    }

    /** 删除草稿 */
    public void deleteDraft(String email, String time) throws Exception {
        Query user_query = new Query();
        user_query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email)
                .and("draft.time").is(time));
        User.Drafts[] drafts = mongoTemplate.findOne(user_query, User.class).getDraft();
        if (drafts == null) {
            throw new CourseWarn(UserWarnEnum.DRAFT_FAILED);
        }
        for (User.Drafts draft : drafts) {
            if (draft.getTime().equals(time)) {
                Update update = new Update();
                update.pull("draft", draft);
                mongoTemplate.updateFirst(user_query, update, User.class);
                return;
            }
        }
    }

    /** 发布草稿 */
    public void postDraft(String email, String title, String content, String old_time, String post_time) throws Exception {
        // 确认草稿存在
        email = email.replace("@", "%40");
        old_time = URLDecoder.decode(old_time, "utf-8");
        Query query = new Query();
        query.addCriteria(Criteria.where("time").is(old_time));
        if (mongoTemplate.findOne(query, User.Drafts.class) == null) {
            throw new CourseWarn(UserWarnEnum.DRAFT_FAILED);
        }

        // 发布动态
        System.out.println("CreateMomentByUser");
        String time = URLDecoder.decode(post_time, "utf-8");
        email = email.replace("@", "%40");
        Moment moment = new Moment(email, time);
        if(content.length() >= 0 && content != null) {
            content = URLDecoder.decode(content, "UTF-8");
            moment.setContent(content);
        }
        if(title.length() >= 0 && title != null) {
            title = URLDecoder.decode(title, "UTF-8");
            moment.setTitle(title);
        }
        mongoTemplate.insert(moment);

        // 给粉丝发送通知
        Query fan_query = new Query();
        fan_query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User.Fans[] fans = mongoTemplate.findOne(fan_query, User.class).getFan();
        User.Notices notice = new User.Notices(email, "UPDATE", time);
        if (fans != null) {
            for (User.Fans fan : fans) {
                String fan_email = fan.getEmail();
                Query notice_query = new Query();
                notice_query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(fan_email));
                Update fan_update = new Update();
                fan_update.push("notice", notice);
                mongoTemplate.updateFirst(notice_query, fan_update, User.class);
            }
        }

        // 将当前草稿从草稿箱中删除
        deleteDraft(email, old_time);
    }

    /** 获取草稿箱列表 */
    public String getDrafts(String email) throws Exception {
        email = email.replace("@", "%40");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null)
            throw new CourseWarn(UserWarnEnum.USER_FAILED);
        User.Drafts[] drafts = user.getDraft();
        String res = "";
        if (drafts != null) {
            for(User.Drafts draft: drafts) {
                res = res + draft.toString() + ",";
            }
        }
        return res;
    }
}
