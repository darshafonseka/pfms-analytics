package com.pfms.pfms_analytics.service.client;

import com.pfms.pfms_analytics.response.ExpenseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "expenseClient", url = "${external.income-expense-budget-service.url}")
public interface ExpenseClient {
        @GetMapping("/api/expense")
        List<ExpenseResponse> getAllExpenses(
                @RequestParam("userId") Integer userId,
                @RequestParam(value = "startDate", required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                @RequestParam(value = "endDate", required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
        );
}