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

//    @PostConstruct
//    @Transactional
//    public void init() {
//        Random random = new Random();
//        Person person = new Person("pekka", "pekka", "pekka");
//        person.setPassword("pekka");
//        personRepository.save(person);
//        Tag tag = new Tag();
//        tag.setName("perkele");
//        List<Task> tasks = tag.getTasks();
//        tag.setTasks(tasks);
//        tagRepository.save(tag);
//        tag = new Tag();
//        tag.setName("vittu");
//        tasks = tag.getTasks();
//        tag.setTasks(tasks);
//        tagRepository.save(tag);
//        tag = new Tag();
//        tag.setName("saatana");
//        tasks = tag.getTasks();
//        tag.setTasks(tasks);
//        tagRepository.save(tag);
//        tag = new Tag();
//        tag.setName("helvetti");
//        tasks = tag.getTasks();
//        tag.setTasks(tasks);
//        tagRepository.save(tag);
//        for (int i = 0; i < 25; i++) {
//
//            Task task = new Task();
//            task.setName("Buy some strawberries for my computer");
//            task.setDescription("Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy");
//            task.setPriority(random.nextInt(10));
//            List<Tag> tags = task.getTags();
//            taskRepository.save(task);
//            tags.add(tagRepository.findAll().get(0));
//            tags.add(tagRepository.findAll().get(1));
//            tags.add(tagRepository.findAll().get(2));
//            tags.add(tagRepository.findAll().get(3));
//            tagRepository.findAll().get(3).getTasks().add(task);
//            tagRepository.findAll().get(1).getTasks().add(task);
//            tagRepository.findAll().get(2).getTasks().add(task);
//            tagRepository.findAll().get(0).getTasks().add(task);
//            tagRepository.save(tagRepository.findAll().get(0));
//            task.setTags(tags);
//            taskRepository.save(task);
//        }
//    }

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
