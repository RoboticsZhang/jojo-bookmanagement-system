package views;

import javax.swing.*;
import java.awt.event.ActionEvent;

import fonts.*;
import model.*;

public class ReaderHistoryView extends ReaderView {

    private JButton borrowButton;
    private JButton returnButton;

    private JTable dataTable;
    private JScrollPane tableHolderScrollPane;

    public ReaderHistoryView() {
        super();
        placeSubComponents();
        handleBorrowButton();
    }

    private void placeSubComponents() {

        // Borrow Button
        borrowButton = new JButton("在借书目");
        borrowButton.setFont(new jojoPlainTextFont());
        borrowButton.setBounds(50, 120, 100, 50);
        borrowButton.setActionCommand("borrowButton");
        borrowButton.addActionListener(this);
        this.add(borrowButton);

        // Return Button
        returnButton = new JButton("还书记录");
        returnButton.setFont(new jojoPlainTextFont());
        returnButton.setBounds(50, 170, 100, 50);
        returnButton.setActionCommand("returnButton");
        returnButton.addActionListener(this);
        this.add(returnButton);

        // Data Table
        dataTable = new JTable();
        dataTable.setFont(new jojoCommentFont());
        dataTable.setSize(550, 1000);
        dataTable.setRowHeight(20);

        // Table Holder Scroll Pane
        tableHolderScrollPane = new JScrollPane();
        tableHolderScrollPane.setBounds(175, 120, 550, 380);
        tableHolderScrollPane.add(dataTable);
        this.add(tableHolderScrollPane);
    }

    private void reloadTable(String[][] tableData, String[] attributes) {
        tableHolderScrollPane.remove(dataTable);
        dataTable = new JTable(tableData, attributes);
        dataTable.setFont(new jojoCommentFont());
        dataTable.setSize(550, 1000);
        dataTable.setRowHeight(20);
        tableHolderScrollPane.setViewportView(dataTable);
        this.repaint();
    }

    private void handleBorrowButton() {
        int maxSize = jojoDataManager.sharedInstance.maxItemSize;
        String[][] tableData = new String[maxSize][6];
        if (jojoDataManager.sharedInstance.fetchReaderBorrowRecord(tableData)) {
            System.out.println("User borrow data fetched");
        } else {
            JOptionPane.showMessageDialog(null, "无法获取借书信息.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] attributeName = {"借书证号", "书号", "书名", "借书日期", "应还日期", "图书管理员"};
        reloadTable(tableData, attributeName);
    }

    private void handleReturnButton() {
        int maxSize = jojoDataManager.sharedInstance.maxItemSize;
        String[][] tableData = new String[maxSize][7];
        if (jojoDataManager.sharedInstance.fetchReaderReturnRecord(tableData)) {
            System.out.println("User return data fetched");
        } else {
            JOptionPane.showMessageDialog(null, "无法获得还书记录.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] attributeName = {"借书证号", "书号", "书名", "借书日期", "还书日期", "是否超时","图书管理员"};
        reloadTable(tableData, attributeName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        String command = e.getActionCommand();
        switch (command) {
            case "borrowButton":
                handleBorrowButton();
                break;
            case "returnButton":
                handleReturnButton();
                break;
        }
    }
}
