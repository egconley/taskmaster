package com.egconley.taskmaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListTasksQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TaskFragment extends Fragment {

    private AWSAppSyncClient mAWSAppSyncClient;
    RecyclerView recyclerView;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // make server accessible
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(view.getContext().getApplicationContext())
                .awsConfiguration(new AWSConfiguration(view.getContext().getApplicationContext()))
                .build();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(view.getContext().getApplicationContext());

        String team = sharedPreferences.getString("team", null);

        if (team!=null) {

        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//            recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(TaskContent.TASK_LIST, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());

        final String team = sharedPreferences.getString("team", null);
        System.out.println("TEAAAAAMMMM!!!!! " + team);

        mAWSAppSyncClient.query(ListTasksQuery.builder().build()).responseFetcher(AppSyncResponseFetchers.NETWORK_FIRST)
                .enqueue(new GraphQLCall.Callback<ListTasksQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<ListTasksQuery.Data> response) {
                        final List<ListTasksQuery.Item> dbTasks = response.data().listTasks().items();
                        final List<ListTasksQuery.Item> dbTeamTasks = new LinkedList<>();

                        // only show tasks associated with user-selected team
                        for (ListTasksQuery.Item task : dbTasks) {
                            if (task.team()!=null && task.team().name().equals(team)) {
                                dbTeamTasks.add(task);
                            }
                        }
                        Handler threadHandler = new Handler(Looper.getMainLooper()) {
                            public void handleMessage(Message msg) {
                                recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(dbTeamTasks, mListener));

                            }
                        };
                        threadHandler.obtainMessage().sendToTarget();
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {

                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ListTasksQuery.Item task);
    }
}
