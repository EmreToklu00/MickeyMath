package Utility;

import javax.swing.*;
import java.awt.*;

public class Items {


   public static JButton Button(String text, boolean isOpacity) {
       JButton button = new JButton(text);

       if (isOpacity){
           button.setOpaque(false);
       }
        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setContentAreaFilled(false);

        button.setForeground(Color.BLACK);

        button.setFont(new Font("Arial", Font.BOLD, 50));

       button.setFocusable(true);
        return button;
    }



    public static JTextField TextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setOpaque(false);
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font("Arial", Font.BOLD, 35));
        return textField;
    }

   public static JPasswordField PasswordTextField(int columns) {
        JPasswordField passwordtextField = new JPasswordField(columns);
        passwordtextField.setOpaque(false);
        passwordtextField.setForeground(Color.WHITE);
        passwordtextField.setFont(new Font("Arial", Font.BOLD, 20));
        return passwordtextField;
    }

    public static JTextArea TextArea(String text) {
        JTextArea textarea = new JTextArea(text);
        textarea.setForeground(Color.green);
        textarea.setEditable(false);
        textarea.setOpaque(false);
        textarea.setBackground(new Color(0, 0, 0, 0));
        textarea.setFont(new Font("Arial", Font.BOLD, 20));
        return textarea;
    }

    public static JPanel Panel() {
        Color transparentMagenta = new Color(255, 0, 255, 0);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(transparentMagenta);
        return panel;
    }

    public static JLabel Label(String text){
        JLabel Label =   new JLabel(text);
        Label.setForeground(Color.BLACK);
        Label.setFont(new Font("Arial", Font.BOLD, 50));
       return Label;
    }
}
