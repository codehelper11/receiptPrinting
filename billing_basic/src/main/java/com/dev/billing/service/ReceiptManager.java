package com.dev.billing.service;

import com.dev.billing.entity.ReceiptItem;
import com.dev.billing.parser.CsvDataParser;
import com.dev.billing.util.FileConstants;
import com.dev.billing.util.Formatter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * User: droid
 * Date: 30/1/14
 * Time: 10:54 PM
 */
public class ReceiptManager {

    private CsvDataParser<ReceiptItem> receiptItemParser;

    public ReceiptManager() {
        this.receiptItemParser = new CsvDataParser<>();
    }

    public List<ReceiptItem> getAllReceipts() {
        try {
            return receiptItemParser.parseData(FileConstants.RECEIPT_FILE, ReceiptItem.class);
        } catch (IOException e) {
            throw new RuntimeException("could not read billing data", e);
        }
    }


    public void generateReceipts() {
        String message = "as voluntary contribution towards the general fund of the trust.";
        String cashMessage = "(received in cash) " + message;
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("report_template/isha_reciept_template.jrxml")) {
            Map parameters = new HashMap();
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JRBeanCollectionDataSource dataSource;
            Collection<ReceiptItem> receiptItems = getAllReceipts();
            for (ReceiptItem receiptItem : receiptItems) {
                List<ReceiptItem> items = new ArrayList<>();
                items.add(receiptItem);
                System.out.println("Going to generate receipt " + receiptItem.getSerialNumber());
                parameters.put("serialNumber", receiptItem.getSerialNumber());
                parameters.put("date", receiptItem.getDate());
                parameters.put("name", receiptItem.getName());
                parameters.put("address", receiptItem.getAddress());
                parameters.put("phoneNumber", receiptItem.getPhoneNumber());
                parameters.put("emailAddress", receiptItem.getEmailAddress());
                parameters.put("amountInWords", Formatter.covertNumberToWord(Integer.parseInt(receiptItem.getAmount()))+" Only");
                parameters.put("paymentType", receiptItem.getPaymentType());
                parameters.put("chequeDate", receiptItem.getChequeDate());
                parameters.put("bankName", receiptItem.getBankName());
                parameters.put("bankBranch", receiptItem.getBankBranch());
                parameters.put("pan", receiptItem.getPan());
                parameters.put("amount", Formatter.formatNumber(receiptItem.getAmount()) + "/-");
                parameters.put("disclaimer", "cash".equalsIgnoreCase(receiptItem.getPaymentType()) ? cashMessage : message);
                dataSource = new JRBeanCollectionDataSource(items);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                JasperExportManager.exportReportToPdfFile(jasperPrint, String.format("receipt_%1$s.pdf", receiptItem.getSerialNumber()));
                parameters.clear();
                System.out.println("Generated bill " + receiptItem.getSerialNumber());
            }
        } catch (JRException e) {
            throw new RuntimeException("error generating Receipt", e);
        } catch (IOException e) {
            throw new RuntimeException("error reading receipt template", e);
        }
    }
}
