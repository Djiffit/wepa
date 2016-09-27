package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    @RequestMapping(value = "/{id}/save", method = RequestMethod.POST)
    public String updateTask(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Integer priority,
                             Model model) {
        Task task = taskRepository.findOne(id);
        if (name.length() > 0
                && description.length() > 0
                && priority > -1
                && task != null) {
            task.setName(name);
            task.setDescription(description);
            task.setPriority(priority);
            notificationService.add(new Notification("success", "Task was edited successfully!"));
        } else {
            notificationService.add(new Notification("error", "All fields didn't have correct data!"));
            return "redirect:/tasks/" + id;
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/{id}")
    public String editTask(Model model,
                           @PathVariable Long id) {
        Task task = taskRepository.findOne(id);
        if (task != null) {
            model.addAttribute("task", taskRepository.findOne(id));
            model.addAttribute("errors", notificationService.getAlert());
            model.addAttribute("successes", notificationService.getSuccess());
        } else {
            notificationService.add(new Notification("error", "The desired task does not exist!"));
            return "redirect:/home";
        }
        return "edittask";
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String addTag(@RequestParam Long tagId, @PathVariable Long id) {
        Tag tag = tagRepository.findOne(tagId);
        Task task = taskRepository.findOne(id);
        if (tag != null && task != null) {
            task.getTags().add(tag);
            tag.getTasks().add(task);
            notificationService.add(new Notification("success", "Tag added successfully!"));
        } else {
            notificationService.add(new Notification("error", "Failed to add tag!"));
        }
        return "redirect:/home";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String createTask(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Integer priority,
                             @RequestParam Long tagId) {
        Task task = new Task();
        if (name.length() > 0
                && description.length() > 0
                && priority > -1) {
            task.setName(name);
            task.setDescription(description);
            task.setPriority(priority);
            taskRepository.save(task);
            notificationService.add(new Notification("success", "Task created successfully!"));
        } else {
            notificationService.add(new Notification("error", "Failed to create a new task!"));
        }
        if (tagId != null) {
            Tag tag = tagRepository.findOne(tagId);
            task.getTags().add(tag);
            tag.getTasks().add(task);
        }
        return "redirect:/index";
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findOne(id);
        if (task != null) {
            for (Tag tag : task.getTags()) {
                tag.getTasks().remove(task);
            }
            taskRepository.delete(task);
            notificationService.add(new Notification("success", "Task was successfully deleted!"));
        } else {
            notificationService.add(new Notification("error", "Failed to delete task!"));
        }
        return "redirect:/index";
    }

    @Transactional
    @RequestMapping(value = "/{id}/tag", method = RequestMethod.DELETE)
    public String deleteTag(@PathVariable Long id,
                            @RequestParam Long tagId) {
        Task task = taskRepository.findOne(id);
        Tag tag = tagRepository.findOne(tagId);
        if (tag != null && task != null) {
            task.getTags().remove(tag);
            tag.getTasks().remove(task);
            notificationService.add(new Notification("success", "Tag was successfully removed from the task!"));
        }
        return "redirect:/tasks/" + task.getId();
    }
}
