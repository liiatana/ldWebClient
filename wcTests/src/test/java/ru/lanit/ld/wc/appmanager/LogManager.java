package ru.lanit.ld.wc.appmanager;

import com.codeborne.selenide.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogManager {

    private String allureProrertyFilePath;
    private File allureEnvFile;

    public LogManager(String allureProrertyFilePath) {
        this.allureProrertyFilePath=allureProrertyFilePath;
    }

    public void exportEnviromentInfornationToFile(ApplicationManager app) throws IOException {

        this.allureEnvFile = new File(this.allureProrertyFilePath);
            if (allureEnvFile.exists()) {
                allureEnvFile.delete();
            }

            allureEnvFile.createNewFile();

            try (FileWriter writer = new FileWriter(allureEnvFile, false);) {
                writer.write("Browser=" + app.browser);
                writer.write(System.lineSeparator());
                writer.write("BrowserSize=" + Configuration.browserSize);
                writer.write(System.lineSeparator());
                writer.write(System.lineSeparator());
                writer.write("Back=" + app.apiUrl);
                writer.write(System.lineSeparator());
                writer.write("Back=" + app.UserList.users.get(0).getUserApi().getBackVersion());
                writer.write("Front=" + app.baseUrl);


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addEnviromentInfo(String parameter, String value) {

         try (FileWriter writer = new FileWriter(allureEnvFile, true);) {

            writer.write(System.lineSeparator());
            writer.write( parameter +"=" + value);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }



}
