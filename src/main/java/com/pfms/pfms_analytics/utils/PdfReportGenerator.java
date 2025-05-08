package com.pfms.pfms_analytics.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.pfms.pfms_analytics.response.FinancialReport;

import java.io.ByteArrayOutputStream;

public class PdfReportGenerator {

    public static byte[] generate(FinancialReport report) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font textFont = new Font(Font.HELVETICA, 11);

        // Title
        document.add(new Paragraph("Financial Report", titleFont));
        document.add(new Paragraph("User ID: " + report.getUserId(), textFont));
        document.add(new Paragraph("Period Type: " + report.getPeriodType(), textFont));
        document.add(new Paragraph("Period: " + report.getPeriod().getStartDate() + " to " + report.getPeriod().getEndDate(), textFont));
        document.add(Chunk.NEWLINE);

        // Summary Table
        PdfPTable summaryTable = new PdfPTable(3);
        summaryTable.setWidthPercentage(100);
        summaryTable.addCell(getCell("Total Income", headerFont));
        summaryTable.addCell(getCell("Total Expenses", headerFont));
        summaryTable.addCell(getCell("Net Balance", headerFont));
        summaryTable.addCell(getCell(String.valueOf(report.getSummary().getTotalIncome()), textFont));
        summaryTable.addCell(getCell(String.valueOf(report.getSummary().getTotalExpenses()), textFont));
        summaryTable.addCell(getCell(String.valueOf(report.getSummary().getNetBalance()), textFont));
        document.add(summaryTable);
        document.add(Chunk.NEWLINE);

        // Income Breakdown
        document.add(new Paragraph("Income Breakdown", headerFont));
        PdfPTable incomeTable = new PdfPTable(2);
        incomeTable.setWidthPercentage(100);
        incomeTable.addCell(getCell("Source", headerFont));
        incomeTable.addCell(getCell("Amount", headerFont));
        for (var income : report.getIncomeBreakdown()) {
            incomeTable.addCell(getCell(income.getSource(), textFont));
            incomeTable.addCell(getCell(String.valueOf(income.getAmount()), textFont));
        }
        document.add(incomeTable);
        document.add(Chunk.NEWLINE);

        // Expense Breakdown
        document.add(new Paragraph("Expense Breakdown", headerFont));
        PdfPTable expenseTable = new PdfPTable(2);
        expenseTable.setWidthPercentage(100);
        expenseTable.addCell(getCell("Category", headerFont));
        expenseTable.addCell(getCell("Amount", headerFont));
        for (var expense : report.getExpenseBreakdown()) {
            expenseTable.addCell(getCell(expense.getCategory(), textFont));
            expenseTable.addCell(getCell(String.valueOf(expense.getAmount()), textFont));
        }
        document.add(expenseTable);
        document.add(Chunk.NEWLINE);

        // Top Spending Categories
        document.add(new Paragraph("Top Spending Categories", headerFont));
        for (String cat : report.getTopSpendingCategories()) {
            document.add(new Paragraph("• " + cat, textFont));
        }

        document.close();
        return baos.toByteArray();
    }

    private static PdfPCell getCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        return cell;
    }
}
