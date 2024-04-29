package loginpage;

import javax.swing.*;

public class LoginPageMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPageGUI().setVisible(true));
    }
}
