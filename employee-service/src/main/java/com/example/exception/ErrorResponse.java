package com.example.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) instead of this I have configured in the Yml file
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String errorCode;
    private String errorDescription;
    private String path;
    private List<String> validationErrors;
}
