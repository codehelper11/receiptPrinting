package com.dev.billing.service;

import com.dev.billing.entity.ReceiptItem;
import com.dev.billing.parser.CsvDataParser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return receiptItemParser.parseData("/home/vinay/work/intellij_workspace/billing_tactical/billing_basic/src/main/resources/receipt_data.csv", ReceiptItem.class);
        } catch (IOException e) {
            throw new RuntimeException("could not read billing data", e);
        }
    }


    public void generateReceipts() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("report_template/isha_reciept_template.jrxml")) {
            Map parameters = new HashMap();
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JRBeanCollectionDataSource dataSource;
            Collection<ReceiptItem> receiptItems = getAllReceipts();
            for (ReceiptItem receiptItem : receiptItems) {
                System.out.println("Going to generate receipt " + receiptItem.getSerialNumber());
                parameters.put("serialNumber", receiptItem.getSerialNumber());
                parameters.put("date", receiptItem.getDate());
                parameters.put("name", receiptItem.getName());
                parameters.put("address", receiptItem.getAddress());
                parameters.put("phoneNumber", receiptItem.getPhoneNumber());
                parameters.put("emailAddress", receiptItem.getEmailAddress());
                parameters.put("amountInWords", receiptItem.getAmountInWords());
                parameters.put("paymentType", receiptItem.getPaymentType());
                parameters.put("chequeDate", receiptItem.getChequeDate());
                parameters.put("bankName", receiptItem.getBankName());
                parameters.put("bankBranch", receiptItem.getBankBranch());
                parameters.put("pan", receiptItem.getPan());
                parameters.put("amount", receiptItem.getAmount()+"/-");
                dataSource = new JRBeanCollectionDataSource(receiptItems);
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
