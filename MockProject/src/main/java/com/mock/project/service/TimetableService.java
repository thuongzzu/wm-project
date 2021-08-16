package com.mock.project.service;

import java.util.List;

import com.mock.project.dto.ViewSchedule;
import com.mock.project.dto.ViewTimetable;
import com.mock.project.entity.Timetable;

public interface TimetableService {
	Iterable<ViewTimetable> viewTimetable(Integer classID, Integer weekID);
	
	void saveTimetable(Timetable timetable);
	
	void addTimetable(int classID, int dayID, int periodID, int teacherID, int weekID);
	
	void deleteTimetable(int classID, int periodID, int dayID, int weekID);
	
	void newTable2();
	
	void newTable3();
	
	Iterable<ViewSchedule> viewSchedule(long teacherID, long weekID);
	
	List<Integer> checkTimetable (int dayID, int periodID, int start_year, int teacherID, int weekID);
}
