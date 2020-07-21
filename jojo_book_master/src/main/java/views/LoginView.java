package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import configuration.*;
import fonts.*;
import model.*;
import main.*;

public class LoginView extends JPanel implements ActionListener {

    public JLabel iconLabel;
    public JLabel userLabel;
    public JLabel passwordLabel;
    public JTextField userTextField;
    public JPasswordField passwordField;
    public JButton UserloginButton;
    public JButton AdminloginButton;

    public LoginView() {
        this.setLayout(null);
        placeComponents();
    }

    private void placeComponents() {

        // Image Icon
        // System.out.println(this.getClass().getClassLoader().getResource("").getPath());
        ImageIcon iconImage = new ImageIcon(Configuration.iconPATH);
        iconImage.setImage(iconImage.getImage().
                getScaledInstance(230, 230, Image.SCALE_DEFAULT));
        iconLabel = new JLabel(iconImage);
        iconLabel.setBounds(300, 50, 230, 230);
        this.add(iconLabel);

        // User Label
        userLabel = new JLabel("账号");
        userLabel.setFont(new jojoSubtitleFont());
        userLabel.setBounds(300, 300, 70, 50);
        this.add(userLabel);

        // Password Label
        passwordLabel = new JLabel("密码");
        passwordLabel.setFont(new jojoSubtitleFont());
        passwordLabel.setBounds(300, 350, 70, 50);
        this.add(passwordLabel);

        // User Text Field
        userTextField = new JTextField();
        userTextField.setBounds(380, 310, 130, 30);
        this.add(userTextField);

        // Password Text Field
        passwordField = new JPasswordField();
        passwordField.setBounds(380, 360, 130, 30);
        this.add(passwordField);

        //User Login Button
        UserloginButton = new JButton("用户登录");
        UserloginButton.setFont(new jojoPlainTextFont());
        UserloginButton.setBounds(290, 420, 120, 40);
        UserloginButton.setActionCommand("UserloginButton");
        UserloginButton.addActionListener(this);
        this.add(UserloginButton);

        //Admin Login Button
        AdminloginButton = new JButton("管理员登录");
        AdminloginButton.setFont(new jojoPlainTextFont());
        AdminloginButton.setBounds(430, 420, 120, 40);
        AdminloginButton.setActionCommand("AdminloginButton");
        AdminloginButton.addActionListener(this);
        this.add(AdminloginButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "UserloginButton":
                jojoDataManager dataManager = new jojoDataManager();
                if (dataManager.connectToDatabase()) {
                    System.out.println("连接到数据库.");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "无法连接到数据库", "jojo图书管理", JOptionPane.ERROR_MESSAGE);
                }
                String password = String.valueOf(passwordField.getPassword());
                //输入账号与数据库中管理员账号相同，且密码正确
                if (dataManager.readerLogin(userTextField.getText(), password)) {
                    System.out.println("用户成功登录.");
                    ReaderView readerView = new ReaderView();
                    Main.sharedInstance.reloadView(readerView);
                } else {
                    System.out.println("User logging failed.");
                    JOptionPane.showMessageDialog(null,
                            "账号或者密码错误", "jojo图书管理", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "AdminloginButton":
                dataManager = new jojoDataManager();
                if (dataManager.connectToDatabase()) {
                    System.out.println("连接到数据库.");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "无法连接到数据库", "jojo图书管理", JOptionPane.ERROR_MESSAGE);
                }
                password = String.valueOf(passwordField.getPassword());
                //输入账号与数据库中管理员账号相同，且密码正确
                if (dataManager.adminLogin(userTextField.getText(), password)) {
                    System.out.println("管理员成功登录.");
                    AdminView adminView = new AdminView();
                    Main.sharedInstance.reloadView(adminView);
                } else {
                    System.out.println("User logging failed.");
                    JOptionPane.showMessageDialog(null,
                            "账号或者密码错误", "jojo图书管理", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }
}
