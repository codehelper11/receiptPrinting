package com.dev.billing.service;

import com.dev.billing.util.FileConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * User: droid
 * Date: 7/2/14
 * Time: 8:55 PM
 */
public class BillEntryService {

    public void addBillEntries(List<String> entries) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(FileConstants.BILL_DATA_FILE),true))) {
            for (String entry : entries) {
                checkArgument(entry.split(FileConstants.DELIMITER).length == 4, "Bill Data is incorrect [" + entry + "]");
                System.out.println("processing bill entry ["+entry+"]");
                bufferedWriter.append(entry);
                bufferedWriter.append(String.format("%n"));
            }
            System.out.println("Added bill entries");
        } catch (IOException e) {
            throw new RuntimeException("count not add bill data to file", e);
        }
    }
}
