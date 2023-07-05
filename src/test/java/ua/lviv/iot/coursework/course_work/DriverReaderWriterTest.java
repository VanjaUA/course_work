package ua.lviv.iot.coursework.course_work;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import ua.lviv.iot.coursework.course_work.dataaccess.DriverReader;
import ua.lviv.iot.coursework.course_work.dataaccess.DriverWriter;
import ua.lviv.iot.coursework.course_work.models.Driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class DriverReaderWriterTest {
    private static final String TEST_DIRECTORY = "TestDataStorage";

    @BeforeEach
    public void setup() {
        Path path = Paths.get(TEST_DIRECTORY);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @AfterEach
    public void cleanup() {
        Path path = Paths.get(TEST_DIRECTORY);
        if (Files.exists(path)) {
            try {
                Files.walk(path)
                        .sorted(java.util.Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(java.io.File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        @Test
        public void testWriteAndReadDrivers() {

            Map<Integer, Driver> drivers = new HashMap<>();
            drivers.put(1, new Driver("123", "last", "OK"));
            drivers.put(2, new Driver("222", "lastname", "not OK"));
            drivers.put(3, new Driver("999", "namelast", "Good"));

            DriverWriter writer = new DriverWriter();
            writer.setDirectoryPath(TEST_DIRECTORY);
            writer.writeDriversToCsv(drivers);

            DriverReader reader = new DriverReader();
            reader.setDirectoryPath(TEST_DIRECTORY);
            Map<Integer, Driver> readDrivers = reader.readDriversFromCsv();

            Assertions.assertEquals(drivers.size(), readDrivers.size());
            for (Map.Entry<Integer, Driver> entry : drivers.entrySet()) {
                Integer id = entry.getKey();
                Driver expectedDriver = entry.getValue();
                Driver actualDriver = readDrivers.get(id);
                Assertions.assertEquals(expectedDriver.getFirstName(), actualDriver.getFirstName());
                Assertions.assertEquals(expectedDriver.getLastName(), actualDriver.getLastName());
                Assertions.assertEquals(expectedDriver.getHealthStatus(), actualDriver.getHealthStatus());
            }
        }

}

