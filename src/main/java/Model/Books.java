package Model;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    private long id;          // Уникальный идентификатор книги
    private String title;     // Название книги
    private String author;    // Автор книги
    private int year;         // Год издания книги
    private Long ownerId;     // ID владельца (человека), может быть null

}
