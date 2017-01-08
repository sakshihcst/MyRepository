package org.tcs.myApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tcs.myApp.mapper.UserDTO;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage(){
		ModelAndView model = new ModelAndView();
		model.addObject("welcomeNote","Welcome! Sign in to continue..");
		model.setViewName("home");
		return model;
		
	}
	
	@RequestMapping(value="/userCredentials", method = RequestMethod.GET)
	public @ResponseBody UserDTO getUserDetails(@RequestParam String userName, @PathVariable String password, ModelMap model){
		UserDTO userDTO = new UserDTO();
		return userDTO;
	}
	

}
