package com.dev.billing.service;

import com.dev.billing.entity.Bill;
import com.dev.billing.entity.BillItem;
import com.dev.billing.entity.ProductItem;
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
public class BillingManager {

    private CsvDataParser<BillItem> billDataParser;
    private ReferenceDataManager referenceDataManager;

    public BillingManager() {
        referenceDataManager = new ReferenceDataManager();
        this.billDataParser = new CsvDataParser<>();
    }

    public List<BillItem> getAllBillItems() {
        try {
            return billDataParser.parseData("billing_data.csv", BillItem.class);
        } catch (IOException e) {
            throw new RuntimeException("could not read billing data", e);
        }
    }

    public Collection<Bill> getBills() {
        Map<Integer, ProductItem> productsMap = referenceDataManager.getAllProductsMap();
        Map<String, Bill> billsMap = new HashMap<>();
        for (BillItem item : getAllBillItems()) {
            if (billsMap.get(item.getBillNo()) == null) {
                billsMap.put(item.getBillNo(), new Bill(item.getBillNo(), item.getBillingDate()));
            }
            ProductItem product = productsMap.get(item.getProductId());
            billsMap.get(item.getBillNo()).addItem(product.getProductName(), item.getProductQuantity(), product.getPrice(), product.getVatPercentage());
        }
        System.out.println(billsMap.values());
        return billsMap.values();
    }

    public void generateBills() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("report_template/invoice_template.jrxml")) {
            Map parameters = new HashMap();
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JRBeanCollectionDataSource dataSource;
            Collection<Bill> bills = getBills();
            for (Bill aBill : bills) {
                System.out.println("Going to generate bill " + aBill.getBillNo());
                parameters.put("billNo", aBill.getBillNo());
                parameters.put("billDate", aBill.getBillingDate());
                parameters.put("customerName", "CASH");
                parameters.put("totalVat", aBill.getTotalVat());
                parameters.put("totalExcludingVat", aBill.getTotalExcludingVat());
                parameters.put("billTotal", aBill.getTotalExcludingVat() + aBill.getTotalVat());
                dataSource = new JRBeanCollectionDataSource(aBill.getItems());
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                JasperExportManager.exportReportToPdfFile(jasperPrint, String.format("bill_%1$s.pdf", aBill.getBillNo()));
                parameters.clear();
                System.out.println("Generated bill " + aBill.getBillNo());
            }
        } catch (JRException e) {
           throw new RuntimeException("error generation invoice",e);
        } catch (IOException e) {
            throw new RuntimeException("error reading invoice template",e);
        }
    }
}
