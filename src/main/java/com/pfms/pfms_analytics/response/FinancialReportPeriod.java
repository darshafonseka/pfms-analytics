package com.pfms.pfms_analytics.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialReportPeriod {
    private LocalDate startDate;
    private LocalDate endDate;
}
