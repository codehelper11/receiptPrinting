package com.dev.billing.service;

import com.dev.billing.entity.ProductItem;
import com.dev.billing.parser.CsvDataParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return csvDataParser.parseData("product_details.csv", ProductItem.class);
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
