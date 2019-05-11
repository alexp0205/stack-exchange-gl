package com.alex.api;

import com.alex.api.stack.StackQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer questionId;
    private List<String> tags;
    private String title;

    public Question(final StackQuestion stackQuestion) {
        this.questionId = stackQuestion.getQuestionId();
        this.tags = stackQuestion.getTags();
        this.title = stackQuestion.getTitle();
    }
}
