package Dao;



import Model.Books;
import Model.Person;
import jakarta.validation.Valid;


import java.awt.print.Book;
import java.util.List;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



//@Getter
//@Setter
//@Data
@Getter
@Repository
public class BooksDao {
    private static final Logger logger = LoggerFactory.getLogger(BooksDao.class);
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public BooksDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Books books = new Books();



    //получение всех книг
    public List<Books> getAllBooks() {
        try {
            String sql = "SELECT * FROM book";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Books.class));
        } catch (Exception e) {
            logger.error("Ошибка при получении списка всех книг", e);
            throw new RuntimeException(e);
        }
    }

    //создание книги
    public void saveBooks (@Valid Books books){
        try {
            String sql = "INSERT INTO book (title, author, year, owner) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, books.getTitle(), books.getAuthor(), books.getYear(), books.getOwnerId());
        } catch (Exception e) {
            logger.error("Ошибка при сохранении объекта book", e);
            throw new RuntimeException("Ошибка при сохранении объекта book", e);
        }
    }

    //получение объекта book по id
    public Books getBookById(long id) {
        try {
            String sql = "SELECT * FROM book WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Books.class), id);
        } catch (Exception e) {
            logger.error("Ошибка при получении объекта book", e);
            throw new RuntimeException("Ошибка при получении объекта book", e);
        }
    }

    //обновление объекта book
    public void updateBook(@Valid Books bookFromForm) {
        try {
            String sql = "UPDATE book SET title = ?, author = ?, year = ?, owner = ? WHERE id = ?";
            jdbcTemplate.update(sql, bookFromForm.getTitle(), bookFromForm.getAuthor(), bookFromForm.getYear(), bookFromForm.getOwnerId(), bookFromForm.getId());
        } catch (Exception e) {
            logger.error("Ошибка при обновлении объекта book", e);
            throw new RuntimeException("Ошибка при обновлении объекта book", e);
        }
    }

    //удаление объекта book
    public void deleteBook(long id) {
        try {
            String sql = "DELETE FROM book WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении объекта book", e);
            throw new RuntimeException("Ошибка при удалении объекта book", e);
        }
    }

    // назначение книги читателю
    public void assignBookToPerson(long bookId, Person person) {
        try {
            String updateQuery = "UPDATE book SET owner = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(updateQuery, person.getId(), bookId);

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Книга с ID " + bookId + " не найдена");
            }
        } catch (Exception e) {
            logger.error("Ошибка при назначении книги", e);
            throw new RuntimeException("Ошибка при назначении книги", e);
        }
    }

    // удаляем книгу у читателя
    public void deleteBookToPerson(long bookId) {
        try {
            String sql = "DELETE FROM book WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, bookId);
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Книга с ID " + bookId + " не найдена");
            }
        } catch (Exception e) {
            logger.error("Ошибка при назначении книги", e);
            throw new RuntimeException("Ошибка при назначении книги", e);
        }
    }
}