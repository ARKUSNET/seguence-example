package ru.kusart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(generator = "get_nal")
    private long empId;
    private String empName;
}
