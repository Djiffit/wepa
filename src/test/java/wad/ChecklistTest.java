package wad;

import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.repository.PersonRepository;
import wad.repository.TagRepository;
import wad.repository.TaskRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChecklistTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PersonRepository personRepository;

    @After
    public void after() {
        tagRepository.deleteAll();
        personRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void canSignUpAndLoginToAccount() {
        signUpAndLogin();
        assertTrue(pageSource().contains("Success!"));
    }

    @Test
    public void canCreateTask() {
        createTask();
        assertTrue(pageSource().contains("Title of the task"));
    }

    @Test
    public void canDeleteTask() {
        createTask();
        submit(find("#deleteTaskForm").first());
        assertTrue(pageSource().contains("Task was successfully deleted"));
    }

    @Test
    public void canCreateTag() {
        signUpAndLogin();
        createTag();
        assertTrue(pageSource().contains("Tag was created successfully"));
    }

    @Test
    public void tagCanBeAddedToTask() {
        createTask();
        createTag();
        click(find("#addTag").first());
        assertTrue(pageSource().contains("Tag added successfully"));
    }

    @Test
    public void tagCanBeDeleted() {
        signUpAndLogin();
        createTag();
        click(find("#deleteTag"));
        assertTrue(pageSource().contains("Tag was successfully deleted!"));
    }

    @Test
    public void canBrowseTasksByTag() {
        createTask();
        createTask();
        createTag();
        assertTrue(2 == find("#task").size());
        click(find("#addTag").first());
        click(find("#Thenewtag"));
        assertTrue(1 == find("#task").size());
    }

    @Test
    public void taskCanBeEdited() {
        createTask();
        click(find("#editTaskLink").first());
        fill(find("#taskTitle")).with("This is an edited title");
        click(find("#saveButton"));
        assertTrue(pageSource().contains("This is an edited title"));
    }

    @Test
    public void taskSpecificTagCanBeDeleted() {
        createTask();
        createTag();
        click(find("#addTag").first());
        click(find("#editTaskLink"));
        click(find("#deleteTag"));
        assertTrue(pageSource().contains("Tag was successfully removed from the task"));
    }

    @Test
    public void invalidInputsDontWork() {
        goTo("http://localhost:" + port + "/login");
        fill(find("#signUpName")).with("Aleksanteri");
        click(find("#signUpButton"));

        assertTrue(pageSource().contains("Error"));

        fill(find("#logInName")).with("Aleksanteri");
        click(find("#logInButton"));

        assertTrue(pageSource().contains("Error"));
        goTo("http://localhost:" + port);

        createTask();

        click(find("#submitNewTag"));

        assertTrue(pageSource().contains("Error"));
        goTo("http://localhost:" + port);

        click("#saveTask");

        assertTrue(pageSource().contains("Error"));
        goTo("http://localhost:" + port);
        click(find("#editTaskLink").first());

        fill(find("#taskTitle")).with("");

        click(find("#saveButton"));
        assertTrue(pageSource().contains("Error"));
    }

    private void createTag() {
        fill(find("#newTagField")).with("Thenewtag");
        click(find("#submitNewTag"));
    }

    private void createTask() {
        signUpAndLogin();
        goTo("http://localhost:" + port);
        fill(find("#taskTitle")).with("Title of the task");
        fill(find("#description")).with("Description of the task");
        click("#saveTask");
    }

    public void signUpAndLogin() {
        goTo("http://localhost:" + port + "/login");
        fill(find("#signUpName")).with("Aleksanteri");
        fill(find("#signUpPassword")).with("Aleksanteri");
        click(find("#signUpButton"));

        assertEquals("Identification", title());

        fill(find("#logInName")).with("Aleksanteri");
        fill(find("#logInPassword")).with("Aleksanteri");
        click(find("#logInButton"));
    }
}

