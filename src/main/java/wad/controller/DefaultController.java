package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.repository.PersonRepository;
import wad.repository.TagRepository;
import wad.repository.TaskRepository;
import wad.service.NotificationService;
import wad.service.PersonService;

@Controller
@RequestMapping("*")
public class DefaultController {


        @Autowired
        private PersonService personService;

        @Autowired
        private NotificationService notificationService;

        @Autowired
        private TagRepository tagRepository;

        @Autowired
        private TaskRepository taskRepository;

        @Autowired
        private PersonRepository personRepository;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String viewLogin(Model model) {
        model.addAttribute("successes", notificationService.getSuccess());
        model.addAttribute("errors", notificationService.getAlert());
        return "login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String viewSignup(Model model) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        PageRequest pr = new PageRequest(0, 500, Sort.Direction.DESC, "priority");
        model.addAttribute("tasks", taskRepository.findAll(pr));
        model.addAttribute("successes", notificationService.getSuccess());
        model.addAttribute("errors", notificationService.getAlert());
        model.addAttribute("tags", tagRepository.findAll());
        if (personService.getAuthenticatedPerson() != null) {
            model.addAttribute("username", personService.getAuthenticatedPerson().getUsername());
        }
        return "index";
    }
}
