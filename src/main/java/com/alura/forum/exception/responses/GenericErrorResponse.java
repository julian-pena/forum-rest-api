package com.alura.forum.exception.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericErrorResponse {

    private int status;

    private String message;

    private String timeStamp;

}
