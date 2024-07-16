package com.alura.forum.exception.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BadInputErrorResponse {

    private int status;

    private String message;

    private String timeStamp;

    private String field;

    private String rejectedValue;

}
