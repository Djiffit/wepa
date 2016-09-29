package wad.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.repository.TagRepository;
import wad.repository.TaskRepository;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {


    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private TagRepository tagRepository;

    @Test
    public void saveTask() throws Exception {
        Task task = new Task();
        task.setName("Moro");
        task.setDescription("tere");
        taskRepository.save(task);
        Task retrieved = taskRepository.findByName("Moro");
        assertNotNull(retrieved);
    }

    @Test
    public void saveTaskDoesntWorkWithEmptyName() throws Exception {
        Task task = new Task();
        task.setName("");
        try {
            taskRepository.save(task);
        } catch (Exception ex) {

        }

        Task retrieved = taskRepository.findByName("");
        assertNull(retrieved);
    }

    @Test
    public void saveTaskDoesntWorkWithTooLongName() throws Exception {
        Task task = new Task();
        task.setName("12345678901234567890123456789012345678901234567890123456789012345678901234567890");

        try {
            taskRepository.save(task);
        } catch (Exception ex) {
        }

        Task retrieved = taskRepository.findByName("12345678901234567890123456789012345678901234567890123456789012345678901234567890");
        assertNull(retrieved);
    }

}