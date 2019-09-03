package com.xidian.reservation.service;

import com.xidian.reservation.entity.Manager;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

public interface ManagerService {

    UniversalResponseBody managerLogin(Manager manager) throws Exception;

    boolean saveManager(Manager manager);

    boolean deleteManager(Long managerId);

    Manager findManagerById(Long managerId);
}
