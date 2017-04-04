package io.threeloop.bookaspot.web;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.threeloop.bookaspot.model.User;
import io.threeloop.bookaspot.service.UserService;

@Controller
public class UserController {

  private final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/login")
  public String login(User user) {
      return "user/login";
  }
  
  @GetMapping(value = "/user/register")
  public String register(User user) {
//      return "user/register";
    return "user/login";
  }
  
  @RequestMapping("/")
  public String home(Map<String, Object> model) {
      model.put("message", "Hello World");
      model.put("title", "Hello Home");
      model.put("date", new Date());
      return "index";
  }
  
  @RequestMapping(value = "/user/register", method = RequestMethod.POST)
  public String registerPost(@Valid User user, BindingResult result) {
      if (result.hasErrors()) {
//          return "user/register";
          return "user/login";
      }
      
      User registeredUser = userService.register(user);
      if (registeredUser != null) {
//         mailService.sendNewRegistration(user.getEmail(), registeredUser.getToken());
//          if(!requireActivation) {
              userService.autoLogin(user.getUsername());
              return "redirect:/";
//          }
//          return "user/register-success";
      } else {
          result.rejectValue("email", "error.alreadyExists", "This username or email already exists, please try to reset password instead.");
//          return "user/register";
          return "user/login";
      }
  }
  
}
