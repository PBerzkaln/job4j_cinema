package ru.job4j.cinema.repository;

import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Properties;
import java.util.random.RandomGenerator;

public class Sql2oHallRepositoryTest {
    private static Sql2oHallRepository sql2oHallRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetAll() {
        var hall1 = new Hall(1, 3, 15, "Зал с пуфиками",
                "Зал с мягкими пуфиками, большой экран, качественный звук.");
        var hall2 = new Hall(2, 6, 72, "2D/3D",
                "Стандартный кинозал с возможностью демонстрации фильмов в 2D и 3D форматах.");
        var hall3 = new Hall(3, 4, 60, "IMAX",
                new StringBuilder().append("Кинозалы, оборудованные самой совершенной технологией кинопоказа IMAX, ")
                        .append("которая полностью погружает зрителя в происходящее на экране. ")
                        .append("Эффект присутствия создается благодаря огромному экрану, ")
                        .append("проекторам с кастомизированными линзами и особой геометрии зала.").toString());
        var result = sql2oHallRepository.findAll();
        assertThat(result).isEqualTo(List.of(hall1, hall2, hall3));
    }

    @Test
    public void whenFindById() {
        var hall1 = new Hall(1, 3, 15, "Зал с пуфиками",
                "Зал с мягкими пуфиками, большой экран, качественный звук.");
        var result = sql2oHallRepository.findById(hall1.getId()).get();
        assertThat(result).isEqualTo(hall1);
    }

    @Test
    public void whenNotFindById() {
        assertThat(sql2oHallRepository.findById(
                RandomGenerator.getDefault().nextInt(100, 150))).isEqualTo(empty());
    }
}