package Model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Person {

    private long id;           // Уникальный идентификатор человека

    @NotNull(message = "Полное имя не должно быть null")
    @NotBlank(message = "Полное имя не должно быть пустым")
    @Size(max = 255, message = "Полное имя должно содержать не более 255 символов")
    private String fullName; // Полное имя

    @Min(value = 1900, message = "Год рождения должен быть не ранее 1900 года")
    @Max(value = java.time.Year.MAX_VALUE, message = "Год рождения не должен быть в будущем")
    private int birthYear; // Год рождения

    private List<Books> books; // Список книг, принадлежащих человеку

}

