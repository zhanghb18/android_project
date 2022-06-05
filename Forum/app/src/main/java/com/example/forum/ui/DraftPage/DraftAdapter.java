package com.example.forum.ui.DraftPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.moments.SingleMoment;
import com.example.forum.ui.DraftPage.ChangeMomentsActivity;

import java.util.List;


public class DraftAdapter extends
        RecyclerView.Adapter<DraftAdapter.DraftViewHolder> {

    private final List<SingleMoment> moment_List;
    private final LayoutInflater mInflater;
    public Context context;

    class DraftViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final DraftAdapter mAdapter;
        private AdapterView.OnItemClickListener mOnItemClickListener;
        public final TextView mDraftTimeView;
        public final TextView mTitleView;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public DraftViewHolder(View itemView, DraftAdapter adapter) {
            super(itemView);
            mDraftTimeView = itemView.findViewById(R.id.draft_time);
            mTitleView = itemView.findViewById(R.id.draft_title);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

            //点击跳转进入草稿编辑页面
            ImageView imageView=itemView.findViewById(R.id.draft_rightarrow);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    String cur_post_time = moment_List.get(mPosition).post_time;
                    String cur_content = moment_List.get(mPosition).content;
                    String cur_title = moment_List.get(mPosition).title;
                    Intent intent = new Intent(context,ChangeMomentsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",cur_title);
                    bundle.putString("content",cur_content);
                    bundle.putString("post_time",cur_post_time);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
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

    public DraftAdapter(Context context, List<SingleMoment> momentList) {
        mInflater = LayoutInflater.from(context);
        this.moment_List = momentList;
        this.context = context;
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
    public DraftAdapter.DraftViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.draft_list, parent, false);

        //MomentsViewHolder momentsViewHolder=new MomentsViewHolder(mItemView,mOnItemClickListener)

        return new DraftViewHolder(mItemView, this);
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
    public void onBindViewHolder(DraftAdapter.DraftViewHolder holder,
                                 int position) {
        // Retrieve the data for that position.
//        String mCurrent = mWordList.get(position);
        // Add the data to the view holder.
        SingleMoment moment=moment_List.get(position);
        holder.mDraftTimeView.setText(moment.post_time);
        holder.mTitleView.setText(moment.title);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return moment_List.size();
    }
//    @Override
//    public int getItemViewType(int position) {
//        return momment_List.get(position) != null ? VIEW_ITEM : VIEW_PROG;
}



