package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;


import java.util.Collections;



@Controller
public class RegistrationController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/registration")
	public String registration(Model model) {

		model.addAttribute("userForm", new User());
		model.addAttribute("userRoles", userService.allRoles());
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(@ModelAttribute("userForm") User userForm,
						  BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "registration";
		}
		if (!userForm.getPassword().equals(userForm.getConfirmPassword())){
			model.addAttribute("passwordError", "Пароли не совпадают");
			return "registration";
		}
		
		userForm.setRoles(Collections.singleton(new Role(1L, "USER")));
		userService.saveUser(userForm);
		return "redirect:/login";
	}

}