package com.mock.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mock.project.dto.ViewSchedule;
import com.mock.project.dto.ViewTimetable;
import com.mock.project.entity.Timetable;
import com.mock.project.repository.TimetableRepository;

@Service
public class TimetableServiceImpl implements TimetableService{
	
	@Autowired
	private TimetableRepository timetableRepository;
	
	@Override
	public Iterable<ViewTimetable> viewTimetable(Integer classID, Integer weekID) {
		Iterable<Object[]> datas=  timetableRepository.viewTimetable(classID,weekID);
		ArrayList<ViewTimetable> result = new ArrayList<ViewTimetable>();
		datas.forEach(item -> {
			ViewTimetable obj = new ViewTimetable();
			obj.setPeriodID(item[0]==null ? null : ((Integer)item[0]).longValue());
			obj.setDayID(item[1]==null ? null : ((Integer)item[1]).longValue());
			obj.setName((String)item[2]);
			obj.setSubjectName((String)item[3]);
			obj.setClassID(item[4]==null ? null : ((Integer)item[4]).longValue());
			obj.setWeekID(item[5]==null ? null : ((Integer)item[5]).longValue());
			result.add(obj);
		});
		return result;
	}
	
	@Override
	public Iterable<ViewSchedule> viewSchedule(long teacherID, long weekID) {
		Iterable<Object[]> datas=  timetableRepository.viewSchedule(teacherID, weekID);
		ArrayList<ViewSchedule> result = new ArrayList<ViewSchedule>();
		datas.forEach(item -> {
			ViewSchedule obj = new ViewSchedule();
			obj.setPeriodID(item[0]==null ? null : ((Integer)item[0]));
			obj.setDayID(item[1]==null ? null : ((Integer)item[1]));
			obj.setClassName((String)item[2]);
			obj.setTeacherID(item[3]==null ? null : ((Integer)item[3]));
			obj.setStartYear(item[4]==null ? null : ((Integer)item[4]));
			obj.setWeekID(item[5]==null ? null : ((Integer)item[5]));
			result.add(obj);
		});
		return result;
	}

	@Override
	public void saveTimetable(Timetable timetable) {
		timetableRepository.save(timetable);
	}

	@Override
	public void addTimetable(int classID, int dayID, int periodID, int teacherID, int weekID) {
		timetableRepository.addTimetable(classID, dayID, periodID, teacherID, weekID);
	}

	@Override
	public void deleteTimetable(int classID, int periodID, int dayID, int weekID) {
		timetableRepository.deleteTimetable(classID, periodID, dayID, weekID);
		
	}

	@Override
	public void newTable2() {
		timetableRepository.newTable2();
	}

	@Override
	public void newTable3() {
		timetableRepository.newTable3();
	}

	@Override
	public List<Integer> checkTimetable(int dayID, int periodID, int start_year, int teacherID, int weekID) {
		return timetableRepository.checkTimetable(dayID, periodID, start_year, teacherID, weekID);
	}

}
