package dashboard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GeneralDatabaseGenerator {
    public static void copyAndRenameCSVFile(String sourceFilePath, String destinationFilePath) {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);

        if (!sourceFile.exists()) {
            System.err.println("Source file does not exist.");
            return;
        }

        if (!destinationFile.getParentFile().exists()) {
            boolean dirsCreated = destinationFile.getParentFile().mkdirs();
            if (!dirsCreated) {
                System.err.println("Failed to create parent directories for destination file.");
                return;
            }
        }

        try {
            // Copy the file
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied and renamed successfully.");

            // Open the copied file
        } catch (IOException e) {
            System.err.println("Error copying or renaming the file: " + e.getMessage());
        }
    }
}
