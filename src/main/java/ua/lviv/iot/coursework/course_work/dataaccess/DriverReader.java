package ua.lviv.iot.coursework.course_work.dataaccess;

import org.springframework.stereotype.Component;
import ua.lviv.iot.coursework.course_work.models.Driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class DriverReader {
    private String directoryPath;
    private Integer lastId = 0;

    public Integer getLastId(){
        return lastId;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public DriverReader(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentData = dateFormat.format(new Date());
        this.directoryPath = "DriverData";
    }

    public Map<Integer, Driver> readDriversFromCsv() {
        Date currentDate = new Date();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String currentMonth = monthFormat.format(currentDate);
        Map<Integer, Driver> drivers = new HashMap<>();

        Path path = Paths.get(directoryPath);

        if (Files.exists(path) && Files.isDirectory(path)) {
            File[] files = new File(directoryPath).listFiles();

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    String fileName = file.getName();
                    String fileMonth = getMonthFromFileName(fileName);
                    System.out.println(fileMonth);

                    if (fileMonth.equals("07") || fileMonth.equals("7")) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            boolean isFirstLine = true;
                            while ((line = reader.readLine()) != null) {
                                if (isFirstLine) {
                                    isFirstLine = false;
                                    continue;
                                }
                                String[] data = line.split(",");
                                if (data.length == 4) {
                                    Integer id = Integer.parseInt(data[0]);
                                    String firstName = data[1];
                                    String lastName = data[2];
                                    String healthStatus = data[3];
                                    Driver driver = new Driver(firstName,lastName,healthStatus,id);
                                    drivers.put(id, driver);
                                    lastId = id;
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("ERROR");
                            e.printStackTrace();
                        }
                    }
                }
            }

            return drivers;
        } else {
            System.out.println("No file datastorage");
            return drivers;
        }
    }

    private String getMonthFromFileName(String fileName) {
        int startIndex = fileName.indexOf("-") + 6;
        int endIndex = fileName.indexOf("-", startIndex);
        return fileName.substring(startIndex, endIndex);
    }
}
