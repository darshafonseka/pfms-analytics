package com.pfms.pfms_analytics.service;

import com.pfms.pfms_analytics.request.ReportRequest;
import com.pfms.pfms_analytics.response.FinancialReport;

public interface FinancialReportService {

    /**
     * Generates a financial report based on the provided report request.
     *
     * @param reportRequest The request containing the parameters for generating the report.
     * @return A FinancialReport object containing the generated report data.
     */
    FinancialReport generateReport(ReportRequest reportRequest);

}
