package ua.lviv.iot.coursework.course_work.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.lviv.iot.coursework.course_work.dataaccess.DriverReader;
import ua.lviv.iot.coursework.course_work.dataaccess.DriverWriter;
import ua.lviv.iot.coursework.course_work.models.Driver;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DriverService {
    @Autowired
    private DriverWriter driverWriter = new DriverWriter();
    @Autowired
    private DriverReader driverReader = new DriverReader();

    private Map<Integer,Driver> drivers = driverReader.readDriversFromCsv();
    private AtomicInteger idCounter = new AtomicInteger(driverReader.getLastId());

    public Map<Integer,Driver> getDriversMap(){
        return drivers;
    }

    public LinkedList<Driver> getAllDrivers(){
        return new LinkedList<>(drivers.values());
    }
    public ResponseEntity getDriver(Integer driverId){
        if (drivers.containsKey(driverId)){
            drivers.get(driverId);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
    public void createDriver(Driver driver) throws IOException {
        driver.setId(idCounter.incrementAndGet());
        drivers.put(driver.getId(),driver);
        driverWriter.writeDriversToCsv(drivers);
    }

    public ResponseEntity deleteDriver(Integer driverId){
        if (drivers.containsKey(driverId)){
            drivers.remove(driverId);
            driverWriter.writeDriversToCsv(drivers);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity updateDriver(Integer driverId,Driver driver){
        if (drivers.containsKey(driverId)){
            driver.setId(driverId);
            drivers.put(driverId,driver);
            driverWriter.writeDriversToCsv(drivers);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
