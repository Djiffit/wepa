package wad.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wad.domain.Person;
import wad.repository.PersonRepository;

import javax.annotation.PostConstruct;

@Configuration
@Profile("production")
public class ProductionConfiguration {


    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void init() {
        Person person = new Person();
        person.setUsername("admin");
        person.setPassword("admin");
        personRepository.save(person);
    }

}
