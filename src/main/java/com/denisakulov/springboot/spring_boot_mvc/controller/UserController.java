package com.denisakulov.springboot.spring_boot_mvc.controller;

import com.denisakulov.springboot.spring_boot_mvc.model.User;
import com.denisakulov.springboot.spring_boot_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {


	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String showAll() {
		return "redirect:/login";
	}

	@GetMapping(value = "/user")
	public String showAllUsers(Principal principal, ModelMap model) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("principalUser", user);
		return "hello";
	}

	@GetMapping(value = "/admin")
	public String showAadmins(@ModelAttribute("user") User user, Principal principal, ModelMap model) {
		User principalUser = userService.findByUsername(principal.getName());
		model.addAttribute("principalUser", principalUser);
		model.addAttribute("users", userService.listUsers());
		model.addAttribute("adduser", user);
		model.addAttribute("rolelist", userService.listRoles());
		return "index";
	}

	@GetMapping(value = "/login")
	public String getLoginPage() {
		return "login.html";
	}


	@PostMapping(value = "/saveuser")
	public String saveUser(@ModelAttribute("user") User user,
							@RequestParam("val") List<Long> roles) {
		user.setRoles(userService.hashSetRolesByListId(roles));
		userService.save(user);
		return "redirect:/admin";
	}



	@PostMapping(value = "/deleteuser")
	public String removeUser(@RequestParam("id") Long id, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if(user.getId().equals(id)) {
			userService.delete(id);
			return "redirect:/login";
		} else {
			userService.delete(id);
			return "redirect:/admin";
		}
	}
}

