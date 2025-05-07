package com.pfms.pfms_analytics.controller;

import com.pfms.pfms_analytics.request.ReportRequest;
import com.pfms.pfms_analytics.response.FinancialReport;
import com.pfms.pfms_analytics.service.FinancialReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Financial Report", description = "Financial Report API")
public class FinancialReportController {

    private final FinancialReportService financialReportService;

    public FinancialReportController(FinancialReportService financialReportService) {
        this.financialReportService = financialReportService;
    }

    @PostMapping("/report")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Generate Financial Report", description = "Generates a financial report based on the provided request.")
    public FinancialReport generateReport(@Valid @RequestBody ReportRequest reportRequest) {
        return financialReportService.generateReport(reportRequest);
    }

}
