package com.pfms.pfms_analytics.service.impl;

import com.pfms.pfms_analytics.enums.MonthEnum;
import com.pfms.pfms_analytics.enums.PeriodType;
import com.pfms.pfms_analytics.enums.QuarterEnum;
import com.pfms.pfms_analytics.request.ReportRequest;
import com.pfms.pfms_analytics.response.*;
import com.pfms.pfms_analytics.service.FinancialReportService;
import com.pfms.pfms_analytics.service.client.ExpenseClient;
import com.pfms.pfms_analytics.service.client.IncomeClient;
import com.pfms.pfms_analytics.utils.ReportDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FinancialReportServiceImpl implements FinancialReportService {

    private final IncomeClient incomeClient;
    private final ExpenseClient expenseClient;

    public FinancialReportServiceImpl(IncomeClient incomeClient, ExpenseClient expenseClient) {
        this.incomeClient = incomeClient;
        this.expenseClient = expenseClient;
    }

    @Override
    public FinancialReport generateReport(ReportRequest reportRequest) {
        PeriodType periodType = PeriodType.valueOf(reportRequest.getPeriodType());
        int userId = Integer.parseInt(reportRequest.getUserId());
        LocalDate[] periodDates = resolveReportDates(periodType, reportRequest);

        List<IncomeBreakdown> incomeBreakdowns = fetchIncomeBreakdown(userId, periodDates);
        List<ExpenseBreakdown> expenseBreakdowns = fetchExpenseBreakdown(userId, periodDates);

        double totalIncome = incomeBreakdowns.stream().mapToDouble(IncomeBreakdown::getAmount).sum();
        double totalExpense = expenseBreakdowns.stream().mapToDouble(ExpenseBreakdown::getAmount).sum();
        double netBalance = totalIncome - totalExpense;

        FinancialReportSummary summary = new FinancialReportSummary(totalIncome, totalExpense, netBalance);
        FinancialReportPeriod period = new FinancialReportPeriod(periodDates[0], periodDates[1]);

        List<String> top3Categories = getTopSpendingCategories(userId, periodDates);


        return FinancialReport.builder()
                .userId(userId)
                .periodType(periodType.name())
                .period(period)
                .summary(summary)
                .incomeBreakdown(incomeBreakdowns)
                .expenseBreakdown(expenseBreakdowns)
                .topSpendingCategories(top3Categories)
                .build();
    }


    /**
     * Fetches the income breakdown for a given user and date range.
     *
     * @param userId The ID of the user.
     * @param dates  An array containing the start and end dates for the report.
     * @return A list of IncomeBreakdown objects representing the user's income.
     */
    private List<IncomeBreakdown> fetchIncomeBreakdown(int userId, LocalDate[] dates) {
        return incomeClient.getAllIncomes(userId, dates[0], dates[1]).stream()
                .map(income -> new IncomeBreakdown(income.getSource(), income.getAmount().doubleValue()))
                .toList();
    }

    /**
     * Fetches the expense breakdown for a given user and date range.
     *
     * @param userId The ID of the user.
     * @param dates  An array containing the start and end dates for the report.
     * @return A list of ExpenseBreakdown objects representing the user's expenses.
     */
    private List<ExpenseBreakdown> fetchExpenseBreakdown(int userId, LocalDate[] dates) {
        return expenseClient.getAllExpenses(userId, dates[0], dates[1]).stream()
                .map(expense -> new ExpenseBreakdown(expense.getCategory(), expense.getAmount().doubleValue()))
                .toList();
    }

    /**
     * Resolves the start and end dates for the report based on the period type and request parameters.
     *
     * @param periodType The type of period (monthly, quarterly, yearly).
     * @param request    The report request containing the year and other parameters.
     * @return An array containing the start and end dates for the report.
     */
    private LocalDate[] resolveReportDates(PeriodType periodType, ReportRequest request) {
        int year = Integer.parseInt(request.getYear());
        switch (periodType) {
            case MONTHLY -> {
                MonthEnum month = MonthEnum.valueOf(request.getMonth());
                return ReportDateUtils.getMonthlyReportDates(year, month);
            }
            case QUARTERLY -> {
                QuarterEnum quarter = QuarterEnum.valueOf(request.getQuarter());
                return ReportDateUtils.getQuarterlyReportDates(year, quarter);
            }
            case YEARLY -> {
                return ReportDateUtils.getYearlyReportDates(year);
            }
            default -> throw new IllegalArgumentException("Invalid period type: " + periodType);
        }
    }

    /**
     * Fetches the top spending categories for a given user and date range.
     *
     * @param userId The ID of the user.
     * @param dates  An array containing the start and end dates for the report.
     * @return A list of ExpenseBreakdown objects representing the top spending categories.
     */
    private List<String> getTopSpendingCategories(int userId, LocalDate[] dates) {
        return expenseClient.getAllExpenses(userId, dates[0], dates[1]).stream()
                .collect(Collectors.groupingBy(
                        ExpenseResponse::getCategory,
                        Collectors.summingDouble(exp -> exp.getAmount().doubleValue())
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

}
