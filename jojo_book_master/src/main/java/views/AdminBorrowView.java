package views;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
import java.text.ParseException;

import fonts.*;
        import model.*;

public class AdminBorrowView extends AdminView implements ActionListener {

    private JLabel featureNotationLabel;
    private JLabel cnoLabel;
    private JLabel bnoLabel;

    private JTextField borrowTextField;
    private JTextField returnTextField;
    private JTextField cnoTextField;
    private JTextField bnoTextField;

    private JButton borrowButton;
    private JButton searchButton;
    private JButton returnButton;

    private JTable dataTable;
    private JScrollPane tableHolderScrollPane;

    public AdminBorrowView() {
        super();
        placeSubComponents();
        iconLabel.setVisible(false);
    }

    private void placeSubComponents() {
        // Borrow and Return Label
        featureNotationLabel = new JLabel("借/还书");
        featureNotationLabel.setFont(new jojoSubtitleFont());
        featureNotationLabel.setBounds(50, 120, 250, 50);
        this.add(featureNotationLabel);

        // cno Label
        cnoLabel = new JLabel("借书证号:");
        cnoLabel.setFont(new jojoPlainTextFont());
        cnoLabel.setBounds(50, 250, 80, 30);
        this.add(cnoLabel);
        // cno Textfield
        cnoTextField = new JTextField();
        cnoTextField.setFont(new jojoPlainTextFont());
        cnoTextField.setBounds(50, 280, 100, 30);
        this.add(cnoTextField);
        // cno search Button
        searchButton = new JButton("查询");
        searchButton.setFont(new jojoPlainTextFont());
        searchButton.setForeground(Color.red);
        searchButton.setBounds(50, 315, 80, 30);
        searchButton.setActionCommand("searchButton");
        searchButton.addActionListener(this);
        this.add(searchButton);

        // Bno Label
        bnoLabel = new JLabel("输入书号:");
        bnoLabel.setFont(new jojoPlainTextFont());
        bnoLabel.setBounds(340, 120, 100, 30);
        this.add(bnoLabel);

        // Bno Text Field
        bnoTextField = new JTextField();
        bnoTextField.setFont(new jojoPlainTextFont());
        bnoTextField.setBounds(420, 120, 150, 30);
        this.add(bnoTextField);

        // borrow Button
        borrowButton = new JButton("借书");
        borrowButton.setFont(new jojoPlainTextFont());
        borrowButton.setBounds(340, 155, 100, 30);
        borrowButton.setActionCommand("borrowButton");
        borrowButton.addActionListener(this);
        this.add(borrowButton);

        // Return Button
        returnButton = new JButton("还书");
        returnButton.setFont(new jojoPlainTextFont());
        returnButton.setBounds(460, 155, 100, 30);
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
        tableHolderScrollPane.setBounds(175, 200, 550, 380);
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

    private void handleSearchButton() {
        int maxSize = jojoDataManager.sharedInstance.maxItemSize;
        String[][] tableData = new String[maxSize][6];
        String cno = cnoTextField.getText();
        if (jojoDataManager.sharedInstance.fetchReaderBorrowRecord_Admin(tableData, cno)) {
            System.out.println("User borrow data fetched");
        } else {
            JOptionPane.showMessageDialog(null, "无法获取借书信息.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] attributeName = {"借书证号", "书号", "书名", "借书日期", "应还日期", "图书管理员"};
        reloadTable(tableData, attributeName);
    }
    private void handleBorrowButton() throws ParseException {
        String cno = cnoTextField.getText();
        String bno = bnoTextField.getText();
        if (jojoDataManager.sharedInstance.AdminborrowBook(bno, cno)) {
            System.out.println("Book borrowed");
            JOptionPane.showMessageDialog(null, "借书成功.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
            bnoTextField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "借书失败.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleReturnButton() {
        String cno = cnoTextField.getText();
        String bno = bnoTextField.getText();
        if (jojoDataManager.sharedInstance.AdminreturnBook(bno, cno)) {
            System.out.println("Book returned");
            bnoTextField.setText("");
            JOptionPane.showMessageDialog(null, "还书成功.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "还书失败，请确认书号，以及是否在之前已归还.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        String command = e.getActionCommand();
        switch (command) {
            case "searchButton":
                handleSearchButton();
                break;
            case "borrowButton":
                try {
                    handleBorrowButton();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                break;
            case "returnButton":
                handleReturnButton();
                break;
        }
    }
}
