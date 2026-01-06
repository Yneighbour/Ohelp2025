package com.soft.ob.repository;

import com.soft.ob.entity.Elderly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElderlyRepository extends JpaRepository<Elderly, Long> {
    List<Elderly> findByIsActiveTrue();
    List<Elderly> findByRoomNumber(String roomNumber);
    List<Elderly> findByNameContaining(String name);
    Optional<Elderly> findByUsername(String username);
    Optional<Elderly> findByUuid(String uuid);
    boolean existsByUsername(String username);
    boolean existsByUuid(String uuid);
}