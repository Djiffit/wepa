package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Tag;
import wad.domain.Task;
import wad.repository.TagRepository;
import wad.repository.TaskRepository;
import wad.service.Notification;
import wad.service.NotificationService;
import wad.service.PersonService;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private PersonService personService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = RequestMethod.POST)
    public String createTag(@RequestParam String name) {
        Tag tag = new Tag();
        if (name.length() > 0) {
            tag.setName(name);
            tagRepository.save(tag);
            notificationService.add(new Notification("success", "Tag was created successfully!"));
        } else {
            notificationService.add(new Notification("error", "Tag did not have a proper name! Creation failed."));
        }
        return "redirect:/home";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteTag(@RequestParam Long tagId) {
        Tag tag = tagRepository.findOne(tagId);
        if (tag != null) {
            for (Task task : tag.getTasks()) {
                task.getTags().remove(tag);
            }
            tagRepository.delete(tag);
            notificationService.add(new Notification("success", "Tag was successfully deleted!"));
        } else {
            notificationService.add(new Notification("error", "Erroneus tag id provided."));
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/browse", method = RequestMethod.POST)
    public String browse(@RequestParam String tagName) {
        return "redirect:/tags/" + tagName;
    }

    @RequestMapping(value = "/{tag}")
    public String browseByTag(Model model,
                              @PathVariable String tag) {
        Tag t = tagRepository.findByName(tag);
        if (t != null) {
            List<Task> tasks = taskRepository.findTasksByTag(t);
            model.addAttribute("tasks", tasks);
            model.addAttribute("tags", tagRepository.findAll());
            if (personService.getAuthenticatedPerson() != null) {
                model.addAttribute("username", personService.getAuthenticatedPerson().getUsername());
            }
        } else {
            notificationService.add(new Notification("error", "Provided tag could not be found!"));
            return "redirect:/home";
        }
        return "index";
    }
}