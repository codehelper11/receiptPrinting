package com.dev.billing.swing;

import javax.swing.*;

/**
 * User: droid
 * Date: 6/2/14
 * Time: 9:20 PM
 */
public class SwingConsole {

    public static void run(final JFrame frame, final int height, final int width, final String title) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setTitle(title);
                frame.setSize(width, height);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
