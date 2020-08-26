package controllers;

import configs.AppConfig;
import configs.HibernateConfig;
import models.security_models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import security.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@ComponentScan(basePackageClasses = {AppConfig.class, HibernateConfig.class})
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "security/registration";
    }

    @PostMapping
    public String registrationAction(@Valid @ModelAttribute("user") User user,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException("validation error");
        userService.createUser(user);
        return "security/login_page";
    }

}
