package com.egconley.taskmaster.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            addTask(createTask(i));
        }
    }

    private static void addTask(Task task) {
        TASK_LIST.add(task);
        TASK_MAP.put(task.title, task);
    }

    private static Task createTask(int task_number) {

        //int task_number, String title, String body, String state
        //state should be one of “new”, “assigned”, “in progress”, or “complete”.
        String[][] sampleTasks = { {"Deliver review","productize the deliverables and focus on the bottom line", "assigned"}, {"Cloud strategy", "Sea change where the metal hits the meat.", "in progress"}, {"Synergize product", "Manage that low hanging fruit.", "new"} };

        return new Task(task_number + 1, sampleTasks[task_number][0], sampleTasks[task_number][1], sampleTasks[task_number][2]);
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
