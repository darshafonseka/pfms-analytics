package com.pfms.pfms_analytics.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class ExpenseResponse {
    private Integer expenseId;
    private Integer userId;
    private String category;
    private BigDecimal amount;
    private LocalDate date;
}
