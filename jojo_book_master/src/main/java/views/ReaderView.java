package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fonts.*;
import model.*;
import main.*;

public class ReaderView extends JPanel implements ActionListener {
    private JLabel titleLabel;
    private JLabel navigationLabel;
    private JButton readerSearchButton;
    private JButton readerBorrowButton;
    private JButton readerHistoryButton;
    private JLabel navigationSeparatorLabel;
    private JButton logoutButton;

    public ReaderView() {
        this.setLayout(null);
        placeComponents();
    }

    private void placeComponents() {

        // Title Label
        titleLabel = new JLabel("jojo图书管理");
        titleLabel.setFont(new jojoTitleFont());
        titleLabel.setBounds(20, 10, 280, 50);
        this.add(titleLabel);

        // Navigation Label
        navigationLabel = new JLabel("登录用户:" + jojoDataManager.sharedInstance.currentUser);
        navigationLabel.setFont(new jojoPlainTextFont());
        navigationLabel.setBounds(20, 60, 250, 30);
        this.add(navigationLabel);
        // 登出按钮
        logoutButton = new JButton("登出");
        logoutButton.setFont(new jojoCommentFont());
        logoutButton.setBounds(160, 60, 80, 30);
        logoutButton.setForeground(Color.red);
        logoutButton.setActionCommand("logoutButton");
        logoutButton.addActionListener(this);
        this.add(logoutButton);
        // Search Button
        readerSearchButton = new JButton("查询");
        readerSearchButton.setFont(new jojoPlainTextFont());
        readerSearchButton.setBounds(350, 25, 120, 60);
        readerSearchButton.setActionCommand("readerSearchButton");
        readerSearchButton.addActionListener(this);
        this.add(readerSearchButton);

        // Edit Books Button
        readerBorrowButton = new JButton("借还书");
        readerBorrowButton.setFont(new jojoPlainTextFont());
        readerBorrowButton.setBounds(470, 25, 120, 60);
        readerBorrowButton.setActionCommand("readerBorrowButton");
        readerBorrowButton.addActionListener(this);
        this.add(readerBorrowButton);

        // Reader History Button
        readerHistoryButton = new JButton("借书记录");
        readerHistoryButton.setFont(new jojoPlainTextFont());
        readerHistoryButton.setBounds(590, 25, 120, 60);
        readerHistoryButton.setActionCommand("readerHistoryButton");
        readerHistoryButton.addActionListener(this);
        this.add(readerHistoryButton);

        // Navigation Separator Label
        navigationSeparatorLabel = new JLabel();
        navigationSeparatorLabel.setBounds(20, 100, 760, 3);
        navigationSeparatorLabel.setOpaque(true);
        navigationSeparatorLabel.setBackground(Color.DARK_GRAY);
        this.add(navigationSeparatorLabel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "readerSearchButton":
                ReaderSearchView readerSearchView = new ReaderSearchView();
                Main.sharedInstance.reloadView(readerSearchView);
                break;
            case "readerBorrowButton":
                ReaderBorrowView readerBorrowView = new ReaderBorrowView();
                Main.sharedInstance.reloadView(readerBorrowView);
                break;
            case "readerHistoryButton":
                ReaderHistoryView readerHistoryView = new ReaderHistoryView();
                Main.sharedInstance.reloadView(readerHistoryView);
                break;
            case "logoutButton":
                LoginView loginView = new LoginView();
                if (!jojoDataManager.sharedInstance.logoutFromDatabase()) {
                    System.out.println("Logout Failed.");
                    JOptionPane.showMessageDialog(null, "登出失败.",
                            "jojo图书管理", JOptionPane.ERROR_MESSAGE);
                } else {
                    Main.sharedInstance.reloadView(loginView);
                }
        }
    }
}
