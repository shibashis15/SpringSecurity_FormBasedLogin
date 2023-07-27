package com.SpringSecurity.com.Repository;

import com.SpringSecurity.com.Entity.Attempts;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttemptsRepository extends JpaRepository<Attempts , Integer> {
    Optional<Attempts> findAttemptsByUsername(String username);
}
