package loginpage;

import javax.swing.*;

import languageselection.LanguageSelectionGUI;

public class ProgramStart {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LanguageSelectionGUI::new);
    }
}
