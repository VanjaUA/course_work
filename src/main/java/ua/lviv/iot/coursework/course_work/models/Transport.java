package ua.lviv.iot.coursework.course_work.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Transport {
    public Transport(Integer driverId,String location,Integer speed){
        this.driverId = driverId;
        this.location = location;
        this.speed = speed;
    }
    public Transport(String location,Integer speed){
        this.location = location;
        this.speed = speed;
        this.driverId = -1;
    }
    private String location;
    private Integer speed;
    private Integer id;
    private Integer driverId;
}
