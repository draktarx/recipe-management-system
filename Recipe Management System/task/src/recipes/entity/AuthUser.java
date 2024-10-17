package recipes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AuthUser {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;

    private String password;

}
