package signuppage;

import java.io.*;

public class UserFileManager {
    private static final String USER_FOLDER_PATH = "userdatabases";

    public static boolean saveUser(String username) {
        String filePath = USER_FOLDER_PATH + File.separator + username + ".csv";

        try {
            File folder = new File(USER_FOLDER_PATH);
            if (!folder.exists()) {
                folder.mkdir();
            }

            try (PrintWriter printWriter = new PrintWriter(new FileWriter(filePath))) {
                printWriter.println("Title,Author,Rating,Review,Status,TimeSpent,StartDate,EndDate,UserRating,UserReview");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkUsername(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String existingUsername = userData[0].trim();
                if (existingUsername.equals(username)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
