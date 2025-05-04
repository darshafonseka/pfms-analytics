package com.pfms.pfms_analytics.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ReportRequest {

    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[1-9]\\d*$", message = "User ID must be a positive number")
    private String userId;

    @NotBlank(message = "Report type is required")
    @Pattern(regexp = "^(MONTHLY|QUARTERLY|YEARLY)$", message = "Report type must be either 'income', 'expense', or 'budget'")
    private String periodType;  // can be "monthly", "quarterly", "yearly"

    @NotBlank(message = "Year is required")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be a valid year (e.g., 2023)")
    private String year; // year (required for monthly, quarterly, and yearly reports)

    @Pattern(regexp = "^(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)$", message = "Month must be a valid month (e.g., JAN, FEB, MAR, etc.)")
    private String month;       // month (required for monthly reports)

    @Pattern(regexp = "^(Q1|Q2|Q3|Q4)$", message = "Quarter must be either 'Q1', 'Q2', 'Q3', or 'Q4'")
    private String quarter;     // quarter (required for quarterly reports, e.g., "Q1", "Q2")

    // Custom validation to ensure correct fields based on period type
    @AssertTrue(message = "Month is required for 'MONTHLY' period type")
    private boolean isMonthValidForMonthly() {
        return !"MONTHLY".equals(periodType) || (month != null && !month.isBlank());
    }

    @AssertTrue(message = "Quarter is required for 'QUARTERLY' period type")
    private boolean isQuarterValidForQuarterly() {
        return !"QUARTERLY".equals(periodType) || (quarter != null && !quarter.isBlank());
    }

    // Ensure that the month and quarter fields are not provided simultaneously
    @AssertTrue(message = "Month and Quarter cannot be provided at the same time.")
    private boolean isMonthAndQuarterValid() {
        return !(month != null && !month.isBlank() && quarter != null && !quarter.isBlank());
    }
}
