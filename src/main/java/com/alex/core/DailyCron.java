package com.alex.core;

import com.alex.api.stack.StackQuestionResponse;
import com.alex.client.StackExchangeClient;
import com.alex.db.MockedTaskQueue;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class DailyCron implements Runnable {

    private static Long lastExecutionDate = null;

    private StackExchangeClient stackExchangeClient;

    @Override
    public void run() {
        System.out.println("DailyCron");
        long timestamp = MockedTaskQueue.getCurrentTimeStamp();
        long yesterdayTimestamp = timestamp - 1000 * 60 * 60 * 24;
        if (lastExecutionDate == null || lastExecutionDate < yesterdayTimestamp) {
            for (int i = 1; i <= 2; i++) {
                StackQuestionResponse stackQuestionResponse = stackExchangeClient.getNewQuestions(yesterdayTimestamp, timestamp, i, 3);
                String questionIds = stackQuestionResponse.getItems().stream()
                        .map(sq -> sq.getQuestionId().toString())
                        .collect(Collectors.joining(";"));
                MockedTaskQueue.addTask(questionIds);
            }
        } else {
            try {
                Thread.sleep(1000 * 60 * 60 * 6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
