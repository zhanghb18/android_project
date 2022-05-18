package com.example.forum.ui.moments;

import com.example.forum.ForumActivity;
import com.example.forum.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.ui.PersonalPage.OtherHomeActivity;
import com.example.forum.ui.PersonalPage.PersonHomeActivity;
import com.example.forum.ui.moments.SingleMoment;
import com.example.forum.user.UserApplication;

import java.util.LinkedList;
import java.util.List;


public class MomentsAdapter extends
        RecyclerView.Adapter<MomentsAdapter.MomentsViewHolder> {

    private final List<SingleMoment> momment_List;
    private final LayoutInflater mInflater;
    public Context context;

    class MomentsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView content_view;
        public final TextView title_view;
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
            content_view = itemView.findViewById(R.id.content);
            title_view = itemView.findViewById(R.id.title);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
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
            //点击头像进入个人主页
            ImageView imageView=itemView.findViewById(R.id.moment_all_avator);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    String cur_email = momment_List.get(mPosition).email;
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
        this.momment_List = mommentList;
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
        SingleMoment moment=momment_List.get(position);
        holder.title_view.setText(moment.title);
        holder.content_view.setText(moment.content);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return momment_List.size();
    }
//    @Override
//    public int getItemViewType(int position) {
//        return momment_List.get(position) != null ? VIEW_ITEM : VIEW_PROG;
}



