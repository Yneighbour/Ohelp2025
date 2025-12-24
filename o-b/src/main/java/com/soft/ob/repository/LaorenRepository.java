package com.soft.ob.repository;

import com.soft.ob.entity.Laoren;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LaorenRepository extends JpaRepository<Laoren, Long> {
    Optional<Laoren> findByUsername(String username);
    Optional<Laoren> findByLaorenUuid(String laorenUuid);
    boolean existsByUsername(String username);
    boolean existsByLaorenUuid(String laorenUuid);
}