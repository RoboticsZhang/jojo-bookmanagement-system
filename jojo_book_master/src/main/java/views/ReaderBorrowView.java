package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import fonts.*;
import model.*;

public class ReaderBorrowView extends ReaderView implements ActionListener {

    private JLabel featureNotationLabel;

    private JTextField borrowTextField;
    private JTextField returnTextField;

    private JButton borrowButton;
    private JButton searchButton;
    private JButton returnButton;

    public ReaderBorrowView() {
        super();
        placeSubComponents();
    }

    private void placeSubComponents() {
        // Borrow and Return Label
        featureNotationLabel = new JLabel("借/还书");
        featureNotationLabel.setFont(new jojoSubtitleFont());
        featureNotationLabel.setBounds(50, 120, 250, 50);
        this.add(featureNotationLabel);

        // Borrow Text Field
        borrowTextField = new JTextField();
        borrowTextField.setFont(new jojoPlainTextFont());
        borrowTextField.setBounds(125, 250, 250, 50);
        this.add(borrowTextField);

        // Return Text Field
        returnTextField = new JTextField();
        returnTextField.setFont(new jojoPlainTextFont());
        returnTextField.setBounds(125, 400, 250, 50);
        this.add(returnTextField);

        // search Button
        searchButton = new JButton("查询");
        searchButton.setFont(new jojoPlainTextFont());
        searchButton.setForeground(Color.red);
        searchButton.setBounds(375, 250, 100, 50);
        searchButton.setActionCommand("searchButton");
        searchButton.addActionListener(this);
        this.add(searchButton);

        // borrow Button
        borrowButton = new JButton("借书");
        borrowButton.setFont(new jojoPlainTextFont());
        borrowButton.setBounds(125, 300, 150, 50);
        borrowButton.setActionCommand("borrowButton");
        borrowButton.addActionListener(this);
        this.add(borrowButton);

        // Return Button
        returnButton = new JButton("还书");
        returnButton.setFont(new jojoPlainTextFont());
        returnButton.setBounds(125, 450, 150, 50);
        returnButton.setActionCommand("returnButton");
        returnButton.addActionListener(this);
        this.add(returnButton);
    }

    private void handleSearchButton() {
        int curr_stock = jojoDataManager.sharedInstance.checkForBookAmount(borrowTextField.getText());
        if (curr_stock == -1) {
            System.out.println("Book not found");
            JOptionPane.showMessageDialog(null, "该书不存在.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (curr_stock == 0){
            System.out.println("Book no stock");
            JOptionPane.showMessageDialog(null, "该书暂无库存.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (curr_stock > 0){
            System.out.println("Book no stock");
            JOptionPane.showMessageDialog(null, "查询到该书，库存量:" + curr_stock,
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
            return;
        }
    }
    private void handleBorrowButton() throws ParseException {
       if (jojoDataManager.sharedInstance.SelfborrowBook(borrowTextField.getText())) {
            System.out.println("Book borrowed");
            JOptionPane.showMessageDialog(null, "借书成功.",
                    "jojo图书管理", JOptionPane.PLAIN_MESSAGE);
            borrowTextField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "借书失败.",
                    "jojo图书管理", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleReturnButton() {
        if (jojoDataManager.sharedInstance.returnBook(returnTextField.getText())) {
            System.out.println("Book returned");
            returnTextField.setText("");
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
