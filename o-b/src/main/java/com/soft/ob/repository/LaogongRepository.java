package com.soft.ob.repository;

import com.soft.ob.entity.Laogong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LaogongRepository extends JpaRepository<Laogong, Long> {
    Optional<Laogong> findByUsername(String username);
    Optional<Laogong> findByLaogongUuid(String laogongUuid);
    boolean existsByUsername(String username);
    boolean existsByLaogongUuid(String laogongUuid);
}