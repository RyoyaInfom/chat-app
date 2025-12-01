package in.tech_camp.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.form.LoginForm;
import in.tech_camp.chat_app.form.UserEditForm;
import in.tech_camp.chat_app.form.UserForm;
import in.tech_camp.chat_app.repository.UserRepository;
import in.tech_camp.chat_app.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;




@Controller
@AllArgsConstructor
public class UserController {
  
  private final UserRepository userRepository;
  private final UserService userService;


  @GetMapping("/users/sign_up")
  public String showSignUp(Model model) {
      model.addAttribute("userForm",new UserForm());
      return "users/signUp";
  }

  @PostMapping("/user")
  public String createUser(@ModelAttribute("userForm") UserForm userForm, Model model) {
      UserEntity userEntity = new UserEntity();
      userEntity.setName(userForm.getName());
      userEntity.setEmail(userForm.getEmail());
      userEntity.setPassword(userForm.getPassword());

      try{
        userService.createUserWithEncryptedPassword(userEntity);
      }catch(Exception e){
        System.out.println("エラー:" + e);
        return "/users/signUp";
      }
      
      return "redirect:/";
  }

  @GetMapping("/users/login")
  public String LoginUser(Model model) {
    model.addAttribute("loginForm", new LoginForm());
      return "users/login";
  }
  
  @GetMapping("/login")
  public String showLoginWithError(@RequestParam(value = "error")String error, Model model) {
      if(error != null){
        model.addAttribute("loginError","invalid email or password.");
      }
      return("users/login");
  }
  
  @GetMapping("/users/{userId}/edit")
  public String editUserForm(@PathVariable("userId") Integer userId, Model model) {
    UserEntity userEntity = userRepository.findById(userId);

    UserEditForm userForm = new UserEditForm();
    userForm.setId(userEntity.getId());
    userForm.setName(userEntity.getName());
    userForm.setEmail(userEntity.getEmail());

    model.addAttribute("user", userForm);
    return "users/edit";
  }

  @PostMapping("/users/{userId}")
  public String updateUser(@PathVariable("userId") Integer userId, @ModelAttribute("user") UserEditForm userEditForm, Model model) {
    UserEntity user = userRepository.findById(userId);
    user.setName(userEditForm.getName());
    user.setEmail(userEditForm.getEmail());

    try {
      userRepository.update(user);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      model.addAttribute("user", userEditForm);
      return "users/edit";
    }
    return "redirect:/";
  }
  
  
  
}
