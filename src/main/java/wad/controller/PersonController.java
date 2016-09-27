package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Person;
import wad.repository.PersonRepository;
import wad.service.Notification;
import wad.service.NotificationService;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestParam String username, @RequestParam String password) {
        if (personRepository.findByUsername(username) != null
                || username.length() < 5
                || password.length() < 5) {
            notificationService.add(new Notification("error", "Error while creating account!"));
            return "redirect:/login";
        }
        notificationService.add(new Notification("success", "Account successfully created! Please log in!"));
        Person person = new Person();
        person.setPassword(password);
        person.setUsername(username);
        personRepository.save(person);
        return "redirect:/login";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        Person person = personRepository.findOne(id);
        if (person != null) {
            personRepository.delete(person);
            notificationService.add(new Notification("success", "User successfully deleted!"));
        }
        return "redirect:/persons/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllUsers(Model model) {
        model.addAttribute("users", personRepository.findAll());
        model.addAttribute("successes", notificationService.getSuccess());
        return "users";
    }
}
