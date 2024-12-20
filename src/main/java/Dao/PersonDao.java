package Dao;


import Model.Person;
import jakarta.validation.Valid;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Setter
@Data
@Repository
public class PersonDao{

    private static final Logger logger = LoggerFactory.getLogger(PersonDao.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //получение всех людей
    public List<Person> getAllPeoples() {
        try {
            String sql = "SELECT * FROM person";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));
        } catch (Exception e) {
            logger.error("Ошибка при получении списка всех людей", e);
            throw new RuntimeException(e);
        }
    }

    //создание человека
    public void savePerson (@Valid Person person){
        try {
            String sql = "INSERT INTO person (full_name, birth_year) VALUES (?, ?)";
            jdbcTemplate.update(sql, person.getFullName(), person.getBirthYear());
        } catch (Exception e) {
            logger.error("Ошибка при сохранении объекта Person", e);
            throw new RuntimeException("Ошибка при сохранении объекта Person", e);
        }
    }

    //получение человека по id
    public Person getPersonById(long id) {
        try {
            String sql = "SELECT * FROM person WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Person.class), id);
        } catch (Exception e) {
            logger.error("Ошибка при получении объекта Person", e);
            throw new RuntimeException("Ошибка при получении объекта Person", e);
        }
    }

    //обновление человека
    public void updatePerson(@Valid Person personFromForm) {
        try {
            String sql = "UPDATE person SET full_name = ?, birth_year = ? WHERE id = ?";
            jdbcTemplate.update(sql, personFromForm.getFullName(), personFromForm.getBirthYear(), personFromForm.getId());
        } catch (Exception e) {
            logger.error("Ошибка при обновлении объекта Person", e);
            throw new RuntimeException("Ошибка при обновлении объекта Person", e);
        }
    }

    //удаление человека
    public void deletePerson(long id) {
        try {
            String sql = "DELETE FROM person WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            logger.error("Ошибка при удалении объекта Person", e);
            throw new RuntimeException("Ошибка при удалении объекта Person", e);
        }
    }
}