package com.alex.service;

import com.alex.api.Question;
import com.alex.api.stack.StackQuestion;
import com.alex.api.stack.StackQuestionResponse;
import com.alex.client.StackExchangeClient;
import com.alex.db.ElasticClient;
import com.alex.db.MockedTaskQueue;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class StackService {
    private StackExchangeClient stackExchangeClient;
    private ElasticClient elasticClient;

    public List<Question> searchQuestions(final String tags,
                                          final int from,
                                          final int size) {
        List<String> tagList;
        if (StringUtils.isBlank(tags)) {
            tagList = Lists.newArrayList();
        } else {
            tagList = Arrays.asList(tags.split(";"));
        }
        return elasticClient.getQuestions(tagList, from, size);
    }

    public Boolean indexQuestions(final String questionIds) {
        StackQuestionResponse stackQuestionResponse = stackExchangeClient.getQuestions(questionIds);
        if (stackQuestionResponse.getQuotaRemaining() == 0) {
            // Assuming quoteRemaining will get reset by that time
            MockedTaskQueue.blockTasksTill(MockedTaskQueue.getCurrentTimeStamp() + 60 * 60 * 6);
        }
        for (StackQuestion stackQuestion : stackQuestionResponse.getItems()) {
            Question question = new Question(stackQuestion);
            elasticClient.index(question);
        }
        return true;
    }


    public StackQuestionResponse getStackExchangeQuestion(final String questionIds) {
        return stackExchangeClient.getQuestions(questionIds);
    }
}
