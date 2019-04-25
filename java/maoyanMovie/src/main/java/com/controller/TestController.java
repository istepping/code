package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.Test;
import com.service.TestService;


@Controller
@RequestMapping("/Test")
public class TestController {

	@Autowired
	private TestService testService;
	@RequestMapping("/getTestList")
	public ModelAndView getFoodList(){
		ModelAndView mav=new ModelAndView("TestList");
		//List<fddFood> list=fddFoodService.getFoodList();
//		mav.addObject("AllFoodModel",list);
//		mav.addObject("username",username);
//		mav.addObject("deskid",deskid);
		return mav;
	}
	
	@RequestMapping("/getTestListJSON")
	@ResponseBody
	public List<Test> getTestListJSON() {
		List<Test> list = testService.getTestList();
		return list;
	}
}
