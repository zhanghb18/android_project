package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.Moment;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt.*;
import com.tsinghua.course.Frame.Util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.tsinghua.course.Base.Enum.NoticeType.*;

@Component
public class MomentProcessor {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserProcessor userProcessor;

    @Autowired
    FileUtil fileUtil;

    /* 发布动态 */
    public void CreateMomentByUser(String email, String title, String content, String time) throws Exception {
        System.out.println("CreateMomentByUser");
        time = URLDecoder.decode(time, "utf-8");
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
    }

    /* 获取除屏蔽用户外的全部动态--时间顺序 */
    public String getMoments(String email) throws Exception {
        Query query = new Query();
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        if (moments == null) {
            return "null";
        }
        String res = "";
        for (int i = moments.size()-1; i >= 0; i--) {
            Moment moment = moments.get(i);
            String cur_email = moment.getEmail();
            cur_email = cur_email.replace("@", "%40");
            if (userProcessor.isBlock(email, cur_email) == true) {
                continue;
            }

            Query query1 = new Query();
            query1.addCriteria(Criteria.where(KeyConstant.EMAIL).is(cur_email));
            String nickname = mongoTemplate.findOne(query1, User.class).getNickname();
            String aboutMe = mongoTemplate.findOne(query1, User.class).getAboutMe();
            res += moment.momentString(nickname, aboutMe);
        }
        return res;
    }

    /* 获取关注用户的全部动态--时间顺序 */
    public String getStarMoments(String email) throws Exception {
        email = email.replace("@", "%40");
        Query query = new Query();
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        if (moments == null) {
            return "null";
        }
        String res = "";
        for (int i = moments.size()-1; i >= 0; i--) {
            Moment moment = moments.get(i);
            String cur_email = moment.getEmail();
            cur_email = cur_email.replace("@", "%40");
            if (userProcessor.isStar(email, cur_email) == false) {
                continue;
            }

            Query query1 = new Query();
            query1.addCriteria(Criteria.where(KeyConstant.EMAIL).is(cur_email));
            String nickname = mongoTemplate.findOne(query1, User.class).getNickname();
            String aboutMe = mongoTemplate.findOne(query1, User.class).getAboutMe();
            res += moment.momentString(nickname, aboutMe);
        }
        return res;
    }

    /* 获取除屏蔽用户外的全部动态--点赞数顺序 */
    public String getMomentsByLikes(String email) throws Exception {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("likes")));
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        if (moments == null) {
            return "null";
        }
        String res = "";
        for (int i = 0; i < moments.size(); i++) {
            Moment moment = moments.get(i);
            String cur_email = moment.getEmail();
            cur_email = cur_email.replace("@", "%40");
            if (userProcessor.isBlock(email, cur_email) == true) {
                continue;
            }

            Query query1 = new Query();
            query1.addCriteria(Criteria.where(KeyConstant.EMAIL).is(cur_email));
            String nickname = mongoTemplate.findOne(query1, User.class).getNickname();
            String aboutMe = mongoTemplate.findOne(query1, User.class).getAboutMe();
            res += moment.momentString(nickname, aboutMe);
        }
        return res;
    }

    /* 获取关注用户的全部动态--点赞数顺序 */
    public String getStarMomentsByLikes(String email) throws Exception {
        email = email.replace("@", "%40");
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("likes")));
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        if (moments == null) {
            return "null";
        }
        String res = "";
        for (int i = 0; i < moments.size(); i++) {
            Moment moment = moments.get(i);
            String cur_email = moment.getEmail();
            cur_email = cur_email.replace("@", "%40");
            if (userProcessor.isStar(email, cur_email) == false) {
                continue;
            }

            Query query1 = new Query();
            query1.addCriteria(Criteria.where(KeyConstant.EMAIL).is(cur_email));
            String nickname = mongoTemplate.findOne(query1, User.class).getNickname();
            String aboutMe = mongoTemplate.findOne(query1, User.class).getAboutMe();
            res += moment.momentString(nickname, aboutMe);
        }
        return res;
    }

    /* 获取某用户的动态列表---时间顺序 */
    public String getPersonalMomentByEmail(String email) throws Exception{
        Query query = new Query();
        email = email.replace("@", "%40");
        query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(email));
        List<Moment> moments = mongoTemplate.find(query, Moment.class);
        if (moments == null) {
            return "null";
        }
        String res = "";
        for (int i = moments.size()-1; i >= 0; i--) {
            Moment moment = moments.get(i);
            String cur_email = moment.getEmail();
            Query query1 = new Query();
            query1.addCriteria(Criteria.where(KeyConstant.EMAIL).is(cur_email));
            String nickname = mongoTemplate.findOne(query1, User.class).getNickname();
            String aboutMe = mongoTemplate.findOne(query1, User.class).getAboutMe();
            res += moment.momentString(nickname, aboutMe);
        }
        return res;
    }

    /* 动态点赞 */
    public void LikeMoment(LikeMomentInParams inParams) throws Exception {
        System.out.println("LikeMoment");
        Query query = new Query();
        String time = URLDecoder.decode(inParams.getTime(), "utf-8");
        String email = inParams.getEmail().replace("@", "%40");
        String post_email = inParams.getPost_email().replace("@", "%40");
        query.addCriteria(Criteria.where("email").is(post_email).and("post_time").is(time));
        Moment moment = mongoTemplate.findOne(query, Moment.class);
        if (moment == null) {
            throw new CourseWarn(UserWarnEnum.MOMENT_FAILED);
        }
        // 检查是否已经点赞
        if( moment.ifliked(email)) {
            return;
        }
        else {
            int likes = moment.getLikes();
            likes += 1;
            Update update = new Update();
            update.push("liked", inParams.getEmail());
            update.set("likes", likes);
            mongoTemplate.updateFirst(query, update, Moment.class);
        }

        // 给被点赞用户发送通知
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User.Notices notice = new User.Notices(email, "LIKE", simpleDateFormat.format(new Date()));
        Query user_query = new Query();
        user_query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(post_email));
        Update user_update = new Update();
        user_update.push("notice", notice);
        mongoTemplate.updateFirst(user_query, user_update, User.class);
    }

    /* 动态取消点赞 */
    public void unlikeMomentByEmail(UnlikeMomentInParams inParams) throws Exception {
        Query query = new Query();
        String time = URLDecoder.decode(inParams.getTime(), "utf-8");
        String post_email = inParams.getPost_email().replace("@", "%40");
        query.addCriteria(Criteria.where("email").is(post_email).and("post_time").is(time));
        Moment moment = mongoTemplate.findOne(query, Moment.class);
        if (moment == null) {
            throw new CourseWarn(UserWarnEnum.MOMENT_FAILED);
        }
        int likes = moment.getLikes();
        likes -= 1;
        Update update = new Update();
        update.pull("liked", inParams.getEmail());
        update.set("likes", likes);
        mongoTemplate.updateFirst(query, update, Moment.class);
    }

    /* 评论动态或回复评论 */
    public void CommentMoment(CommentMomentInParams inParams) throws Exception {
        Query query = new Query();
        String content = URLDecoder.decode(inParams.getComment(), "utf-8");
        String post_time = URLDecoder.decode(inParams.getPost_time(), "utf-8");
        String comment_time = URLDecoder.decode(inParams.getComment_time(), "utf-8");
        String post_email = inParams.getPost_email().replace("@", "%40");
        String email = inParams.getEmail().replace("@", "%40");
        query.addCriteria(Criteria.where("email").is(post_email).and("post_time").is(post_time));
        Moment moment = mongoTemplate.findOne(query, Moment.class);
        if (moment == null) {
            throw new CourseWarn(UserWarnEnum.MOMENT_FAILED);
        }
        Update update = new Update();
        if (inParams.getReply_email() == null ){ // 评论动态
            Moment.Comment comment = new Moment.Comment(email, post_email,
                    content, comment_time);
            update.push("commentList", comment);
        }
        else { // 回复评论
            String reply_email = inParams.getReply_email().replace("@", "%40");
            Moment.Comment comment = new Moment.Comment(email, reply_email, content, comment_time);
            update.push("commentList", comment);

            // 给被评论者发送通知
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            User.Notices com_notice = new User.Notices(email, "COMMENT", simpleDateFormat.format(new Date()));
            Query com_query = new Query();
            com_query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(reply_email));
            Update com_update = new Update();
            com_update.push("notice", com_notice);
            mongoTemplate.updateFirst(com_query, com_update, User.class);
        }
        mongoTemplate.updateFirst(query, update, Moment.class);

        // 给动态发布者发送通知
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User.Notices notice = new User.Notices(email, "COMMENT", simpleDateFormat.format(new Date()));
        Query user_query = new Query();
        user_query.addCriteria(Criteria.where(KeyConstant.EMAIL).is(post_email));
        Update user_update = new Update();
        user_update.push("notice", notice);
        mongoTemplate.updateFirst(user_query, user_update, User.class);
    }

    /* 删除评论或回复 */
    public void DeleteComment(DeleteCommentInParams inParams) throws Exception {
        Query query = new Query();
        String post_time = URLDecoder.decode(inParams.getPost_time(), "utf-8");
        String content = URLDecoder.decode(inParams.getComment(), "utf-8");
        String comment_time = URLDecoder.decode(inParams.getComment_time(), "utf-8");
        String post_email = inParams.getPost_email().replace("@", "%40");
        query.addCriteria(Criteria.where("email").is(post_email).and("post_time").is(post_time));
        Moment moment = mongoTemplate.findOne(query, Moment.class);
        if (moment == null) {
            throw new CourseWarn(UserWarnEnum.MOMENT_FAILED);
        }
        Update update = new Update();
        if (inParams.getReply_email() == null) { // 删除动态评论
            Moment.Comment comment = new Moment.Comment(inParams.getEmail(), inParams.getPost_email(),
                    content, comment_time);
            update.pull("commentList", comment);
        }
        else { // 删除评论回复
            Moment.Comment comment = new Moment.Comment(inParams.getEmail(), inParams.getReply_email(),
                    content, comment_time);
            update.pull("commentList", comment);
        }
        mongoTemplate.updateFirst(query, update, Moment.class);
    }
}
