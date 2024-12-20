package Model;

import lombok.*;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Person {

    private long id;           // Уникальный идентификатор человека
    private String fullName;   // Полное имя
    private int birthYear;     // Год рождения
    private List<Books> books; // Список книг, принадлежащих человеку

}

