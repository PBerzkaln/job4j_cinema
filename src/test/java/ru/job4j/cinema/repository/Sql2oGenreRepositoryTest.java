package ru.job4j.cinema.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Genre;

import java.util.List;
import java.util.Properties;

public class Sql2oGenreRepositoryTest {
    private static Sql2oGenreRepository sql2oGenreRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oGenreRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetAll() {
        var genre1 = new Genre(1, "Комедийная драма");
        var genre2 = new Genre(2, "Фэнтези");
        var genre3 = new Genre(3, "Криминальный фильм");
        var genre4 = new Genre(4, "Приключения");
        var genre5 = new Genre(5, "Гангстерский фильм");
        var result = sql2oGenreRepository.findAll();
        assertThat(result).isEqualTo(List.of(genre1, genre2, genre3, genre4, genre5));
    }
}