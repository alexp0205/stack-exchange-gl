package com.alex.db;

import com.alex.api.TaskMessage;

import java.util.*;

public class MockedTaskQueue {
    private static List<TaskMessage> TASK_LIST = new LinkedList<>();
    private static Long BLOCKED_TILL = null;

    public static TaskMessage getTask() {
        long timeStamp = getCurrentTimeStamp();
        if (BLOCKED_TILL != null && BLOCKED_TILL > timeStamp) {
            return null;
        }
        Optional<TaskMessage> taskMessage = TASK_LIST.stream()
                .filter(tm -> !tm.getCompleted())
                .filter(tm -> tm.getTimeStamp() == null || tm.getTimeStamp() > timeStamp)
                .findAny();
        if (taskMessage.isPresent()) {
            TaskMessage task = taskMessage.get();
            task.setTimeStamp(timeStamp);
            return task;
        } else {
            return null;
        }
    }

    public static void blockTasksTill(final Long timeStamp) {
        BLOCKED_TILL = timeStamp;
    }

    public static void acknowledgeTask(final UUID taskId) {
        Optional<TaskMessage> taskMessage = TASK_LIST.stream()
                .filter(tm -> tm.getId().equals(taskId))
                .findFirst();
        taskMessage.ifPresent(message -> message.setCompleted(true));
    }

    public static void addTask(final String questionIds) {
        TaskMessage taskMessage = new TaskMessage(UUID.randomUUID(), questionIds, null, false);
        TASK_LIST.add(taskMessage);
    }

    public static long getCurrentTimeStamp() {
        Date date = new Date();
        return date.getTime() / 1000;
    }
}
