package com.social.book_network.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor // to create a constructor of the class with all arguments
@NoArgsConstructor // to create a constructor of the class with no arguments
@JsonInclude(JsonInclude.Include.NON_EMPTY)//This is used to get the non-empty fields only.
public class ExceptionResponse {

    private Integer businessErrorCode; // For this we have created a enum for all the error code
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;

}
