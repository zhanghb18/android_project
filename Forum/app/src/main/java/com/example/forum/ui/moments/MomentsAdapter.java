package com.example.forum.ui.moments;

import com.example.forum.ForumActivity;
import com.example.forum.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.ui.PersonalPage.OtherHomeActivity;
import com.example.forum.ui.PersonalPage.PersonHomeActivity;
import com.example.forum.user.UserApplication;

import java.util.List;


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
            //点赞功能
            ImageButton button_like=itemView.findViewById(R.id.button_like);
            TextView text_like=itemView.findViewById(R.id.text_like);
            button_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("clicked like");
                    if(like_flag==false){
                        text_like.setText("取消点赞");
                        button_like.setImageDrawable(button_like.getResources().getDrawable(R.drawable.ic_liked));
                        like_flag=true;
                    }
                    else {
                        text_like.setText("点赞");
                        button_like.setImageDrawable(button_like.getResources().getDrawable(R.drawable.ic_like));
                        like_flag=false;
                    }
                }
            });

            //分享功能
            ImageButton button_share=itemView.findViewById(R.id.button_share);
            button_share.setOnClickListener(new View.OnClickListener() {
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
            });
            //点击头像进入个人主页
            ImageView imageView=itemView.findViewById(R.id.moment_all_avator);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    String cur_email = moment_list.get(mPosition).email;
//                    System.out.println("**********");
//                    System.out.println(cur_email);
//                    System.out.println(UserApplication.getEmail());
                    if(UserApplication.getEmail()==cur_email){
                        Intent intent=new Intent(context,PersonHomeActivity.class);
                        intent.putExtra(cur_email,EXTRA_MESSAGE);
                        context.startActivity(intent);
                    }
                    else {
                        Intent intent=new Intent(context, OtherHomeActivity.class);
                        intent.putExtra(cur_email,EXTRA_MESSAGE);
                        context.startActivity(intent);
                    }

                }
            });
        }

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

    public MomentsAdapter(Context context, List<SingleMoment> mommentList) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
        this.moment_list = mommentList;
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
        holder.nickname_view.setText(UserApplication.getNickname());
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



