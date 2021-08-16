package com.mock.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mock.project.entity.Week;

@Repository
public interface WeekRepository extends JpaRepository<Week, Integer> {

}
