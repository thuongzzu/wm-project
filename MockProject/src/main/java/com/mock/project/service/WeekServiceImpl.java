package com.mock.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mock.project.entity.Week;
import com.mock.project.repository.WeekRepository;

@Service
public class WeekServiceImpl implements WeekService {

	@Autowired
	WeekRepository weekRepository;
	
	@Override
	public List<Week> findAll() {
		return weekRepository.findAll() ;
	}
	
}
