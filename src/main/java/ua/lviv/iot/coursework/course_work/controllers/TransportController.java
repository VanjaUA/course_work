package ua.lviv.iot.coursework.course_work.controllers;

import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.coursework.course_work.models.Driver;
import ua.lviv.iot.coursework.course_work.models.Transport;

@RequestMapping("/transports")
@RestController
public class TransportController {
    @GetMapping(path = "/{firstName}")
    public Transport getTransport(@PathVariable("firstName") String driverName){
        System.out.println(driverName);
        return new Transport(new Driver(driverName,"last"));
    }
}
