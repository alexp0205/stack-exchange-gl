package com.alex.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskMessage {
    private UUID id;
    private String questionIds;
    private Long timeStamp;
    private Boolean completed;
}
