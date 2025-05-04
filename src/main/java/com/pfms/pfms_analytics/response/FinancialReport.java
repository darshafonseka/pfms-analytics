package com.pfms.pfms_analytics.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialReport {
    private Integer userId;
    private String periodType;
    private FinancialReportPeriod period;
    private FinancialReportSummary summary;
    private List<IncomeBreakdown> incomeBreakdown;
    private List<ExpenseBreakdown> expenseBreakdown;
    private List<String> topSpendingCategories;
}
