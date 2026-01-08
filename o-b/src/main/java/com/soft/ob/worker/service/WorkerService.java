package com.soft.ob.worker.service;

import com.soft.ob.worker.entity.Worker;
import com.soft.ob.worker.mapper.WorkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerMapper workerMapper;

    public Worker createWorker(Worker worker) {
        worker.setCreatedAt(LocalDateTime.now());
        worker.setUpdatedAt(LocalDateTime.now());
        worker.setIsActive(true);
        worker.setIsAvailable(true);
        workerMapper.insert(worker);
        return worker;
    }

    public Worker getWorkerById(Long id) {
        return workerMapper.selectById(id);
    }

    public Worker getWorkerByEmail(String email) {
        return workerMapper.selectByEmail(email);
    }

    public Worker getWorkerByPhone(String phone) {
        return workerMapper.selectByPhone(phone);
    }

    public List<Worker> getAllWorkers() {
        return workerMapper.selectAll();
    }

    public List<Worker> getWorkersByDepartment(String department) {
        return workerMapper.selectByDepartment(department);
    }

    public List<Worker> getWorkersByPosition(String position) {
        return workerMapper.selectByPosition(position);
    }

    public List<Worker> getAvailableWorkers() {
        return workerMapper.selectByAvailability(true);
    }

    public Worker updateWorker(Worker worker) {
        worker.setUpdatedAt(LocalDateTime.now());
        workerMapper.update(worker);
        return worker;
    }

    public boolean deleteWorker(Long id) {
        return workerMapper.deleteById(id) > 0;
    }

    public boolean activateWorker(Long id) {
        Worker worker = workerMapper.selectById(id);
        if (worker != null) {
            worker.setIsActive(true);
            worker.setUpdatedAt(LocalDateTime.now());
            workerMapper.update(worker);
            return true;
        }
        return false;
    }

    public boolean deactivateWorker(Long id) {
        Worker worker = workerMapper.selectById(id);
        if (worker != null) {
            worker.setIsActive(false);
            worker.setUpdatedAt(LocalDateTime.now());
            workerMapper.update(worker);
            return true;
        }
        return false;
    }

    public boolean setWorkerAvailability(Long id, Boolean available) {
        Worker worker = workerMapper.selectById(id);
        if (worker != null) {
            worker.setIsAvailable(available);
            worker.setUpdatedAt(LocalDateTime.now());
            workerMapper.update(worker);
            return true;
        }
        return false;
    }
}
