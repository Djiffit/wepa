package wad.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import wad.domain.Person;
import wad.repository.PersonRepository;
import wad.service.Notification;
import wad.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {

        String username = a.getPrincipal().toString();
        String password = a.getCredentials().toString();
        Person person = personRepository.findByUsername(username);

        if (person == null) {
            notificationService.add(new Notification("error", "Unable to authenticate user!"));
            throw new AuthenticationException("Unable to authenticate user " + username) {
            };
        }

        if (!BCrypt.hashpw(password, person.getSalt()).equals(person.getPassword())) {
            notificationService.add(new Notification("error", "Unable to authenticate user!"));
            throw new AuthenticationException("Unable to authenticate user " + username) {
            };
        }

        notificationService.add(new Notification("success", "Successfully logged in as " + username + "!"));

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("USER"));
        if (username.equals("admin")) {
            grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return new UsernamePasswordAuthenticationToken(person.getUsername(), password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

}
