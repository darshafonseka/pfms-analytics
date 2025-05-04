package com.pfms.pfms_analytics.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationException {
    private String field;
    private String message;
}
