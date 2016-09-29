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
public class TagTest {

    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private TagRepository tagRepository;

    @Test
    public void saveTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("Jack Bauer");
        tagRepository.save(tag);

        Tag retrieved = tagRepository.findByName("Jack Bauer");
        assertNotNull(retrieved);
    }

    @Test
    public void saveTagDoesntWorkWithEmptyName() throws Exception {
        Tag tag = new Tag();
        tag.setName("");
        try {
            tagRepository.save(tag);
        } catch (Exception ex) {

        }

        Tag retrieved = tagRepository.findByName("");
        assertNull(retrieved);
    }

}