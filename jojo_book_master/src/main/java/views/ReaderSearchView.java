package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fonts.*;
import model.*;

public class ReaderSearchView extends ReaderView implements ActionListener {

    private JLabel featureNotationLabel;

    private JLabel categoryLabel;
    private JLabel titleLabel;
    private JLabel pressLabel;
    private JLabel authorLabel;
    private JLabel yearLabel;
    private JLabel priceLabel;

    private JTextField categoryTextField;
    private JTextField titleTextField;
    private JTextField pressTextField;
    private JTextField authorTextField;
    private JTextField yearTextField;
    private JComboBox priceBox;

    private JTextField searchNameTextField;
    private JButton searchButton;

    int searchMode = 0;
    private JButton searchNameButton;
    private JButton searchAuthorButton;
    private JButton searchYearButton;

    private JTable dataTable;
    private JScrollPane tableHolderScrollPane;

    public ReaderSearchView() {
        super();
        placeSubComponents();
        //handleSearchNameButton();
    }

    private void placeSubComponents() {
        // category Label
        categoryLabel = new JLabel("类别:");
        categoryLabel.setFont(new jojoPlainTextFont());
        categoryLabel.setBounds(100, 120, 100, 30);
        this.add(categoryLabel);

        // category TextField
        categoryTextField = new JTextField();
        categoryTextField.setFont(new jojoPlainTextFont());
        categoryTextField.setBounds(140, 120, 100, 30);
        this.add(categoryTextField);

        // title Label
        titleLabel = new JLabel("书名:");
        titleLabel.setFont(new jojoPlainTextFont());
        titleLabel.setBounds(250, 120, 100, 30);
        this.add(titleLabel);

        // title TextField
        titleTextField = new JTextField();
        titleTextField.setFont(new jojoPlainTextFont());
        titleTextField.setBounds(290, 120, 100, 30);
        this.add(titleTextField);

        //  press Label
        pressLabel = new JLabel("出版社:");
        pressLabel.setFont(new jojoPlainTextFont());
        pressLabel.setBounds(400, 120, 100, 30);
        this.add(pressLabel);

        // press TextField
        pressTextField = new JTextField();
        pressTextField.setFont(new jojoPlainTextFont());
        pressTextField.setBounds(460, 120, 100, 30);
        this.add(pressTextField);

        //  author Label
        authorLabel = new JLabel("作者:");
        authorLabel.setFont(new jojoPlainTextFont());
        authorLabel.setBounds(100, 160, 100, 30);
        this.add(authorLabel);

        // author TextField
        authorTextField = new JTextField();
        authorTextField.setFont(new jojoPlainTextFont());
        authorTextField.setBounds(140, 160, 100, 30);
        this.add(authorTextField);

        //  year Label
        yearLabel = new JLabel("年份:");
        yearLabel.setFont(new jojoPlainTextFont());
        yearLabel.setBounds(250, 160, 100, 30);
        this.add(yearLabel);

        // year TextField
        yearTextField = new JTextField();
        yearTextField.setFont(new jojoPlainTextFont());
        yearTextField.setBounds(290, 160, 100, 30);
        yearTextField.setText(" - ");
        this.add(yearTextField);

        //  price Label
        priceLabel = new JLabel("价格:");
        priceLabel.setFont(new jojoPlainTextFont());
        priceLabel.setBounds(400, 160, 100, 30);
        this.add(priceLabel);

        //price ComboBox
        String[] listData = new String[]{"", "0-20元", "20-50元", "50-100元", "100元以上"};
        priceBox = new JComboBox<String>(listData);
        priceBox.setFont(new jojoPlainTextFont());
        priceBox.setBounds(460, 160, 100, 30);
        this.add(priceBox);

        // Search Button
        searchButton = new JButton("查询");
        searchButton.setFont(new jojoCommentFont());
        searchButton.setBounds(600, 140, 100, 30);
        searchButton.setActionCommand("searchButton");
        searchButton.addActionListener(this);
        this.add(searchButton);

        // Data Table
        dataTable = new JTable();
        dataTable.setFont(new jojoCommentFont());
        dataTable.setSize(600, 1000);
        dataTable.setRowHeight(20);

        // Table Holder Scroll Pane
        tableHolderScrollPane = new JScrollPane();
        tableHolderScrollPane.setBounds(100, 220, 600, 300);
        tableHolderScrollPane.add(dataTable);
        this.add(tableHolderScrollPane);
    }

    private void reloadTable(String[][] tableData, String[] attributes) {
        tableHolderScrollPane.remove(dataTable);
        dataTable = new JTable(tableData, attributes);
        dataTable.setFont(new jojoCommentFont());
        dataTable.setSize(600, 1000);
        dataTable.setRowHeight(20);
        tableHolderScrollPane.setViewportView(dataTable);
        this.repaint();
    }
    private void bookSearch() {
        int maxSize = jojoDataManager.sharedInstance.maxItemSize;
        String[][] tableData = new String[maxSize][9];
        String[] sql_string = new String[7];
        String[] year = new String[2];

        year = yearTextField.getText().split("-");
        String price = Integer.toString(priceBox.getSelectedIndex());
        sql_string[0] = categoryTextField.getText();
        sql_string[1] = titleTextField.getText();
        sql_string[2] = pressTextField.getText();
        sql_string[3] = authorTextField.getText();
        sql_string[4] = year[0];
        sql_string[5] = year[1];
        sql_string[6] = price;

        if (year.length < 2) {
            JOptionPane.showMessageDialog(null, "年份格式错误.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (jojoDataManager.sharedInstance.searchBook(tableData,sql_string)) {
            System.out.println("Table book data fetched");
        } else {
            JOptionPane.showMessageDialog(null, "无法获得书目信息.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] attributeName = {"书号", "类别", "书名", "出版社","出版年份", "作者", "价格", "馆藏量", "库存量"};
        reloadTable(tableData, attributeName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        String command = e.getActionCommand();
        switch (command) {
            case "searchButton":
                bookSearch();
                break;
        }
    }
}
