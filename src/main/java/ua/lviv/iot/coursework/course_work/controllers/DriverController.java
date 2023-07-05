package ua.lviv.iot.coursework.course_work.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.lviv.iot.coursework.course_work.business.DriverService;
import ua.lviv.iot.coursework.course_work.models.Driver;

import java.io.IOException;
import java.util.List;

@RequestMapping("/drivers")
@RestController
public class DriverController {
    /**
     * Class to represent Driver Controller, this is class to work with REST
     */
    @Autowired
    private DriverService driverService = new DriverService();

    @GetMapping
    public List<Driver> getDrivers(){
        return driverService.getAllDrivers();
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable("id") Integer driverId){
        return driverService.getDriver(driverId);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver){
        try {
            driverService.createDriver(driver);
            return ResponseEntity.ok(driver);
        }catch (IOException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable("id") Integer driverId) throws IOException{
        return driverService.deleteDriver(driverId);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable("id") Integer driverId,@RequestBody Driver driver){
        return driverService.updateDriver(driverId, driver);
    }


}
