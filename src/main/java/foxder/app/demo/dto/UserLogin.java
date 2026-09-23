package foxder.app.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLogin {
    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$")
    String email;
    @NotBlank @Size(min = 8, max = 16) @Pattern(regexp = "^[A-Z][a-zA-Z0-9@#$&]*[@#$&][a-zA-Z0-9@#$&]*$")
    String password;
}
