package ua.lviv.iot.coursework.course_work.dataaccess;

import org.springframework.stereotype.Component;
import ua.lviv.iot.coursework.course_work.models.Driver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("DM_DEFAULT_ENCODING")
@Component
public class DriverWriter {
    private String fileName;
    private String directoryPath;

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public DriverWriter(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentData = dateFormat.format(new Date());
        this.fileName = "driver-" + currentData + ".csv";
        this.directoryPath = "DriverData";
        createFileIfNotExists(directoryPath, fileName);
    }

    public void writeDriversToCsv(Map<Integer, Driver> drivers) {
        try (FileWriter writer = new FileWriter(directoryPath + "/" + fileName)) {

            writer.write("ID,First Name,Last Name,Health Status\n");

            for (Map.Entry<Integer,Driver> entry : drivers.entrySet()){
                Integer id = entry.getKey();
                Driver driver = entry.getValue();
                writer.write(id + "," +
                        driver.getFirstName() + "," +
                        driver.getLastName() + "," +
                        driver.getHealthStatus() + "\n");
            }

            System.out.println("Data successfully in " + fileName);
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }
    private void createFileIfNotExists(String directoryPath, String fileName) {
        Path filePath = Paths.get(directoryPath, fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
