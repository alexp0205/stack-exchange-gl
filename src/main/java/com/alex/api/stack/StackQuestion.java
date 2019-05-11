package com.alex.api.stack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StackQuestion {
    @JsonProperty("question_id")
    private Integer questionId;

    private List<String> tags;
    private String title;
}
