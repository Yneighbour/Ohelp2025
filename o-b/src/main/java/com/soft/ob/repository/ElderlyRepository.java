package com.soft.ob.repository;

import com.soft.ob.entity.Elderly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ElderlyRepository extends JpaRepository<Elderly, Long> {
    List<Elderly> findByIsActiveTrue();
    List<Elderly> findByRoomNumber(String roomNumber);
    List<Elderly> findByNameContaining(String name);
}