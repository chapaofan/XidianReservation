package com.xidian.reservation.service;

import com.xidian.reservation.entity.Manager;

public interface ManagerService {
    boolean saveManager(Manager manager);

    boolean deleteManager(Integer managerId);
}
