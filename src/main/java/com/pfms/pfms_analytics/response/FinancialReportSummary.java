package com.pfms.pfms_analytics.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialReportSummary {
    private double totalIncome;
    private double totalExpenses;
    private double netBalance;
}
