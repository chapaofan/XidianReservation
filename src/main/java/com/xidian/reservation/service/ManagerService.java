package com.xidian.reservation.service;

import com.xidian.reservation.entity.Manager;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

public interface ManagerService {

    UniversalResponseBody managerLogin(Manager manager) throws Exception;

    boolean saveManager(Manager manager);

    boolean deleteManager(Integer managerId);

    Manager findManagerById(Integer managerId);
}
