package com.mock.project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mock.project.entity.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long>{
	@Transactional
	@Query(value = "select NewTable2.periodID, NewTable2.dayID, Teacher.name, [Subject].subject_name, NewTable2.classID, NewTable2.weekID "
			+ "from NewTable2 full join Timetable "
			+ "on Timetable.periodID = NewTable2.periodID and Timetable.dayID = NewTable2.dayID and Timetable.classID = NewTable2.classID and Timetable.weekID = NewTable2.weekID "
			+ "full join Teacher on Teacher.teacherID = Timetable.teacherID "
			+ "full join [Subject] on [Subject].subjectID = Teacher.subjectID "
			+ "group by NewTable2.periodID, NewTable2.dayID, Teacher.name, [Subject].subject_name, NewTable2.classID, NewTable2.weekID "
			+ "having NewTable2.classID = :param1 and NewTable2.weekID = :param2 ",
			nativeQuery = true)
	Iterable<Object[]> viewTimetable(@Param("param1") Integer param1, @Param("param2") Integer param2);
	
	@Transactional
	@Modifying
	@Query(value = "insert into Timetable(classID, dayID, periodID, teacherID, weekID) values (:param1, :param2, :param3, :param4, :param5)",
			nativeQuery = true)
	void addTimetable(
			@Param("param1") int param1,
			@Param("param2") int param2,
			@Param("param3") int param3,
			@Param("param4") int param4,
			@Param("param5") int param5);
	
	@Transactional
	@Modifying
	@Query(value = "delete from Timetable where classID = :param1 and periodID = :param2 and dayID = :param3 and weekID = :param4",
			nativeQuery = true)
	void deleteTimetable(
			@Param("param1") int param1,
			@Param("param2") int param2,
			@Param("param3") int param3,
			@Param("param4") int param4);
	
	@Transactional
	@Modifying
	@Query(value="drop table NewTable2\r\n"
			+ "select Period.periodID, [Day].dayID, Class.classID, [Week].weekID into NewTable2 from Period, [Day], Class, [Week]", nativeQuery = true)
	void newTable2();
	
	@Transactional
	@Modifying
	@Query(value="drop table NewTable3\r\n"
			+ "select Period.periodID, [Day].dayID, Teacher.teacherID, start_year.start_year , [Week].weekID into NewTable3 from Period, [Day], Teacher, start_year, [Week]"
			, nativeQuery = true)
	void newTable3();
	
	@Transactional
	@Query(value = "select NewTable3.periodID, NewTable3.dayID, Class.class_name, NewTable3.teacherID, NewTable3.start_year, NewTable3.weekID "
			+ "from NewTable3 full join Timetable "
			+ "on Timetable.periodID = NewTable3.periodID and Timetable.dayID = NewTable3.dayID and Timetable.teacherID = NewTable3.teacherID and Timetable.weekID = NewTable3.weekID "
			+ "full join Class on Class.classID = Timetable.classID "
			+ "full join start_year on start_year.start_year = Class.start_year "
			+ "group by NewTable3.periodID, NewTable3.dayID, Class.class_name, start_year.start_year, NewTable3.teacherID, NewTable3.start_year, NewTable3.weekID "
			+ "having NewTable3.teacherID = :param1 and NewTable3.start_year = (select top 1 * from start_year) and NewTable3.weekID =:param2 ",
			nativeQuery = true)
	Iterable<Object[]> viewSchedule(
			@Param("param1") long teacherID,
			@Param("param2") long weekID);
	
	@Transactional
	@Query(value = "select Timetable.classID from Timetable inner join Class on Timetable.classID = Class.classID inner join Week on Timetable.weekID = [Week].weekID "
			+ "where Timetable.dayID = :param1 and Timetable.periodID = :param2 and Class.start_year = :param3 and Timetable.teacherID = :param4 and Timetable.weekID = :param5 ", nativeQuery = true)
	List<Integer> checkTimetable(
			@Param("param1") int param1,
			@Param("param2") int param2,
			@Param("param3") int param3,
			@Param("param4") int param4,
			@Param("param5") int param5);
	
}
