package ua.lviv.iot.coursework.course_work.models;

import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Driver {
    /**
     * Class to represent entity Driver that have firstname,
     * lastname, healthStatus and id
     */
    public Driver(String firstName, String lastName, String healthStatus){
        this.firstName = firstName;
        this.lastName = lastName;
        this.healthStatus = healthStatus;
    }
    private String firstName;
    private String lastName;
    private String healthStatus;

    private Integer id;
}
