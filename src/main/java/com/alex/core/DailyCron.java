package com.alex.core;

import com.alex.api.stack.StackQuestionResponse;
import com.alex.client.StackExchangeClient;
import com.alex.db.MockedTaskQueue;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class DailyCron implements Runnable {

    private StackExchangeClient stackExchangeClient;

    @Override
    public void run() {
        long timestamp = MockedTaskQueue.getCurrentTimeStamp();
        for (int i = 1; i <= 500; i++) {
            StackQuestionResponse stackQuestionResponse = stackExchangeClient.getNewQuestions(timestamp - 1000 * 60 * 60 * 24, timestamp, i, 10);
            String questionIds = stackQuestionResponse.getItems().stream()
                    .map(sq -> sq.getQuestionId().toString())
                    .collect(Collectors.joining(";"));
            MockedTaskQueue.addTask(questionIds);
        }
    }

    public static void main(String[] args) {
        Long timestamp = MockedTaskQueue.getCurrentTimeStamp();
        System.out.println(timestamp);
        System.out.println(1557532800);
    }
}
