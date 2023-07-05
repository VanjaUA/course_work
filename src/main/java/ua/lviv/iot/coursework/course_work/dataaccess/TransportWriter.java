package ua.lviv.iot.coursework.course_work.dataaccess;

import org.springframework.stereotype.Component;
import ua.lviv.iot.coursework.course_work.models.Driver;
import ua.lviv.iot.coursework.course_work.models.Transport;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class TransportWriter {
    private String fileName;
    private String directoryPath;

    public TransportWriter(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentData = dateFormat.format(new Date());
        this.fileName = "transport-" + currentData + ".csv";
        this.directoryPath = "TransportData";
        createFileIfNotExists(directoryPath, fileName);
    }

    public void writeDriversToCsv(Map<Integer, Transport> transports) {
        try (FileWriter writer = new FileWriter(directoryPath + "/" + fileName)) {

            writer.write("Location,Speed,Id,driverId\n");

            for (Map.Entry<Integer,Transport> entry : transports.entrySet()){
                Integer id = entry.getKey();
                Transport transport = entry.getValue();
                writer.write(id + "," +
                        transport.getLocation() + "," +
                        transport.getSpeed() + "," +
                        transport.getDriverId() + "\n");
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
