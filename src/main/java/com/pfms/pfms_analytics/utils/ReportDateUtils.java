package com.pfms.pfms_analytics.utils;

import com.pfms.pfms_analytics.enums.MonthEnum;
import com.pfms.pfms_analytics.enums.QuarterEnum;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class ReportDateUtils {

    private ReportDateUtils() {
    }

    // Method to calculate start and end dates for a monthly report
    public static LocalDate[] getMonthlyReportDates(int year, MonthEnum monthEnum) {
        Month month = Month.valueOf(monthEnum.name()); // Convert enum to Month
        LocalDate startDate = LocalDate.of(year, month, 1);  // First day of the month
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());  // Last day of the month
        return new LocalDate[]{startDate, endDate};
    }

    // Method to calculate start and end dates for a quarterly report
    public static LocalDate[] getQuarterlyReportDates(int year, QuarterEnum quarter) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        switch (quarter) {
            case Q1 -> {
                startDate = LocalDate.of(year, 1, 1);
                endDate = LocalDate.of(year, 3, 31);
            }
            case Q2 -> {
                startDate = LocalDate.of(year, 4, 1);
                endDate = LocalDate.of(year, 6, 30);
            }
            case Q3 -> {
                startDate = LocalDate.of(year, 7, 1);
                endDate = LocalDate.of(year, 9, 30);
            }
            case Q4 -> {
                startDate = LocalDate.of(year, 10, 1);
                endDate = LocalDate.of(year, 12, 31);
            }
            default -> throw new IllegalArgumentException("Invalid quarter: " + quarter);
        }

        return new LocalDate[]{startDate, endDate};
    }

    // Method to calculate start and end dates for a yearly report
    public static LocalDate[] getYearlyReportDates(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return new LocalDate[]{startDate, endDate};
    }

}
