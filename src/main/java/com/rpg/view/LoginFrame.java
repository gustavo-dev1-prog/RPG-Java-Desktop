package com.rpg.view;

import com.rpg.dao.usuarioDAO;

import com.rpg.fx.JavaFXInitializer;
import com.rpg.view.components.*;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField txtUser;
    private final JPasswordField txtPass;
    private final JButton btnLogin;

    public LoginFrame() {

        JavaFXInitializer.init();

        setTitle("Path of Darkness");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        BackgroundPanel background = new BackgroundPanel("/com/rpggame/resources/background_login/background_login.png");
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JLabel titulo = new JLabel("Adventure World", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(new Color(218, 165, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 50, 0));
        background.add(titulo, BorderLayout.NORTH);

        JPanel loginBox = new JPanel(new GridBagLayout());
        loginBox.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUser = new JLabel("Username");
        lblUser.setForeground(new Color(218, 165, 32));
        loginBox.add(lblUser, gbc);

        gbc.gridx = 1;
        txtUser = new RoundedTextField(12);
        loginBox.add(txtUser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPass = new JLabel("Password");
        lblPass.setForeground(new Color(218, 165, 32));
        loginBox.add(lblPass, gbc);

        gbc.gridx = 1;
        txtPass = new RoundedPasswordField(12);
        loginBox.add(txtPass, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        btnLogin = new RoundedButton("Login");
        btnLogin.addActionListener(e -> {
            usuarioDAO dao = new usuarioDAO();
            int userId = dao.validarLogin(
                    txtUser.getText(),
                    new String(txtPass.getPassword())
            );

            if (userId != -1) {
                dispose();
                SwingUtilities.invokeLater(SeleçãoHeróiFrame::new);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuário ou Senha incorretos!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        loginBox.add(btnLogin, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(loginBox);

        background.add(wrapper, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}