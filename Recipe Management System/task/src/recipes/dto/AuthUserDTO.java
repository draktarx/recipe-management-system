package recipes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthUserDTO {

    @NotBlank
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    public AuthUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthUserDTO() {
    }

}
