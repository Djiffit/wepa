package wad.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wad.domain.Person;
import wad.domain.Tag;
import wad.domain.Task;
import wad.repository.PersonRepository;
import wad.repository.TagRepository;
import wad.repository.TaskRepository;
import wad.service.NotificationService;
import wad.service.PersonService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Configuration
@Profile("default")
public class TestConfiguration {

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

    @PostConstruct
    public void init() {
        Person fds = new Person();
        fds.setUsername("admin");
        fds.setPassword("admin");
        personRepository.save(fds);
        Random random = new Random();
        Person person = new Person("pekka", "pekka", "pekka");
        person.setPassword("pekka");
        personRepository.save(person);
        Tag tag = new Tag();
        tag.setName("TESTITAGI");
        List<Task> tasks = tag.getTasks();
        tag.setTasks(tasks);
        tagRepository.save(tag);
        tag = new Tag();
        tag.setName("maanantaita");
        tasks = tag.getTasks();
        tag.setTasks(tasks);
        tagRepository.save(tag);
        tag = new Tag();
        tag.setName("perjantai");
        tasks = tag.getTasks();
        tag.setTasks(tasks);
        tagRepository.save(tag);
        tag = new Tag();
        tag.setName("iltana");
        tasks = tag.getTasks();
        tag.setTasks(tasks);
        tagRepository.save(tag);
        for (int i = 0; i < 25; i++) {
            Task task = new Task();
            task.setName("Buy some strawberries for my computer");
            task.setDescription("Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy some strawberries for my computer Buy");
            task.setPriority(random.nextInt(10));
            taskRepository.save(task);
        }
    }
}
