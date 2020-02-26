package com.egconley.taskmaster;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListTasksQuery;
import com.egconley.taskmaster.TaskFragment.OnListFragmentInteractionListener;
import com.egconley.taskmaster.content.Task;

import java.util.List;


public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    String TAG = "egc.MyTaskRecyclerViewAdapter";

    private final List<ListTasksQuery.Item> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<ListTasksQuery.Item> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    // creates a new row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    // fills in view with correct data given the holder and position index
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTaskTitleView.setText(mValues.get(position).title());
        holder.mTaskBodyView.setText(mValues.get(position).body());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                Log.i(TAG, "clicked!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTaskTitleView;
        public final TextView mTaskBodyView;
        public ListTasksQuery.Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTaskTitleView = (TextView) view.findViewById(R.id.taskTitle);
            mTaskBodyView = (TextView) view.findViewById(R.id.taskBody);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTaskBodyView.getText() + "'";
        }
    }
}
