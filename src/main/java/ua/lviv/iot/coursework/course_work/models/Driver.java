package ua.lviv.iot.coursework.course_work.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Driver {

    public Driver(String firstName,String lastName,String healthStatus){
        this.firstName = firstName;
        this.lastName = lastName;
        this.healthStatus = healthStatus;
    }
    private String firstName;
    private String lastName;
    private String healthStatus;

    @Id
    private Integer id;
}
