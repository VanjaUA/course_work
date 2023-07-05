package ua.lviv.iot.coursework.course_work.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.coursework.course_work.business.TransportService;
import ua.lviv.iot.coursework.course_work.models.Driver;
import ua.lviv.iot.coursework.course_work.models.Transport;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/transports")
@RestController
public class TransportController {

    @Autowired
    private TransportService transportService = new TransportService();
    private Map<Integer,Transport> transports = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger();

    @GetMapping
    public List<Transport> getTransports(){
        return transportService.getAllTransports();
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Transport> getTransport(@PathVariable("id") Integer transportId){
        return transportService.getTransport(transportId);

    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Transport> createTransport(@RequestBody Transport transport){
        try {
            transportService.createTransport(transport);
            return ResponseEntity.ok(transport);
        }catch (IOException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Driver> deleteTransport(@PathVariable("id") Integer transportId){
        return transportService.deleteTransport(transportId);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<Driver> updateTransport(@PathVariable("id") Integer transportId,@RequestBody Transport transport){
        return transportService.updateTransport(transportId,transport);
    }
}
