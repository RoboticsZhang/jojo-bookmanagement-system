package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import configuration.Configuration;
import fonts.*;
import model.*;
import main.*;

public class AdminView extends JPanel implements ActionListener {

    private JLabel titleLabel;
    private JButton adminImportButton;
    private JButton adminEditButton;
    private JButton adminManageButton;
    private JLabel navigationSeparatorLabel;
    private JButton logoutButton;
    private JButton searchButton;
    public JLabel iconLabel;

    public AdminView() {
        this.setLayout(null);
        placeComponents();
    }

    private void placeComponents() {

        // 标题标签
        titleLabel = new JLabel("管理员界面");
        titleLabel.setFont(new jojoTitleFont());
        titleLabel.setBounds(20, 10, 280, 50);
        this.add(titleLabel);
        // 登出按钮
        logoutButton = new JButton("登出");
        logoutButton.setFont(new jojoCommentFont());
        logoutButton.setBounds(90, 60, 80, 30);
        logoutButton.setForeground(Color.red);
        logoutButton.setActionCommand("logoutButton");
        logoutButton.addActionListener(this);
        this.add(logoutButton);

        // 图书入库按钮(跳转到图书入库界面)
        adminImportButton = new JButton("图书入库");
        adminImportButton.setFont(new jojoPlainTextFont());
        adminImportButton.setBounds(280, 25, 120, 60);
        adminImportButton.setActionCommand("adminImportButton");
        adminImportButton.addActionListener(this);
        this.add(adminImportButton);

        // 图书信息修改按钮(跳转到图书信息修改界面)
        adminEditButton = new JButton("图书修改");
        adminEditButton.setFont(new jojoPlainTextFont());
        adminEditButton.setBounds(400, 25, 120, 60);
        adminEditButton.setActionCommand("adminEditButton");
        adminEditButton.addActionListener(this);
        this.add(adminEditButton);

        // 借还书管理  (需要修改)
        searchButton = new JButton("借/还图书");
        searchButton.setFont(new jojoPlainTextFont());
        searchButton.setBounds(520, 25, 120, 60);
        searchButton.setActionCommand("adminBorrowButton");
        searchButton.addActionListener(this);
        this.add(searchButton);

        // 管理借书证
        adminManageButton = new JButton("图书/用户管理");
        adminManageButton.setFont(new jojoPlainTextFont());
        adminManageButton.setBounds(640, 25, 140, 60);
        adminManageButton.setActionCommand("adminManageButton");
        adminManageButton.addActionListener(this);
        this.add(adminManageButton);

        // 背景图片
        ImageIcon iconImage = new ImageIcon(Configuration.bgPATH);
        iconImage.setImage(iconImage.getImage().
                getScaledInstance(400, 300, Image.SCALE_DEFAULT));
        iconLabel = new JLabel(iconImage);
        iconLabel.setBounds(200, 150, 400, 300);
        this.add(iconLabel);
        iconLabel.setVisible(true);

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
            case "adminImportButton":
                AdminImportView adminImportView = new AdminImportView();
                Main.sharedInstance.reloadView(adminImportView);
                break;
            case "adminEditButton":
                AdminEditView adminEditView = new AdminEditView();
                Main.sharedInstance.reloadView(adminEditView);
                break;
            case "adminManageButton":
                AdminManageView adminManageView = new AdminManageView();
                Main.sharedInstance.reloadView(adminManageView);
                break;
            case "adminBorrowButton":
                AdminBorrowView adminBorrowView = new AdminBorrowView();
                Main.sharedInstance.reloadView(adminBorrowView);
                break;
            case "logoutButton":
                LoginView loginView = new LoginView();
                if (!jojoDataManager.sharedInstance.logoutFromDatabase()) {
                    System.out.println("Logout Failed.");
                    JOptionPane.showMessageDialog(null, "登出失败",
                            "jojo图书管理", JOptionPane.ERROR_MESSAGE);
                } else {
                    Main.sharedInstance.reloadView(loginView);
                }
        }
    }
}
