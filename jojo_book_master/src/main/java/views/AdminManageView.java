package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fonts.*;
import model.*;

public class AdminManageView extends AdminView implements ActionListener {

    private JLabel featureNotationLabel;
    private JButton manageButton;
    private JButton listButton;

    private JLabel userIdLabel;
    private JLabel userNameLabel;
    private JLabel userPasswordLabel;
    private JLabel userDepartmentLabel;
    private JLabel userTypeLabel;

    private JTextField userIdTextField;
    private JTextField userNameTextField;
    private JTextField userDepartmentTextField;
    private JTextField userPasswordTextField;

    private JComboBox typeBox;

    private JLabel dropUserLabel;
    private JTextField dropUserIDTextField;

    private JButton addUserButton;
    private JButton dropUserButton;

    private JButton bookButton;
    private JButton borrowButton;
    private JButton returnButton;
    private JButton userButton;

    private JTable dataTable;
    private JScrollPane tableHolderScrollPane;

    private boolean tapBarSwitch;

    public AdminManageView() {
        super();
        placeSubComponents();
        tapBarSwitch = true;
        placeManageComponents();
        iconLabel.setVisible(false);
    }

    private void placeSubComponents() {

        // Manage User Label
        featureNotationLabel = new JLabel("管理用户");
        featureNotationLabel.setFont(new jojoSubtitleFont());
        featureNotationLabel.setBounds(50, 120, 200, 70);
        this.add(featureNotationLabel);

        // Manage Button
        manageButton = new JButton("用户管理");
        manageButton.setFont(new jojoCommentFont());
        manageButton.setBounds(510, 140, 100, 35);
        manageButton.setActionCommand("manageButton");
        manageButton.addActionListener(this);
        this.add(manageButton);

        // List Button
        listButton = new JButton("信息列表");
        listButton.setFont(new jojoCommentFont());
        listButton.setBounds(610, 140, 100, 35);
        listButton.setActionCommand("listButton");
        listButton.addActionListener(this);
        this.add(listButton);

        // User ID Label
        userIdLabel = new JLabel("借书证ID");
        userIdLabel.setFont(new jojoPlainTextFont());
        userIdLabel.setBounds(100, 200, 150, 50);

        // User Name Label
        userNameLabel = new JLabel("用户名");
        userNameLabel.setFont(new jojoPlainTextFont());
        userNameLabel.setBounds(100, 250, 150, 50);

        // User Password Label
        userPasswordLabel = new JLabel("用户密码");
        userPasswordLabel.setFont(new jojoPlainTextFont());
        userPasswordLabel.setBounds(100, 300, 150, 50);

        // User Department Label
        userDepartmentLabel = new JLabel("部门");
        userDepartmentLabel.setFont(new jojoPlainTextFont());
        userDepartmentLabel.setBounds(100, 350, 150, 50);

        // User type Label
        userTypeLabel = new JLabel("类别");
        userTypeLabel.setFont(new jojoPlainTextFont());
        userTypeLabel.setBounds(100, 400, 150, 50);

        // User ID Text Field
        userIdTextField = new JTextField();
        userIdTextField.setFont(new jojoPlainTextFont());
        userIdTextField.setBounds(250, 200, 200, 50);

        // User Name Text Field
        userNameTextField = new JTextField();
        userNameTextField.setFont(new jojoPlainTextFont());
        userNameTextField.setBounds(250, 250, 200, 50);

        // User Password Text Field
        userPasswordTextField = new JTextField();
        userPasswordTextField.setFont(new jojoPlainTextFont());
        userPasswordTextField.setBounds(250, 300, 200, 50);

        // User Department Text Field
        userDepartmentTextField = new JTextField();
        userDepartmentTextField.setFont(new jojoPlainTextFont());
        userDepartmentTextField.setBounds(250, 350, 200, 50);

        // User Department ComboBox
        String[] listData = new String[]{"本科生", "研究生", "教师", "其他"};
        typeBox = new JComboBox<String>(listData);
        typeBox.setFont(new jojoPlainTextFont());
        typeBox.setBounds(250, 400, 200, 50);

        // Drop User Label
        dropUserLabel = new JLabel("用户ID搜索");
        dropUserLabel.setFont(new jojoPlainTextFont());
        dropUserLabel.setBounds(500, 250, 150, 50);

        // Drop User Name TextField
        dropUserIDTextField = new JTextField();
        dropUserIDTextField.setFont(new jojoPlainTextFont());
        dropUserIDTextField.setBounds(500, 300, 200, 50);

        // Add User Button
        addUserButton = new JButton("添加");
        addUserButton.setFont(new jojoPlainTextFont());
        addUserButton.setBounds(250, 480, 100, 40);
        addUserButton.setActionCommand("addUserButton");
        addUserButton.addActionListener(this);

        // Drop User Button
        dropUserButton = new JButton("删除");
        dropUserButton.setFont(new jojoPlainTextFont());
        dropUserButton.setBounds(500, 350, 100, 40);
        dropUserButton.setActionCommand("dropUserButton");
        dropUserButton.addActionListener(this);

        // Book Button
        bookButton = new JButton("图书");
        bookButton.setFont(new jojoPlainTextFont());
        bookButton.setBounds(50, 200, 100, 50);
        bookButton.setActionCommand("bookButton");
        bookButton.addActionListener(this);

        // Borrow Button
        borrowButton = new JButton("借出");
        borrowButton.setFont(new jojoPlainTextFont());
        borrowButton.setBounds(50, 300, 100, 50);
        borrowButton.setActionCommand("borrowButton");
        borrowButton.addActionListener(this);

        // Return Button
        returnButton = new JButton("归还");
        returnButton.setFont(new jojoPlainTextFont());
        returnButton.setBounds(50, 350, 100, 50);
        returnButton.setActionCommand("returnButton");
        returnButton.addActionListener(this);

        // User Button
        userButton = new JButton("用户");
        userButton.setFont(new jojoPlainTextFont());
        userButton.setBounds(50, 250, 100, 50);
        userButton.setActionCommand("userButton");
        userButton.addActionListener(this);

        // Data Table
        dataTable = new JTable();
        dataTable.setFont(new jojoCommentFont());
        dataTable.setSize(550, 1000);
        dataTable.setRowHeight(20);

        // Table Holder Scroll Pane
        tableHolderScrollPane = new JScrollPane();
        tableHolderScrollPane.setBounds(175, 200, 550, 300);
        tableHolderScrollPane.add(dataTable);

    }


    private void placeManageComponents() {
        this.add(userIdLabel);
        this.add(userNameLabel);
        this.add(userDepartmentLabel);
        this.add(userPasswordLabel);
        this.add(userTypeLabel);

        this.add(userIdTextField);
        this.add(userNameTextField);
        this.add(userDepartmentTextField);
        this.add(userPasswordTextField);
        this.add(typeBox);

        this.add(dropUserLabel);
        this.add(dropUserIDTextField);
        this.add(addUserButton);
        this.add(dropUserButton);
    }

    private void removeManageComponents() {
        this.remove(userIdLabel);
        this.remove(userNameLabel);
        this.remove(userDepartmentLabel);
        this.remove(userPasswordLabel);
        this.remove(userTypeLabel);

        this.remove(userIdTextField);
        this.remove(userNameTextField);
        this.remove(userDepartmentTextField);
        this.remove(userPasswordTextField);
        this.remove(typeBox);

        this.remove(dropUserLabel);
        this.remove(dropUserIDTextField);
        this.remove(addUserButton);
        this.remove(dropUserButton);
    }

    private void placeListComponents() {
        this.add(bookButton);
        this.add(borrowButton);
        this.add(returnButton);
        this.add(userButton);
        this.add(tableHolderScrollPane);
    }

    private void removeListComponents() {
        this.remove(bookButton);
        this.remove(borrowButton);
        this.remove(returnButton);
        this.remove(userButton);
        this.remove(tableHolderScrollPane);
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

    private void handleAddUser() {
        String[] newDataContainer = new String[5];
        newDataContainer[0] = userIdTextField.getText();
        newDataContainer[1] = userNameTextField.getText();
        newDataContainer[2] = userDepartmentTextField.getText();
        newDataContainer[3] = userPasswordTextField.getText();
        newDataContainer[4] = Integer.toString(typeBox.getSelectedIndex() + 1);

        if (jojoDataManager.sharedInstance.addReader(newDataContainer)) {
            userNameTextField.setText("");
            userIdTextField.setText("");
            userDepartmentTextField.setText("");
            userPasswordTextField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "创建用户失败.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDropUser() {
        /*int numberOfUnreturnedBooks =
                jojoDataManager.sharedInstance.userBorrowRecord(dropUserIDTextField.getText());
        if (numberOfUnreturnedBooks == -1) {
            System.out.println("Reader not found.");
            JOptionPane.showMessageDialog(null, "Reader not found.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (numberOfUnreturnedBooks > 0) {
            String warning = "The reader have " + String.valueOf(numberOfUnreturnedBooks) +
            " books kept, still drop?";
            int choice = JOptionPane.showConfirmDialog(null, warning,
                    "jojo图书管理", JOptionPane.YES_NO_OPTION);
            if (choice == 1) return;
        }*/
        if (jojoDataManager.sharedInstance.dropReader(dropUserIDTextField.getText())) {
            dropUserIDTextField.setText("");
            JOptionPane.showMessageDialog(null, "用户已经删除.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "该用户不存在.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBookButton() {
        int maxSize = jojoDataManager.sharedInstance.maxItemSize;
        String[][] tableData = new String[maxSize][9];
        if (jojoDataManager.sharedInstance.selectAllBooks(tableData)) {
            System.out.println("Table book data fetched");
        } else {
            JOptionPane.showMessageDialog(null, "无法获得图书数据.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] attributeName = {"书号", "类别", "书名", "出版社","出版年份", "作者", "价格", "馆藏量", "库存量"};
        reloadTable(tableData, attributeName);
    }

    private void handleUserButton() {
        int maxSize = jojoDataManager.sharedInstance.maxItemSize;
        String[][] tableData = new String[maxSize][5];
        if (jojoDataManager.sharedInstance.selectAllUsers(tableData)) {
            System.out.println("Reader data fetched");
        } else {
            JOptionPane.showMessageDialog(null, "无法获得用户数据.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] attributeName = {"卡号", "用户名", "密码", "部门", "类别"};
        reloadTable(tableData, attributeName);
    }

    private void handleBorrowButton() {
        int maxSize = jojoDataManager.sharedInstance.maxItemSize;
        String[][] tableData = new String[maxSize][6];
        if (jojoDataManager.sharedInstance.selectAllBorrows(tableData)) {
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
        if (jojoDataManager.sharedInstance.selectAllReturns(tableData)) {
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
            case "manageButton":
                if (!tapBarSwitch) {
                    removeListComponents();
                    placeManageComponents();
                    tapBarSwitch = !tapBarSwitch;
                    this.repaint();
                }
                break;
            case "listButton":
                if (tapBarSwitch) {
                    removeManageComponents();
                    placeListComponents();
                    tapBarSwitch = !tapBarSwitch;
                    handleBookButton();
                    this.repaint();
                }
                break;
            case "addUserButton":
                handleAddUser();
                break;
            case "dropUserButton":
                handleDropUser();
                break;
            case "bookButton":
                handleBookButton();
                break;
            case "borrowButton":
                handleBorrowButton();
                break;
            case "returnButton":
                handleReturnButton();
                break;
            case "userButton":
                handleUserButton();
                break;
        }
    }
}
