package com.example.forum.ui.PersonalPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.moments.SingleMoment;

import org.w3c.dom.Text;

import java.util.List;


public class PersonHomeAdapter extends
        RecyclerView.Adapter<PersonHomeAdapter.PersonHomeViewHolder> {

    private final List<SingleMoment> moment_List;
    private final LayoutInflater mInflater;

    class PersonHomeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final PersonHomeAdapter mAdapter;
        private AdapterView.OnItemClickListener mOnItemClickListener;
        public final TextView mNicknameView;
        public final TextView mTitleView;
        public final TextView mContentView;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public PersonHomeViewHolder(View itemView, PersonHomeAdapter adapter) {
            super(itemView);
            mNicknameView = itemView.findViewById(R.id.person_nickname);
            mTitleView = itemView.findViewById(R.id.person_title);
            mContentView = itemView.findViewById(R.id.person_content);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
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

    public PersonHomeAdapter(Context context, List<SingleMoment> momentList) {
        mInflater = LayoutInflater.from(context);
        this.moment_List = momentList;
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
    public PersonHomeAdapter.PersonHomeViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.person_moment_list, parent, false);

        //MomentsViewHolder momentsViewHolder=new MomentsViewHolder(mItemView,mOnItemClickListener)

        return new PersonHomeViewHolder(mItemView, this);
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
    public void onBindViewHolder(PersonHomeAdapter.PersonHomeViewHolder holder,
                                 int position) {
        // Retrieve the data for that position.
//        String mCurrent = mWordList.get(position);
        // Add the data to the view holder.
        SingleMoment moment=moment_List.get(position);
        holder.mContentView.setText(moment.content);
        holder.mNicknameView.setText(moment.nickname);
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



