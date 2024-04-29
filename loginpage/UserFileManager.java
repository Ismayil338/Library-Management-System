package loginpage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserFileManager {
    private static final String USER_FILE_PATH = "users.csv";

    public static boolean checkCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String existingUsername = userData[0].trim();
                String existingPassword = userData[1].trim();
                if (existingUsername.equals(username) && existingPassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}