package com.xidian.reservation.entity;

import lombok.Data;

@Data
public class Manager {
    private Integer managerId;

    private String managerName;

    private String managerPassword;

    private Integer managerPosition;

    private String managerDepartment;

    private String managerSchool;

}