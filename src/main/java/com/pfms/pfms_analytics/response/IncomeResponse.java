package com.pfms.pfms_analytics.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class IncomeResponse {
    private Integer incomeId;
    private Integer userId;
    private String source;
    private BigDecimal amount;
    private LocalDate date;
}
