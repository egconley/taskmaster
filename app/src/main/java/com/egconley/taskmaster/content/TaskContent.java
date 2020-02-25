package com.egconley.taskmaster.content;

import android.util.Log;

import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.MutationType;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amplifyframework.datastore.generated.model.Task;
/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Task> TASK_LIST = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by title.
     */
    public static final Map<String, Task> TASK_MAP = new HashMap<>();

    private static final int COUNT = 3;


    static {
        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addTask(createTask());
        }
    }

    private static void addTask(Task task) {

        Amplify.API.query(Task.class, new ResultListener<GraphQLResponse<Iterable<Task>>>() {
            @Override
            public void onResult(GraphQLResponse<Iterable<Task>> iterableGraphQLResponse) {
                for(Task task : iterableGraphQLResponse.getData()) {
                    Log.i("AmplifyGetStarted", "Task : " + task.getTitle());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("AmplifyGetStarted", throwable.toString());
            }
        });
    }

    private static Task createTask() {

        //int task_number, String title, String body, String state
        //state should be one of “new”, “assigned”, “in progress”, or “complete”.
//        String[][] sampleTasks = { {"Deliver review","productize the deliverables and focus on the bottom line", "assigned"}, {"Cloud strategy", "Sea change where the metal hits the meat.", "in progress"}, {"Synergize product", "Manage that low hanging fruit.", "new"} };

        Task task = com.amplifyframework.datastore.generated.model.Task.builder().title("My first task").body("The boyd of my first task").state("assigned").build();

        Amplify.API.mutate(task, MutationType.CREATE, new ResultListener<GraphQLResponse<com.amplifyframework.datastore.generated.model.Task>>() {
            @Override
            public void onResult(GraphQLResponse<com.amplifyframework.datastore.generated.model.Task> taskGraphQLResponse) {
                Log.i("AmplifyGetStarted", "Added task with id: " + taskGraphQLResponse.getData().getId());
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("AmplifyGetStarted", throwable.toString());
            }
        });
        return task;
    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
//    public static class DummyItem {
//        public final String id;
//        public final String content;
//        public final String details;
//
//        public DummyItem(String id, String content, String details) {
//            this.id = id;
//            this.content = content;
//            this.details = details;
//        }
//
//        @Override
//        public String toString() {
//            return content;
//        }
//    }
}
