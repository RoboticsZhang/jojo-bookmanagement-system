package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fonts.*;
import model.*;

public class AdminEditView extends AdminView implements ActionListener {

    private JLabel featureNotationLabel;
    private JLabel bookNameLabel;
    private JLabel priceLabel;
    private JLabel categoryLabel;
    private JLabel publisherLabel;
    private JLabel authorLabel;
    private JLabel yearLabel;
    private JLabel totalLabel;
    private JLabel stockLabel;
    private JLabel searchBnoLabel;

    private JTextField bookNameTextField;
    private JTextField priceTextField;
    private JTextField categoryTextField;
    private JTextField publisherTextField;
    private JTextField authorTextField;
    private JTextField yearTextField;
    private JTextField totalTextField;
    private JTextField stockTextField;
    private JTextField searchBnoTextField;

    private JButton searchButton;
    private JButton replaceButton;

    public AdminEditView() {
        super();
        placeSubComponent();
        iconLabel.setVisible(false);
    }

    private void placeSubComponent() {
        // Import a Book Label
        featureNotationLabel = new JLabel("修改书目信息");
        featureNotationLabel.setFont(new jojoSubtitleFont());
        featureNotationLabel.setBounds(60, 120, 200, 150);
        this.add(featureNotationLabel);
        // category Label
        categoryLabel = new JLabel("类别");
        categoryLabel.setFont(new jojoPlainTextFont());
        categoryLabel.setBounds(300, 150, 150, 50);
        this.add(categoryLabel);
        // Book Name Label
        bookNameLabel = new JLabel("书名");
        bookNameLabel.setFont(new jojoPlainTextFont());
        bookNameLabel.setBounds(300, 200, 150, 50);
        this.add(bookNameLabel);

        // Publisher Label
        publisherLabel = new JLabel("出版社");
        publisherLabel.setFont(new jojoPlainTextFont());
        publisherLabel.setBounds(300, 250, 150, 50);
        this.add(publisherLabel);

        // Year Label
        yearLabel = new JLabel("出版年份");
        yearLabel.setFont(new jojoPlainTextFont());
        yearLabel.setBounds(300, 300, 150, 50);
        this.add(yearLabel);

        // Author Label
        authorLabel = new JLabel("作者");
        authorLabel.setFont(new jojoPlainTextFont());
        authorLabel.setBounds(300, 350, 150, 50);
        this.add(authorLabel);


        // price Label
        priceLabel = new JLabel("价格");
        priceLabel.setFont(new jojoPlainTextFont());
        priceLabel.setBounds(300, 400, 150, 50);
        this.add(priceLabel);

        // total Label
        totalLabel = new JLabel("馆藏量");
        totalLabel.setFont(new jojoPlainTextFont());
        totalLabel.setBounds(300, 450, 150, 50);
        this.add(totalLabel);

        // stock Label
        stockLabel = new JLabel("库存量");
        stockLabel.setFont(new jojoPlainTextFont());
        stockLabel.setBounds(300, 500, 150, 50);
        this.add(stockLabel);

        // Search Name Label
        searchBnoLabel = new JLabel("以书号查询书目");
        searchBnoLabel.setFont(new jojoPlainTextFont());
        searchBnoLabel.setBounds(50, 200, 200, 50);
        this.add(searchBnoLabel);

        // category Text Field
        categoryTextField = new JTextField();
        categoryTextField.setFont(new jojoPlainTextFont());
        categoryTextField.setBounds(450, 150, 250, 50);
        this.add(categoryTextField);

        // Book Name Text Field
        bookNameTextField = new JTextField();
        bookNameTextField.setFont(new jojoPlainTextFont());
        bookNameTextField.setBounds(450, 200, 250, 50);
        this.add(bookNameTextField);

        // Publisher Text Field
        publisherTextField = new JTextField();
        publisherTextField.setFont(new jojoPlainTextFont());
        publisherTextField.setBounds(450, 250, 250, 50);
        this.add(publisherTextField);

        // Year Text Field
        yearTextField = new JTextField();
        yearTextField.setFont(new jojoPlainTextFont());
        yearTextField.setBounds(450, 300, 250, 50);
        this.add(yearTextField);

        // Author Text Field
        authorTextField = new JTextField();
        authorTextField.setFont(new jojoPlainTextFont());
        authorTextField.setBounds(450, 350, 250, 50);
        this.add(authorTextField);

        // price Text Field
        priceTextField = new JTextField();
        priceTextField.setFont(new jojoPlainTextFont());
        priceTextField.setBounds(450, 400, 250, 50);
        this.add(priceTextField);

        // total Text Field
        totalTextField = new JTextField();
        totalTextField.setFont(new jojoPlainTextFont());
        totalTextField.setBounds(450, 450, 250, 50);
        this.add(totalTextField);

        // stock Text Field
        stockTextField = new JTextField();
        stockTextField.setFont(new jojoPlainTextFont());
        stockTextField.setBounds(450, 500, 250, 50);
        this.add(stockTextField);

        // Search Name Text Field
        searchBnoTextField = new JTextField();
        searchBnoTextField.setFont(new jojoPlainTextFont());
        searchBnoTextField.setBounds(50, 250, 200, 50);
        this.add(searchBnoTextField);

        // Search Button
        searchButton = new JButton("查询");
        searchButton.setFont(new jojoPlainTextFont());
        searchButton.setBounds(50, 300, 100, 40);
        searchButton.setActionCommand("searchButton");
        searchButton.addActionListener(this);
        this.add(searchButton);

        // Edit Button
        replaceButton = new JButton("更改");
        replaceButton.setFont(new jojoPlainTextFont());
        replaceButton.setBounds(50, 450, 100, 40);
        replaceButton.setActionCommand("replaceButton");
        replaceButton.addActionListener(this);
        this.add(replaceButton);
    }

    private void handleSearchButton() {
        String[] result = new String[9];
        if(jojoDataManager.sharedInstance.searchBnoForBook(result ,searchBnoTextField.getText())){
            JOptionPane.showMessageDialog(null, "查询成功.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
            categoryTextField.setText(result[1]);
            bookNameTextField.setText(result[2]);
            publisherTextField.setText(result[3]);
            yearTextField.setText(result[4]);
            authorTextField.setText(result[5]);
            priceTextField.setText(result[6]);
            totalTextField.setText(result[7]);
            stockTextField.setText(result[8]);
        }else {
            JOptionPane.showMessageDialog(null, "未查询到该书.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleReplaceButton() {
        String[] newDataContainer = new String[9];
        newDataContainer[0] = searchBnoTextField.getText();
        newDataContainer[1] = categoryTextField.getText();
        newDataContainer[2] = bookNameTextField.getText();
        newDataContainer[3] = publisherTextField.getText();
        newDataContainer[4] = yearTextField.getText();
        newDataContainer[5] = authorTextField.getText();
        newDataContainer[6] = priceTextField.getText();
        newDataContainer[7] = totalTextField.getText();
        newDataContainer[8] = stockTextField.getText();
        if (jojoDataManager.sharedInstance.updateBook(newDataContainer)) {
            categoryTextField.setText("");
            bookNameTextField.setText("");
            publisherTextField.setText("");
            yearTextField.setText("");
            authorTextField.setText("");
            priceTextField.setText("");
            totalTextField.setText("");
            stockTextField.setText("");
            JOptionPane.showMessageDialog(null, "修改成功.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "修改失败",
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
            case "replaceButton":
                handleReplaceButton();
                break;
        }
    }
}
