package com.egconley.taskmaster;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egconley.taskmaster.TaskFragment.OnListFragmentInteractionListener;
import com.egconley.taskmaster.content.TaskContent;
import com.egconley.taskmaster.content.Task;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    String TAG = "egc.MyTaskRecyclerViewAdapter";

    private final List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
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
        holder.mTaskTitleView.setText(mValues.get(position).getTitle());
        holder.mTaskBodyView.setText(mValues.get(position).getBody());

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
        public Task mItem;

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