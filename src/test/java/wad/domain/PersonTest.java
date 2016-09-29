package wad.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.repository.PersonRepository;
import wad.service.PersonService;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void savePerson() throws Exception {
        Person person = new Person();
        person.setUsername("Jack Bauer");
        person.setPassword("poewkprowker");

        personRepository.save(person);

        Person retrieved = personRepository.findByUsername("Jack Bauer");
        assertNotNull(retrieved);
    }

    @Test
    public void savePersonDoesntWorkWithEmptyName() throws Exception {
        Person person = new Person();
        person.setUsername("");
        person.setPassword("kowekrpowerkpo");


        try {
            personRepository.save(person);
        } catch (Exception ex) {
        }

        Person retrieved = personRepository.findByUsername("");
        assertNull(retrieved);
    }

    @Test
    public void savePersonDoesntWorkWithEmptyPassword() throws Exception {
        Person person = new Person();
        person.setUsername("Korson Jan");
        person.setPassword("");

        try {
            personRepository.save(person);
        } catch (Exception e) {

        }

        Person retrieved = personRepository.findByUsername("Korson Jan");
        assertNull(retrieved);
    }

}