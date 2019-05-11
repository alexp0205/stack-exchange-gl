package com.alex.core;

import com.alex.api.Question;
import com.alex.api.stack.StackQuestion;
import com.alex.api.stack.StackQuestionResponse;
import com.alex.client.StackExchangeClient;
import com.alex.db.ElasticClient;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateIndexTask implements Runnable {

    private StackExchangeClient stackExchangeClient;
    private ElasticClient elasticClient;

    @Override
    public void run() {
        // TODO - check if RUN allowed
        // If not sleep
        // GET from data store next set of docs to be read

        String questionString = "1;2;3";
        StackQuestionResponse stackQuestionResponse = stackExchangeClient.getQuestions(questionString);
        for (StackQuestion stackQuestion: stackQuestionResponse.getItems()) {
            Question question = new Question(stackQuestion);
            elasticClient.index(question);
        }
    }
}
