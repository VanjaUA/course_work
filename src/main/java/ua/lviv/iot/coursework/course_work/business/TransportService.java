package ua.lviv.iot.coursework.course_work.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.lviv.iot.coursework.course_work.dataaccess.TransportReader;
import ua.lviv.iot.coursework.course_work.dataaccess.TransportWriter;
import ua.lviv.iot.coursework.course_work.models.Driver;
import ua.lviv.iot.coursework.course_work.models.Transport;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransportService {

    @Autowired
    private TransportWriter transportWriter = new TransportWriter();
    @Autowired
    private TransportReader transportReader = new TransportReader();

    @Autowired
    private DriverService driverService = new DriverService();
    private Map<Integer, Transport> transports = transportReader.readTransportsFromCsv();
    private AtomicInteger idCounter = new AtomicInteger(transportReader.getLastId());

    public LinkedList<Transport> getAllTransports(){
        return new LinkedList<>(transports.values());
    }
    public ResponseEntity getTransport(Integer transportId){
        if (transports.containsKey(transportId)){
            transports.get(transportId);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
    public void createTransport(Transport transport) throws IOException {
        if (driverService.getDriversMap().containsKey(transport.getDriverId())){
            transport.setId(idCounter.incrementAndGet());
            transports.put(transport.getId(),transport);
            transportWriter.writeDriversToCsv(transports);
        }else{
            throw new IOException();
        }
    }

    public ResponseEntity deleteTransport(Integer transportId){
        if (transports.containsKey(transportId)){
            transports.remove(transportId);
            transportWriter.writeDriversToCsv(transports);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity updateTransport(Integer transportId,Transport transport){
        if (transports.containsKey(transportId)){
            if (driverService.getDriversMap().containsKey(transport.getDriverId())){
                transport.setId(transportId);
                transports.put(transportId,transport);
                transportWriter.writeDriversToCsv(transports);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
