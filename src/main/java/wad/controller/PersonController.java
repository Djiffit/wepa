package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        Person person = new Person();
        person.setPassword(password);
        person.setUsername(username);
        personRepository.save(person);
        return "redirect:/login";
    }
}
