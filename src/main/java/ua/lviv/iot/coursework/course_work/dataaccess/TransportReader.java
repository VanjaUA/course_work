package ua.lviv.iot.coursework.course_work.dataaccess;

import org.springframework.stereotype.Component;
import ua.lviv.iot.coursework.course_work.models.Driver;
import ua.lviv.iot.coursework.course_work.models.Transport;

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
public class TransportReader {
    private String directoryPath;
    private Integer lastId = 0;

    public Integer getLastId(){
        return lastId;
    }
    public TransportReader(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentData = dateFormat.format(new Date());
        this.directoryPath = "TransportData";
    }

    public Map<Integer, Transport> readTransportsFromCsv() {
        Date currentDate = new Date();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String currentMonth = monthFormat.format(currentDate);
        Map<Integer, Transport> transports = new HashMap<>();

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
                                    String location = data[1];
                                    Integer speed = Integer.parseInt(data[2]);
                                    Integer driverId = parseIntOrNull(data[3]);
                                    Transport transport = new Transport(location,speed,id,driverId);
                                    transports.put(id, transport);
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

            return transports;
        } else {
            System.out.println("No file datastorage");
            return transports;
        }
    }

    private String getMonthFromFileName(String fileName) {
        int startIndex = fileName.indexOf("-") + 6;
        int endIndex = fileName.indexOf("-", startIndex);
        return fileName.substring(startIndex, endIndex);
    }

    private Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
