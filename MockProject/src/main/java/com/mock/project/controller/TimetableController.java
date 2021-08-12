package com.mock.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mock.project.entity.Account;
import com.mock.project.entity.Class;
import com.mock.project.entity.Teacher;
import com.mock.project.entity.Timetable;
import com.mock.project.service.AccountService;
import com.mock.project.service.ClassService;
import com.mock.project.service.DayService;
import com.mock.project.service.MarkService;
import com.mock.project.service.PeriodService;
import com.mock.project.service.StudentService;
import com.mock.project.service.TeacherService;
import com.mock.project.service.TimetableService;
import com.mock.project.service.WeekService;

@Controller
public class TimetableController {
	Integer value = null;
	
	@Autowired
	private TimetableService timetableService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private DayService dayService;
	
	@Autowired
	private PeriodService periodService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private MarkService markService;
	
	@Autowired
	private WeekService weekService;
	
	@GetMapping("/viewtimetable")
	public String teacher(Model model) {
		model.addAttribute("classes", classService.findAllOrderByGradeID());
		model.addAttribute("weeks", weekService.findAll());
		return "XemThoiKhoaBieu";
	}
	
	@GetMapping("/searchClass")  
	public String searchClass(@RequestBody @RequestParam("classID") Integer classID,@RequestParam("weekID") Integer weekID, Model model)  
	{	
		model.addAttribute("classes", classService.findAllOrderByGradeID());
		model.addAttribute("classID", classID);
		model.addAttribute("weekID", weekID);
		model.addAttribute("weeks", weekService.findAll());
		if(classService.findByClassID(classID)!= null) {
			model.addAttribute("timetables", timetableService.viewTimetable(classID,weekID));
			System.out.println(model);
		}else {
			return "redirect:/viewtimetable";
		}
	    return "XemThoiKhoaBieu";  
	}
	
	@GetMapping("/addtimetable")
	public String addTimetable(Model model) {
		model.addAttribute("teachers", teacherService.findAll());
		model.addAttribute("days", dayService.findAll());
		model.addAttribute("periods", periodService.findAll());
		model.addAttribute("classes", classService.findAllOrderByGradeID());
		model.addAttribute("weeks", weekService.findAll());
		return "ThemThoiKhoaBieu";
	}
	
	@GetMapping("/searchFirst")  
	public String searchFirst(@RequestParam("classID") Integer classID, @RequestParam("weekID") Integer weekID, Model model)  
	{
		/*if (classID != null) {*/
			model.addAttribute("classes", classService.findAllOrderByGradeID());
			model.addAttribute("classID", classID);
			//value = classID;
		//}
		//else model.addAttribute("currentClassID", value);
		model.addAttribute("weekID", weekID);
		model.addAttribute("foundClass", classService.getOne(classID));
		model.addAttribute("timetables", timetableService.viewTimetable(classID, weekID));
		model.addAttribute("teachers", teacherService.findAll());
		model.addAttribute("days", dayService.findAll());
		model.addAttribute("periods", periodService.findAll());
		model.addAttribute("weeks", weekService.findAll());
		System.out.println(model);
	    return "ThemThoiKhoaBieu";  
	}
	
	@PostMapping("/newTimetable")
	public String save(@ModelAttribute("newTimetable") Timetable newTimetable, @RequestParam("classID") Integer classID,
			@RequestParam("dayID") Integer dayID, @RequestParam("periodID") Integer periodID, @RequestParam("teacherID") Integer teacherID, @RequestParam("weekID") Integer weekID,
			BindingResult bind, Model model) {
		model.addAttribute("classes", classService.findAllOrderByGradeID());
		model.addAttribute("classID", classID);
		model.addAttribute("weekID", weekID);
		model.addAttribute("foundClass", classService.getOne(classID));
		model.addAttribute("timetables", timetableService.viewTimetable(classID, weekID));
		model.addAttribute("teachers", teacherService.findAll());
		model.addAttribute("days", dayService.findAll());
		model.addAttribute("periods", periodService.findAll());
		List<Integer> classesID = timetableService.checkTimetable(dayID, periodID, classService.getOne(classID).getStartYear(), teacherID, weekID);
		for (Integer integer : classesID) {
			System.out.println(integer);
		}
		if(classesID.isEmpty() == false) {
			System.out.println("asdasdadasdasds, "+ classesID);
			model.addAttribute("fail","Giáo viên đã dạy tiết này!");
			return "redirect:/searchFirst?classID=" + classID+"&weekID=" + weekID;
		} else {
			System.out.println("asdasdadasdasdsssssssssssssssssss");
			model.addAttribute("success","Thêm thành công");	
			timetableService.addTimetable(classID, dayID, periodID, teacherID, weekID);
		}
		return "redirect:/searchFirst?classID=" + classID+"&weekID=" + weekID;
	}
	
	@GetMapping("/timetable/{classID}/{weekID}/{periodID}/{dayID}/delete")
	public String delete(@PathVariable int classID, @PathVariable int periodID, @PathVariable int dayID, @PathVariable int weekID, RedirectAttributes redirect) {
		timetableService.deleteTimetable(classID, periodID, dayID, weekID);
		redirect.addFlashAttribute("success", "Deleted successfully!");
		return "redirect:/searchFirst?classID=" + classID+"&weekID=" + weekID;
	}
	
	@GetMapping("/viewschedule")
	public String schedule(HttpServletRequest request,Model model) {
		model.addAttribute("weeks", weekService.findAll());
		Account account;
		String username = request.getUserPrincipal().getName();
		account = accountService.getOne(username);
		Teacher t = teacherService.getOne(account.getAccountID());
		model.addAttribute("teacher", t);
		return "XemLichDay";
	}
	
	@GetMapping("/searchWeek")
	public String viewSchedule(HttpServletRequest request, Model model, @RequestParam("weekID") Integer weekID) {
		model.addAttribute("weeks", weekService.findAll());
		model.addAttribute("weekID", weekID);
		Account account;
		String username = request.getUserPrincipal().getName();
		account = accountService.getOne(username);
		Teacher t = teacherService.getOne(account.getAccountID());
		model.addAttribute("teacher", t);
		model.addAttribute("schedules", timetableService.viewSchedule(t.getTeacherID(), weekID));
		System.out.println(timetableService.viewSchedule(t.getTeacherID(), weekID));
		System.out.println("ádasdas" + weekID);
		return "XemLichDay";
	}
	
}
