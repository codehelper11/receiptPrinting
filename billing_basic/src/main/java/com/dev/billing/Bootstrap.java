package com.dev.billing;

import com.dev.billing.service.BillingManager;

/**
 * User: droid
 * Date: 2/2/14
 * Time: 11:16 PM
 */
public class Bootstrap {

    public static void main(String[] args) {
        new BillingManager().generateBills();
    }
}
