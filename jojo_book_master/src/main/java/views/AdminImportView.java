package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fonts.*;
import model.*;

public class AdminImportView extends AdminView implements ActionListener {

    private JLabel featureNotationLabel;
    private JLabel bookNameLabel;
    private JLabel bnoLabel;
    private JLabel priceLabel;
    private JLabel categoryLabel;
    private JLabel publisherLabel;
    private JLabel authorLabel;
    private JLabel yearLabel;
    private JLabel amountLabel;

    private JTextField bookNameTextField;
    private JTextField bnoTextField;
    private JTextField priceTextField;
    private JTextField categoryTextField;
    private JTextField publisherTextField;
    private JTextField authorTextField;
    private JTextField yearTextField;
    private JTextField amountTextField;

    private JButton singleImportButton;
    private JButton searchButton;
    private JButton bulkImportButton;

    public AdminImportView() {
        super();
        placeSubComponents();
        iconLabel.setVisible(false);
    }

    private void placeSubComponents() {
        // Import a Book Label
        featureNotationLabel = new JLabel("单本入库");
        featureNotationLabel.setFont(new jojoSubtitleFont());
        featureNotationLabel.setBounds(50, 120, 200, 70);
        this.add(featureNotationLabel);

        // Bno Label
        bnoLabel = new JLabel("书号");
        bnoLabel.setFont(new jojoPlainTextFont());
        bnoLabel.setBounds(100, 200, 150, 40);
        this.add(bnoLabel);

        // Category Label
        categoryLabel = new JLabel("类别");
        categoryLabel.setFont(new jojoPlainTextFont());
        categoryLabel.setBounds(100, 240, 150, 40);
        this.add(categoryLabel);

        //BookName Label
        bookNameLabel = new JLabel("书名");
        bookNameLabel.setFont(new jojoPlainTextFont());
        bookNameLabel.setBounds(100, 280, 150, 40);
        this.add(bookNameLabel);

        // Publisher Label
        publisherLabel = new JLabel("出版社");
        publisherLabel.setFont(new jojoPlainTextFont());
        publisherLabel.setBounds(100, 320, 150, 50);
        this.add(publisherLabel);

        // Year Label
        yearLabel = new JLabel("出版年份");
        yearLabel.setFont(new jojoPlainTextFont());
        yearLabel.setBounds(100, 360, 150, 50);
        this.add(yearLabel);

        // Author Label
        authorLabel = new JLabel("作者");
        authorLabel.setFont(new jojoPlainTextFont());
        authorLabel.setBounds(100, 400, 150, 50);
        this.add(authorLabel);


        // Price Label
        priceLabel = new JLabel("价格");
        priceLabel.setFont(new jojoPlainTextFont());
        priceLabel.setBounds(100, 440, 150, 50);
        this.add(priceLabel);

        // Amount Label
        amountLabel = new JLabel("新入库数量");
        amountLabel.setFont(new jojoPlainTextFont());
        amountLabel.setBounds(100, 480, 150, 50);
        this.add(amountLabel);


        // Bno Text Field
        bnoTextField = new JTextField();
        bnoTextField.setFont(new jojoPlainTextFont());
        bnoTextField.setBounds(250, 200, 250, 40);
        this.add(bnoTextField);

        // Category Field
        categoryTextField = new JTextField();
        categoryTextField.setFont(new jojoPlainTextFont());
        categoryTextField.setBounds(250, 240, 250, 40);
        this.add(categoryTextField);

        // Book Name Text Field
        bookNameTextField = new JTextField();
        bookNameTextField.setFont(new jojoPlainTextFont());
        bookNameTextField.setBounds(250, 280, 250, 40);
        this.add(bookNameTextField);

        // Publisher Text Field
        publisherTextField = new JTextField();
        publisherTextField.setFont(new jojoPlainTextFont());
        publisherTextField.setBounds(250, 320, 250, 40);
        this.add(publisherTextField);

        // Year Text Field
        yearTextField = new JTextField();
        yearTextField.setFont(new jojoPlainTextFont());
        yearTextField.setBounds(250, 360, 250, 40);
        this.add(yearTextField);

        // Author Text Field
        authorTextField = new JTextField();
        authorTextField.setFont(new jojoPlainTextFont());
        authorTextField.setBounds(250, 400, 250, 40);
        this.add(authorTextField);


        // Price Text Field
        priceTextField = new JTextField();
        priceTextField.setFont(new jojoPlainTextFont());
        priceTextField.setBounds(250, 440, 250, 40);
        this.add(priceTextField);

        // Amount Text Field
        amountTextField = new JTextField();
        amountTextField.setFont(new jojoPlainTextFont());
        amountTextField.setBounds(250, 480, 250, 40);
        this.add(amountTextField);

        // Single Import Button
        searchButton = new JButton("查询");
        searchButton.setFont(new jojoPlainTextFont());
        searchButton.setForeground(Color.red);
        searchButton.setBounds(500, 200, 80, 40);
        searchButton.setActionCommand("searchButton");
        searchButton.addActionListener(this);
        this.add(searchButton);

        // Single Import Button
        singleImportButton = new JButton("导入");
        singleImportButton.setFont(new jojoPlainTextFont());
        singleImportButton.setBounds(575, 330, 150, 60);
        singleImportButton.setActionCommand("singleImportButton");
        singleImportButton.addActionListener(this);
        this.add(singleImportButton);

        // Bulk Import Button
        bulkImportButton = new JButton("批量入库");
        bulkImportButton.setFont(new jojoPlainTextFont());
        bulkImportButton.setForeground(Color.red);
        bulkImportButton.setBounds(575, 400, 150, 60);
        bulkImportButton.setActionCommand("bulkImportButton");
        bulkImportButton.addActionListener(this);
        this.add(bulkImportButton);
    }
    private void handleSearchButton(){
        String[] result = new String[9];
        if(jojoDataManager.sharedInstance.searchBnoForBook(result ,bnoTextField.getText())){
            JOptionPane.showMessageDialog(null, "书库中已有该书，请填写数量.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
            bnoTextField.setText(result[0]);
            categoryTextField.setText(result[1]);
            bookNameTextField.setText(result[2]);
            publisherTextField.setText(result[3]);
            yearTextField.setText(result[4]);
            authorTextField.setText(result[5]);
            priceTextField.setText(result[6]);
            amountTextField.setText(result[7]);
        }
        else{
            JOptionPane.showMessageDialog(null, "书库中暂无该书，请填写新书信息.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
        }
    }
    private void handleSingleImportButton() {
        boolean returnValue = jojoDataManager.sharedInstance.importBooks(bnoTextField.getText(),
                categoryTextField.getText(), bookNameTextField.getText(),
                publisherTextField.getText(), yearTextField.getText(),authorTextField.getText(),
                priceTextField.getText(), amountTextField.getText());
        if (returnValue) {
            System.out.println("Book inserted.");
            bnoTextField.setText("");
            categoryTextField.setText("");
            bookNameTextField.setText("");
            publisherTextField.setText("");
            yearTextField.setText("");
            authorTextField.setText("");
            priceTextField.setText("");
            amountTextField.setText("");
        } else {
            System.out.println("Error occurred when trying to insert a book");
            JOptionPane.showMessageDialog(null, "图书信息格式错误.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBulkImportButton() {
        String fileAddress = JOptionPane.showInputDialog(null, "输入文件地址:\n",
                "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
        if (fileAddress.equals(""))
            return;
        if (jojoDataManager.sharedInstance.importBooksFromFile(fileAddress)) {
            System.out.println("Books imported");
        } else {
            JOptionPane.showMessageDialog(null, "文件中图书信息格式错误.",
                    "Tiny Library", JOptionPane.ERROR_MESSAGE);
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
            case "singleImportButton":
                handleSingleImportButton();
                break;
            case "bulkImportButton":
                handleBulkImportButton();
                break;
        }
    }
}
