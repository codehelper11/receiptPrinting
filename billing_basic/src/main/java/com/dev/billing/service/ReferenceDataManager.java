package com.dev.billing.service;

import com.dev.billing.entity.ProductItem;
import com.dev.billing.parser.CsvDataParser;

import java.io.IOException;
import java.util.*;

/**
 * User: droid
 * Date: 30/1/14
 * Time: 11:28 PM
 */
public class ReferenceDataManager {

    private CsvDataParser<ProductItem> csvDataParser;

    public ReferenceDataManager() {
        this.csvDataParser = new CsvDataParser<>();
    }

    public List<ProductItem> getAllProducts() {
        try {
            List<ProductItem> list = csvDataParser.parseData("product_details.csv", ProductItem.class);
            Collections.sort(list,new Comparator<ProductItem>() {
                @Override
                public int compare(ProductItem o1, ProductItem o2) {
                    return o1.getProductName().compareToIgnoreCase(o2.getProductName());
                }
            });
            return list;
        } catch (IOException e) {
            throw new RuntimeException("error reading product file", e);
        }
    }

    public Map<Integer,ProductItem> getAllProductsMap(){
        Map<Integer,ProductItem> map = new HashMap<>();
        for(ProductItem productItem : getAllProducts()){
            map.put(productItem.getId(),productItem);
        }
        return map;
    }
}
