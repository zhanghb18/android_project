package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.Moment;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.*;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt.PostMomentInParams;
import com.tsinghua.course.Frame.Util.FileUtil;
import io.netty.handler.codec.http.multipart.MixedFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MomentProcessor {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserProcessor userProcessor;

    @Autowired
    FileUtil fileUtil;

    /* 发布动态 */
    public void CreateMomentByUser(PostMomentInParams inParams) throws Exception {
        System.out.println("CreateMomentByUser");
        String time = URLDecoder.decode(inParams.getPost_time(), "utf-8");
        Moment moment = new Moment(inParams.getEmail(), time);
        String content = inParams.getContent();
//        MixedFileUpload[] images = inParams.getImages();
//        moment.setAvatar_url(userProcessor.getAvatarByUsername(inParams.getUsername()));
        if(content.length() >= 0 && content != null) {
            content = URLDecoder.decode(content, "UTF-8");
            moment.setContent(content);
        }
//        if(images != null && images.length > 0)
//        {
//            List<String> image_urlList = new ArrayList<>();
//            for(MixedFileUpload item: images) {
//                image_urlList.add(fileUtil.saveFile(item));
//            }
//            moment.setImages(image_urlList.toArray(new String[image_urlList.size()]));
//        }
        mongoTemplate.insert(moment);
    }
//    /* 点赞朋友圈 */
//    public void LikeDiscoverByUsername(LikeDiscoverInParams inParams) throws Exception {
//        Query query = new Query();
//        String time = URLDecoder.decode(inParams.getTime(), "utf-8");
//        query.addCriteria(Criteria.where("username").is(inParams.getPost_username())
//                .and("post_time").is(time));
//        Discover discover = mongoTemplate.findOne(query, Discover.class);
//        if (discover == null) {
//            throw new CourseWarn(UserWarnEnum.INVALID_OPTION);
//        }
//        // 检查是否已经点赞
//        if( discover.ifliked(inParams.getUsername())) {
//            return;
//        }
//        else {
//            Update update = new Update();
//            update.push("liked", inParams.getUsername());
//            mongoTemplate.updateFirst(query, update, Discover.class);
//        }
//    }

//    /* 评论朋友圈或者回复评论 */
//    public void CommentDiscoverByUsername(CommentDiscoverInParams inParams) throws Exception {
//        Query query = new Query();
//        String content = URLDecoder.decode(inParams.getContent(), "utf-8");
//        String post_time = URLDecoder.decode(inParams.getPost_time(), "utf-8");
//        String comment_time = URLDecoder.decode(inParams.getComment_time(), "utf-8");
//        query.addCriteria(Criteria.where("username").is(inParams.getPost_username()).and("post_time").is(post_time));
//        Discover discover = mongoTemplate.findOne(query, Discover.class);
//        if (discover == null) {
//            throw new CourseWarn(UserWarnEnum.INVALID_OPTION);
//        }
//        Update update = new Update();
//        // 评论朋友圈
//        if (inParams.getReply_username() == null ){
//            Discover.Comment comment = new Discover.Comment(inParams.getUsername(), inParams.getPost_username(),
//                    content, comment_time);
//            update.push("commentList", comment);
//
//        }
//        else {
//            Discover.Comment comment = new Discover.Comment(inParams.getUsername(), inParams.getReply_username(),
//                    content, comment_time);
//            update.push("commentList", comment);
//        }
//        mongoTemplate.updateFirst(query, update, Discover.class);
//    }
//
//    /* 取消朋友圈点赞 */
//    public void unLikeDiscoverByUsername(UnLikeDiscoverInParams inParams) throws Exception {
//        Query query = new Query();
//        String time = URLDecoder.decode(inParams.getTime(), "utf-8");
//        query.addCriteria(Criteria.where("username").is(inParams.getPost_username())
//                .and("post_time").is(time));
//        Discover discover = mongoTemplate.findOne(query, Discover.class);
//        if (discover == null) {
//            throw new CourseWarn(UserWarnEnum.INVALID_OPTION);
//        }
//        Update update = new Update();
//        update.pull("liked", inParams.getUsername());
//        mongoTemplate.updateFirst(query, update, Discover.class);
//    }
//    /* 删除评论/回复 */
//    public void deleteCommentByUsername(DeleteCommentInParams inParams) throws Exception {
//        Query query = new Query();
//        String post_time = URLDecoder.decode(inParams.getPost_time(), "utf-8");
//        String content = URLDecoder.decode(inParams.getContent(), "utf-8");
//        String comment_time = URLDecoder.decode(inParams.getComment_time(), "utf-8");
//        query.addCriteria(Criteria.where("username").is(inParams.getPost_username())
//                .and("post_time").is(post_time));
//        Discover discover = mongoTemplate.findOne(query, Discover.class);
//        if (discover == null) {
//            throw new CourseWarn(UserWarnEnum.INVALID_OPTION);
//        }
//        Update update = new Update();
//        Discover.Comment comment = new Discover.Comment(inParams.getUsername(), inParams.getReply_username(),
//                content, comment_time);
//        update.pull("commentList", comment);
//        mongoTemplate.updateFirst(query, update, Discover.class);
//    }
//
//    /* 获取用户的好友朋友圈 */
//    public String getDiscoverByUsername(String username) throws Exception{
//        String[] contacts = userProcessor.getContactsByUsername(username);
//        Criteria criteria = new Criteria();
//        criteria.orOperator(Criteria.where(KeyConstant.USERNAME).is(username), Criteria.where(KeyConstant.USERNAME).in(contacts));
//        Query query = new Query(criteria);
//        List<Discover> discovers = mongoTemplate.find(query, Discover.class);
//        String res = "";
//        for (Discover discover : discovers) {
//            res += discover.toString();
//        }
//        return res;
//    }
}
