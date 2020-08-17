package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UserService;

@Controller
public class AuthenticationController {

	@Autowired
	UserService userService;
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login"); // resources/template/login.html
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register"); // resources/template/register.html
		return modelAndView;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		List<Project> listProjects = projectService.listAll();
		modelAndView.addObject("listProjects", listProjects);
		modelAndView.setViewName("home"); // resources/template/home.html
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminHome() {
		ModelAndView modelAndView = new ModelAndView();
		List<Project> listProjects = projectService.listAll();
		modelAndView.addObject("listProjects", listProjects);
		modelAndView.setViewName("admin"); // resources/template/admin.html
		return modelAndView;
	}

	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView();
		// Check for the validations
		if(bindingResult.hasErrors()) {
			modelAndView.addObject("successMessage", "Please correct the errors in form!");
			modelMap.addAttribute("bindingResult", bindingResult);
		}
		else if(userService.isUserAlreadyPresent(user)){
			modelAndView.addObject("successMessage", "user already exists!");			
		}
		// we will save the user if, no binding errors
		else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User is registered successfully!");
		}
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	@RequestMapping(value ="/newproject", method=RequestMethod.GET)
	public String showNewProjectForm(Model model) {
		
		Project project = new Project();
		model.addAttribute("project",project);
		return "newproject";
	}
	
	@RequestMapping(value= "/save", method=RequestMethod.POST)
	public String saveProject(@ModelAttribute("project") Project project) {
		
		projectService.save(project);
		return "redirect:/admin";
	}
	
	@RequestMapping(value= "/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editProject(@PathVariable(name="id") Long id) {
		
		ModelAndView edit= new ModelAndView("editproject");
		Project project = projectService.get(id);
		edit.addObject("project", project);
		return edit;
	}
	@RequestMapping(value= "/delete/{id}", method=RequestMethod.GET)
	public String saveProject(@PathVariable(name="id") Long id) {
		
		projectService.delete(id);
		return "redirect:/admin";
	}
	
	@RequestMapping(value= "/invest/{id}", method=RequestMethod.GET)
	public ModelAndView investProject(@PathVariable(name="id") Long id) {
		
		ModelAndView invest= new ModelAndView("newinvest");
		Project project = projectService.get(id);
		invest.addObject("project", project);
		return invest;
	}
	@RequestMapping(value= "/invest", method=RequestMethod.POST)
	public String saveInvest(@ModelAttribute("project") Project project) {
		
		projectService.save(project);
		return "redirect:/pledge";
	}
}
