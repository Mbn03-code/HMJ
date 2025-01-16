package File;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportFile {
    private static final String FILE_PATH = "report.txt"; // فایل گزارش

    public static void logMessage(String message) {
        // چاپ در ترمینال
        System.out.println(message);

        // چاپ در پنجره پیام گرافیکی
        JOptionPane.showMessageDialog(null, message);

        // ذخیره در فایل
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(message);
        } catch (IOException e) {
            ReportFile.logMessage(e.getMessage());

        }
    }
}
