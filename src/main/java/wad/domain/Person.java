package wad.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import java.util.Date;

@Entity
public class Person extends AbstractPersistable<Long> {

    @Column(unique = true)
    @Length(min = 4, max = 30)
    @NotBlank
    private String username;
    @Length(min = 4)
    @NotBlank
    private String password;
    private String salt;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastUpdated;


    public Person() {
        this.lastUpdated = new Date();
    }

    public Person(String name, String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
