package com.alex.api.stack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StackQuestionResponse {
    private List<StackQuestion> items;
    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("quote_max")
    private Integer quotaMax;
    @JsonProperty("quota_remaining")
    private Integer quotaRemaining;
}
