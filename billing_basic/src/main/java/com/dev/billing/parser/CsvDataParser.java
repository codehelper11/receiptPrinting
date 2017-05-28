package com.dev.billing.parser;

import com.dev.billing.entity.ReceiptItem;
import com.dev.billing.util.FileConstants;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.dev.billing.util.FileUtil.getCompletePath;
import static com.google.common.base.Objects.firstNonNull;

/**
 * User: droid
 * Date: 29/1/14
 * Time: 10:44 PM
 */
public class CsvDataParser<T> {


    private static final String DATE_FORMAT_PROPERTY = "date.format";
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";


    static {
        //register date converter for bean utils
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPattern(firstNonNull(System.getProperty(DATE_FORMAT_PROPERTY), DEFAULT_DATE_FORMAT));
        ConvertUtils.register(dateConverter, Date.class);
    }


    public List<T> parseData(String file, Class<T> aClass) throws IOException {
        List<T> dataList = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     new File(getCompletePath(file))))) {
            String[] propertyNames = parseRow(reader.readLine());
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                System.out.println(String.format("going to parse row [%1$s]",currentLine));
                T dataObject = aClass.newInstance();
                BeanUtils.populate(dataObject, createPropertyMap(propertyNames, parseRow(currentLine)));
                dataList.add(dataObject);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("could not read data", e);
        }
        return dataList;
    }

    private Map<String, Object> createPropertyMap(String[] propertyNames, String[] propertyValues) {
        Map<String, Object> propertyMap = new HashMap<>();
        int i = 0;
        for (String propertyName : propertyNames) {
            propertyMap.put(propertyName, propertyValues[i++]);
        }
        return propertyMap;
    }

    private String[] parseRow(String row) {
        return row.split(FileConstants.DELIMITER);
    }

   public static void main(String[] args) throws IOException {
        List<ReceiptItem> items = new CsvDataParser<ReceiptItem>().parseData("/home/vinay/work/intellij_workspace/billing_tactical/billing_basic/src/main/resources/receipt_data.csv", ReceiptItem.class);
        for (Object item : items) {
            System.out.println(item);
        }
    }
}
