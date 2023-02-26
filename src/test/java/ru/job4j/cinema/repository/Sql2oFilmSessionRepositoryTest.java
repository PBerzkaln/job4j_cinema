package ru.job4j.cinema.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class Sql2oFilmSessionRepositoryTest {
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetAll() {
        var filmSession1 = new FilmSession(1, 1, 1, 250,
                LocalDateTime.parse("2023-03-01T10:00:00"), LocalDateTime.parse("2023-03-01T12:30:00"));
        var filmSession2 = new FilmSession(2, 2, 2, 450,
                LocalDateTime.parse("2023-02-28T14:00:00"), LocalDateTime.parse("2023-03-01T18:00:00"));
        var filmSession3 = new FilmSession(3, 3, 1, 250,
                LocalDateTime.parse("2023-03-02T15:00:00"), LocalDateTime.parse("2023-03-01T18:00:00"));
        var filmSession4 = new FilmSession(4, 4, 2, 350,
                LocalDateTime.parse("2023-03-05T11:00:00"), LocalDateTime.parse("2023-03-01T12:00:00"));
        var filmSession5 = new FilmSession(5, 5, 3, 750,
                LocalDateTime.parse("2023-02-27T17:00:00"), LocalDateTime.parse("2023-03-01T20:30:00"));
        var result = sql2oFilmSessionRepository.findAll();
        assertThat(result).isEqualTo(List.of(filmSession1, filmSession2, filmSession3, filmSession4, filmSession5));
    }
}