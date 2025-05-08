package com.pfms.pfms_analytics.service.client;

import com.pfms.pfms_analytics.config.FeignConfig;
import com.pfms.pfms_analytics.response.IncomeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "incomeClient", url = "${external.income-expense-budget-service.url}", configuration = FeignConfig.class)
public interface IncomeClient {
    @GetMapping("/api/income")
    List<IncomeResponse> getAllIncomes(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
}