package com.dev.billing.swing;

import com.dev.billing.entity.ProductItem;
import com.dev.billing.service.BillEntryService;
import com.dev.billing.service.ReferenceDataManager;
import com.dev.billing.util.FileConstants;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Objects.firstNonNull;

/**
 * User: droid
 * Date: 6/2/14
 * Time: 7:41 PM
 */
public class BillEntryView extends JFrame {

    private static final String DATE_FORMAT_PROPERTY = "date.format";
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    private ReferenceDataManager dataManager;
    private BillEntryService billEntryService;
    private JComboBox<ProductItem> productsDropdown;
    private JTextField billNoField;
    private JTextField dateField;
    private JComboBox quantityDropDown;
    private JTextArea billItems;
    private JButton addButton;
    private JButton submitButton;
    private JTextField totalAmount;
    private List<String> billEntryList = new ArrayList<>();
    private boolean isSubmitted = false;
    private JTextField errorBox;

    public BillEntryView() {
        setLayout(new BorderLayout());
        dataManager = new ReferenceDataManager();
        billEntryService = new BillEntryService();
        Box row1 = Box.createHorizontalBox();
        initBillNo(row1);
        initDate(row1);
        add(row1, BorderLayout.NORTH);

        Box row2 = Box.createVerticalBox();
        errorBox = new JTextField();
        errorBox.setForeground(Color.RED);
        errorBox.setEditable(false);
        row2.add(errorBox);
        row2.add(Box.createVerticalStrut(10));
        Box column2 = Box.createHorizontalBox();
        initProductsList(column2);
        initQuantityList(column2);
        initAddButton(column2);
        row2.add(column2);
        row2.add(Box.createVerticalStrut(10));
        initBillItems(row2);
        row2.add(Box.createVerticalStrut(50));


        Box totalRow = Box.createHorizontalBox();
        totalRow.add(new JLabel("Total"));
        totalAmount = new JTextField();
        totalAmount.setText("0.0");
        totalRow.add(totalAmount);
        row2.add(totalRow);
        row2.add(Box.createVerticalStrut(50));
        add(row2, BorderLayout.CENTER);

        initSubmitButton();
        add(submitButton, BorderLayout.SOUTH);
    }

    private void initBillItems(Box box) {
        billItems = new JTextArea();
        billItems.setBorder(new LineBorder(Color.BLACK));
        billItems.append("Product Id |Product Name |Quantity|MRP|TOTAL\n");
        box.add(billItems);

    }

    private void initAddButton(Box box) {
        addButton = new JButton("Add Item");
        box.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorBox.setText("");
                if (billNoField.getText() == null || billNoField.getText().trim().equals("")) {
                    errorBox.setText("Bill Number is Required");
                    return;
                }
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(firstNonNull(System.getProperty(DATE_FORMAT_PROPERTY), DEFAULT_DATE_FORMAT));
                    simpleDateFormat.setLenient(false);
                    simpleDateFormat.parse(dateField.getText().trim());
                    if (dateField.getText().trim().length() != 10) {
                        errorBox.setText(String.format("Date [%1$s] is incorrect", dateField.getText()));
                        return;
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    errorBox.setText(String.format("Date [%1$s] is incorrect", dateField.getText()));
                    return;
                }
                ProductItem selectedProduct = (ProductItem) productsDropdown.getSelectedItem();
                Integer quantity = (Integer) quantityDropDown.getSelectedItem();
                billItems.append(String.format("%1$s    |%2$s   |%3$s   |%4$s   |%5$s\n", selectedProduct.getId(), selectedProduct.getProductName(),
                        quantity, selectedProduct.getPrice(), selectedProduct.getPrice() * quantity));
                double currentAmount = Double.parseDouble(totalAmount.getText());
                totalAmount.setText(String.valueOf(currentAmount + selectedProduct.getPrice() * quantity));
                billEntryList.add(billNoField.getText().trim() + FileConstants.DELIMITER +
                        selectedProduct.getId() + FileConstants.DELIMITER +
                        quantity + FileConstants.DELIMITER +
                        dateField.getText().trim());
            }
        });
    }

    private void initSubmitButton() {
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isSubmitted) {
                    isSubmitted = true;
                    submitButton.setText("WAIT.....");
                    billEntryService.addBillEntries(billEntryList);
                    submitButton.setText("Create New Bill");
                } else if (isSubmitted && submitButton.getText().equals("Create New Bill")) {
                    resetBill();
                }

            }
        });
    }

    private void resetBill() {
        billNoField.setText("");
        dateField.setText("");
        quantityDropDown.setSelectedIndex(0);
        billItems.setText("");
        billEntryList.clear();
        submitButton.setText("Submit");
        totalAmount.setText("0.0");
        isSubmitted = false;
    }

    private void initBillNo(Box box) {
        billNoField = new JTextField();
        box.add(new JLabel("Bill No"));
        box.add(billNoField);

    }

    private void initDate(Box box) {
        dateField = new JTextField();
        box.add(new JLabel("Date (dd/mm/yyyy)"));
        box.add(dateField);

    }


    private void initProductsList(Box box) {
        productsDropdown = new JComboBox();
        for (ProductItem productItem : dataManager.getAllProducts()) {
            productsDropdown.addItem(productItem);
        }
        productsDropdown.setRenderer(new ProductListRenderer());
        AutoCompleteDecorator.decorate(productsDropdown, new ObjectToStringConverter() {
            @Override
            public String getPreferredStringForItem(Object obj) {
                return obj instanceof ProductItem ? ((ProductItem) obj).getProductName() + " (" + ((ProductItem) obj).getPrice() + ")" : null;
            }
        });
        box.add(new JLabel("Product"));
        box.add(new JScrollPane(productsDropdown));
    }

    private void initQuantityList(Box box) {
        quantityDropDown = new JComboBox();
        for (int i = 1; i <= 50; i++) {
            quantityDropDown.addItem(i);
        }
        box.add(new JLabel("Quantity"));
        box.add(new JScrollPane(quantityDropDown));
    }

    private class ProductListRenderer implements ListCellRenderer<ProductItem> {
        private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList<? extends ProductItem> list, ProductItem value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            renderer.setText(value.getProductName());
            return renderer;
        }
    }

    public static void main(String[] args) {
        SwingConsole.run(new BillEntryView(), 600, 600, "Bill Entry");
    }
}
