package com.soft.ob.serviceorder.service;

import com.soft.ob.serviceorder.entity.ServiceOrder;
import com.soft.ob.serviceorder.mapper.ServiceOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceOrderService {

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    public ServiceOrder createServiceOrder(ServiceOrder serviceOrder) {
        serviceOrder.setCreatedAt(LocalDateTime.now());
        serviceOrder.setUpdatedAt(LocalDateTime.now());
        serviceOrder.setIsActive(true);
        serviceOrderMapper.insert(serviceOrder);
        return serviceOrder;
    }

    public ServiceOrder getServiceOrderById(Long id) {
        return serviceOrderMapper.selectById(id);
    }

    public List<ServiceOrder> getAllServiceOrders() {
        return serviceOrderMapper.selectAll();
    }

    public List<ServiceOrder> getServiceOrdersByElderlyId(Long elderlyId) {
        return serviceOrderMapper.selectByElderlyId(elderlyId);
    }

    public List<ServiceOrder> getServiceOrdersByServiceType(String serviceType) {
        return serviceOrderMapper.selectByServiceType(serviceType);
    }

    public List<ServiceOrder> getServiceOrdersByServiceProviderId(Long serviceProviderId) {
        return serviceOrderMapper.selectByServiceProviderId(serviceProviderId);
    }

    public List<ServiceOrder> getServiceOrdersByStatus(String status) {
        return serviceOrderMapper.selectByStatus(status);
    }

    public ServiceOrder updateServiceOrder(ServiceOrder serviceOrder) {
        serviceOrder.setUpdatedAt(LocalDateTime.now());
        serviceOrderMapper.update(serviceOrder);
        return serviceOrder;
    }

    public boolean deleteServiceOrder(Long id) {
        return serviceOrderMapper.deleteById(id) > 0;
    }

    public boolean cancelServiceOrder(Long id) {
        ServiceOrder serviceOrder = serviceOrderMapper.selectById(id);
        if (serviceOrder != null) {
            serviceOrder.setStatus("cancelled");
            serviceOrder.setUpdatedAt(LocalDateTime.now());
            serviceOrderMapper.update(serviceOrder);
            return true;
        }
        return false;
    }

    public boolean completeServiceOrder(Long id) {
        ServiceOrder serviceOrder = serviceOrderMapper.selectById(id);
        if (serviceOrder != null) {
            serviceOrder.setStatus("completed");
            serviceOrder.setUpdatedAt(LocalDateTime.now());
            serviceOrderMapper.update(serviceOrder);
            return true;
        }
        return false;
    }
}
