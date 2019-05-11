package com.alex.service;

import com.alex.api.Question;
import com.alex.db.ElasticClient;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class StackService {
    private ElasticClient elasticClient;

    public List<Question> search(final String tags,
                                 final int from,
                                 final int size) {
        List<String> tagList = Arrays.asList(tags.split(";"));
        return elasticClient.getQuestions(tagList, from, size);
    }

    public Question getQuestion(final Integer questionId) {
        return null;
    }
}
