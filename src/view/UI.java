package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class UI {
    private static volatile UI instance;
    private volatile boolean isPasswordEntered = false;
    private volatile String password = null;
    private JFrame passwordFrame = null;

    private UI() {}

    public static UI getInstance() {
        if(instance == null) {
            synchronized (UI.class) {
                if(instance == null) {
                    instance = new UI();
                }
            }
        }
        return instance;
    }

    public void showLikedPosts(HashMap<Long, ArrayList<Long>> likedPosts) {
        likedPosts.forEach((groupID, posts) -> {
            System.out.println("Group " + groupID + ":");
            posts.forEach((postID) -> System.out.println("https://vk.com/wall-" + groupID + "_" + postID));
        });
    }

    public void pleaseWait() {
        System.out.println("Please, wait...");
    }

    public String[] requestLoginData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] emailpass = new String[2];
        System.out.print("Login: ");
        emailpass[0] = br.readLine();
        showPasswordRequest();
        while(!isPasswordEntered) {/*wait...*/}
        emailpass[1] = password;
        return emailpass;
    }

    public void showPasswordRequest() {
        PasswordPanel p = new PasswordPanel();
        Runnable r = () -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            p.createAndShowGUI();
        };
        SwingUtilities.invokeLater(r);
    }

    public int requestCountOfPosts() throws IOException {
        System.out.println("How many last posts do you want to check for each group?");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return Integer.valueOf(br.readLine());
    }

    public String requestUserlink() throws IOException {
        System.out.print("User link: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    class PasswordPanel extends JPanel
                implements ActionListener {
            private final String OK = "OK";
            private JPasswordField passwordField;

            public PasswordPanel() {
                passwordField = new JPasswordField(10);
                passwordField.setActionCommand(OK);
                passwordField.addActionListener(this);

                JLabel passLabel = new JLabel("Password: ");
                passLabel.setLabelFor(passwordField);
                JComponent buttonPane = createButtonPanel();

                JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
                textPane.add(passwordField);
                textPane.add(passLabel);

                add(textPane);
                add(buttonPane);
            }

            private JComponent createButtonPanel() {
                JPanel p = new JPanel(new GridLayout(0,1));
                JButton okButton = new JButton("OK");
                okButton.setActionCommand(OK);
                okButton.addActionListener(this);
                p.add(okButton);
                return p;
            }

            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();

                if (OK.equals(cmd)) {
                    char[] input = passwordField.getPassword();
                    password = String.valueOf(input);
                    isPasswordEntered = true;
                    passwordFrame.dispose();
                }
            }

            public void createAndShowGUI() {
                passwordFrame = new JFrame("Password");
                passwordFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                final PasswordPanel newContentPane = new PasswordPanel();
                newContentPane.setOpaque(true);
                passwordFrame.setContentPane(newContentPane);
                passwordFrame.pack();
                passwordFrame.setVisible(true);
            }
        }
}
