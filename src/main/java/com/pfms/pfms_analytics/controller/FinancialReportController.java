package com.pfms.pfms_analytics.controller;

import com.pfms.pfms_analytics.request.ReportRequest;
import com.pfms.pfms_analytics.response.FinancialReport;
import com.pfms.pfms_analytics.service.FinancialReportService;
import com.pfms.pfms_analytics.utils.PdfReportGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api")
@Tag(name = "Financial Report", description = "Financial Report API")
@Slf4j
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

    @PostMapping("/report/pdf")
    @Operation(summary = "Download Financial Report as PDF")
    public ResponseEntity<byte[]> downloadPdf(@Valid @RequestBody ReportRequest reportRequest) {
        try {
            FinancialReport report = financialReportService.generateReport(reportRequest);
            byte[] pdfBytes = PdfReportGenerator.generate(report);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=financial-report.pdf")
                    .header("Content-Type", "application/pdf")
                    .body(pdfBytes);
        } catch (Exception e) {
            log.error("Failed to generate PDF report", e);
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
