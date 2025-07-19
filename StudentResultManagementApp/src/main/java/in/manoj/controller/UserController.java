package in.manoj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.manoj.entity.User;
import in.manoj.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user")  User user ,Model model) {
		boolean success = userService.registerUser(user);
		if(success) {
			model.addAttribute("succmsg", "User registration Sucessfully..");
			return "login";
		}else {
			model.addAttribute("errMsg", "Email already register");
			return "register";
		}
		
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@PostMapping("/login")
	public String doLogin(@RequestParam String email,
						@RequestParam  String password,
						Model model,HttpSession session) {
		User user = userService.login(email, password);
		
		if(user !=null) {
			session.setAttribute("user", user);
			return "redirect:/";
		}else {
			model.addAttribute("error", "invalid email and passowrd");
			return "login";
		}
	}
	@GetMapping("/forgot-password")
	public String forgotPasswordForm() {
		return "forgot-password";
	}
	
	@PostMapping("/forgot-password")
	public String resetpassword(@RequestParam String email,@RequestParam String newPassword,Model model) {
		boolean updated = userService.resetPassword(email, newPassword);
		if(updated) {
			model.addAttribute("msg","Password reset Succesfully..");
		}else {
			model.addAttribute("error", "email not found");
		}
		return "forgot-password";
	}
}
