package com.xidian.reservation.entity;

public class Manager {
    private Integer managerId;

    private String managerName;

    private String managerPassword;

    private Integer managerPosition;

    private String managerDepartment;

    private String managerSchool;

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword == null ? null : managerPassword.trim();
    }

    public Integer getManagerPosition() {
        return managerPosition;
    }

    public void setManagerPosition(Integer managerPosition) {
        this.managerPosition = managerPosition;
    }

    public String getManagerDepartment() {
        return managerDepartment;
    }

    public void setManagerDepartment(String managerDepartment) {
        this.managerDepartment = managerDepartment == null ? null : managerDepartment.trim();
    }

    public String getManagerSchool() {
        return managerSchool;
    }

    public void setManagerSchool(String managerSchool) {
        this.managerSchool = managerSchool == null ? null : managerSchool.trim();
    }
}