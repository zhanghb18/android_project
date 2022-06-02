package com.example.forum.ui.moments;

import com.example.forum.ForumActivity;
import com.example.forum.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.ui.PersonalPage.OtherHomeActivity;
import com.example.forum.ui.PersonalPage.PersonHomeActivity;
import com.example.forum.user.UserApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MomentsAdapter extends
        RecyclerView.Adapter<MomentsAdapter.MomentsViewHolder> {

    private final List<SingleMoment> moment_list;
    private final LayoutInflater mInflater;
    public Context context;

    class MomentsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView content_view;
        public final TextView title_view;
        public final TextView nickname_view;
        final MomentsAdapter mAdapter;
        private AdapterView.OnItemClickListener mOnItemClickListener;
        boolean like_flag=false;
        public static final String EXTRA_MESSAGE = "content";

        LinearLayout box_like;
        LinearLayout box_share;
        LinearLayout box_comment;
        LinearLayout box_like_list=itemView.findViewById(R.id.like_list_box);
        LinearLayout box_comment_list=itemView.findViewById(R.id.comment_list_box);
        ListView comment_list=itemView.findViewById(R.id.comment_list);
        private List<Comment> data;
        private CommentsAdapter commentsAdapter;
        ImageButton button_like=itemView.findViewById(R.id.button_like);
        TextView text_like=itemView.findViewById(R.id.text_like);;
        TextView text_like_list=itemView.findViewById(R.id.like_list);
        String like_list="";

        private PopupWindow popupWindow;
        private View popupView = null;
        private EditText inputComment;
        private String nInputContentText;
        private TextView btn_submit;
        private RelativeLayout rl_input_container;
        private InputMethodManager mInputManager;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public MomentsViewHolder(View itemView, MomentsAdapter adapter) {
            super(itemView);
            content_view = itemView.findViewById(R.id.moment_content);
            title_view = itemView.findViewById(R.id.moment_title);
            nickname_view=itemView.findViewById(R.id.moment_nickname);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

            box_like=itemView.findViewById(R.id.box_like);
            box_like.setClickable(true);
            box_like.setOnClickListener(like_click);

            box_share=itemView.findViewById(R.id.box_share);
            box_share.setClickable(true);
            box_share.setOnClickListener(share_click);


            box_comment=itemView.findViewById(R.id.box_comment);
            box_comment.setClickable(true);
            //box_comment.setOnClickListener(comment_click);
            data=new ArrayList<>();
            commentsAdapter=new CommentsAdapter(context,data);
            comment_list.setAdapter(commentsAdapter);
            box_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("clicked comment");
                    showPopupComment();
                }
            });


            //点击头像进入个人主页
            ImageView imageView=itemView.findViewById(R.id.moment_all_avator);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
//                    System.out.println(moment_list.get(mPosition).email);
                    String cur_email = moment_list.get(mPosition).email;
                    String cur_nickname = moment_list.get(mPosition).nickname;
                    String cur_aboutMe = moment_list.get(mPosition).aboutMe;
//                    System.out.println("**********");
//                    System.out.println(cur_email);
//                    System.out.println(UserApplication.getEmail());
                    if(UserApplication.getEmail().equals(cur_email)){
                        Intent intent=new Intent(context,PersonHomeActivity.class);
                        //intent.putExtra(cur_email,EXTRA_MESSAGE);
                        context.startActivity(intent);
                    }
                    else {
                        Intent intent=new Intent(context, OtherHomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("email", cur_email);
                        bundle.putString("nickname", cur_nickname);
                        bundle.putString("aboutMe",cur_aboutMe);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                }
            });
        }

        //初始化评论列表

        //弹出评论框
            @SuppressLint("WrongConstant")
            private void showPopupComment() {
                if (popupView == null){
                    //加载评论框的资源文件
                    popupView = LayoutInflater.from(context).inflate(R.layout.comment_popupwindow, null);
                }
                inputComment = (EditText) popupView.findViewById(R.id.et_discuss);
                btn_submit = (Button) popupView.findViewById(R.id.btn_confirm);
                rl_input_container = (RelativeLayout)popupView.findViewById(R.id.rl_input_container);
                //利用Timer这个Api设置延迟显示软键盘，这里时间为200毫秒
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run()
                    {
                        mInputManager=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE) ;
                        //mInputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mInputManager.showSoftInput(inputComment, 0);
                    }
                }, 200);
                if (popupWindow == null){
                    popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT, false);
                }
                //popupWindow的常规设置，设置点击外部事件，背景色
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                            popupWindow.dismiss();
                        return false;
                    }
                });
                // 设置弹出窗体需要软键盘，放在setSoftInputMode之前
                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                // 再设置模式，和Activity的一样，覆盖，调整大小。
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                //设置popupwindow的显示位置，这里应该是显示在底部，即Bottom
                popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
                popupWindow.update();
                //设置监听
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    // 在dismiss中恢复透明度
                    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                    public void onDismiss() {
                        mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0); //强制隐藏键盘
                    }
                });
                //外部点击事件
                rl_input_container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0); //强制隐藏键盘
                        popupWindow.dismiss();
                    }
                });
                //评论框内的发送按钮设置点击事件
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nInputContentText = inputComment.getText().toString().trim();
                        if (nInputContentText == null || "".equals(nInputContentText)) {
                            //Comment comment=new Comment();
                            return;
                        }
                        Comment comment=new Comment();
                        String nickname=UserApplication.getNickname();
                        comment.setName(nickname);
                        comment.setContent(nInputContentText);
                        System.out.println("新评论");
                        System.out.println(nInputContentText);
                        commentsAdapter.addComment(comment);
                        setListViewHeightBasedOnChildren(comment_list);
                        box_comment_list.setVisibility(View.VISIBLE);
                        inputComment.setText("");
                        mInputManager.hideSoftInputFromWindow(inputComment.getWindowToken(),0);
                        popupWindow.dismiss();
                    }
                });
            }

        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter(); if (listAdapter == null) { // pre-condition
                return;
            }
            int totalHeight = 0; for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView); // listItem.measure(0, 0);
                listItem.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }


        //点赞
        public View.OnClickListener like_click=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked like");
                if(like_flag==false){
                    String cur_email;
                    cur_email=UserApplication.getEmail();
                    text_like.setText("取消点赞");
                    button_like.setImageDrawable(button_like.getResources().getDrawable(R.drawable.ic_liked));
                    if(like_list.length()==0){
                        System.out.println("list有了");
                        box_like_list.setVisibility(View.VISIBLE);
                    }
                    like_list=like_list+"enenen";
                    System.out.println(like_list);
                    text_like_list.setText(like_list);
                    like_flag=true;
                }
                else {
                    text_like.setText("点赞");
                    button_like.setImageDrawable(button_like.getResources().getDrawable(R.drawable.ic_like));
                    like_list=like_list.replaceAll("enenen","");
                    System.out.println("取消点赞");
                    System.out.println(like_list);
                    if(like_list.length()==0){
                        System.out.println("list空了");
                        box_like_list.setVisibility(View.GONE);
                    }
                    like_flag=false;
                }
            }
        };

        //评论
        public View.OnClickListener comment_click=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked comment");
                //弹出键盘
//                InputMethodManager inputMethodManager=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                //弹出评论框
                //rl_comment.setVisibility(View.VISIBLE);
            }
        };

        //分享
        public View.OnClickListener share_click=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = getLayoutPosition();
                System.out.println("clicked share");
                String share_title= moment_list.get(mPosition).title;
                String share_content=moment_list.get(mPosition).content;
                String mimeType = "text/plain";
                ShareCompat.IntentBuilder
                        .from((Activity)view.getContext())
                        .setType(mimeType)
                        .setText(share_title+'\n'+share_content)
                        .startChooser();
            }
        };

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
//            int mPosition = getLayoutPosition();
//
//            // Use that to access the affected item in mWordList.
//            String element = mWordList.get(mPosition);
//            // Change the word in the mWordList.
//
//            mWordList.set(mPosition, "Clicked! " + element);
//            // Notify the adapter, that the data has changed so it can
//            // update the RecyclerView to display the data.
//            mAdapter.notifyDataSetChanged();

        }
    }

    public MomentsAdapter(Context context, List<SingleMoment> momentList) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
        this.moment_list = momentList;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     *
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */
    @Override
    public MomentsAdapter.MomentsViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.moment_list, parent, false);

        //MomentsViewHolder momentsViewHolder=new MomentsViewHolder(mItemView,mOnItemClickListener)

        return new MomentsViewHolder(mItemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MomentsAdapter.MomentsViewHolder holder,
                                 int position) {
        // Retrieve the data for that position.
//        String mCurrent = mWordList.get(position);
        // Add the data to the view holder.
        SingleMoment moment= moment_list.get(position);
        holder.title_view.setText(moment.title);
        holder.content_view.setText(moment.content);
        holder.nickname_view.setText(moment.nickname);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return moment_list.size();
    }
//    @Override
//    public int getItemViewType(int position) {
//        return momment_List.get(position) != null ? VIEW_ITEM : VIEW_PROG;
}



