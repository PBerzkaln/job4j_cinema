package ru.job4j.cinema.repository;

import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;

import java.util.List;
import java.util.Properties;
import java.util.random.RandomGenerator;

public class Sql2oFileRepositoryTest {
    private static Sql2oFileRepository sql2oFileRepository;

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
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetAll() {
        var file1 = new File(1, "Forrest Gump", "files/Forrest_Gump.jpeg");
        var file2 = new File(2, "House of the Dragon", "files/House_of_the_Dragon.jpeg");
        var file3 = new File(3, "Pulp Fiction", "files/Pulp_Fiction.jpeg");
        var file4 = new File(4, "Raiders of the Lost ark", "files/Raiders_of_the_Lost_ark.jpeg");
        var file5 = new File(5, "The Godfather", "files/The_Godfather.jpeg");
        var result = sql2oFileRepository.findAll();
        assertThat(result).isEqualTo(List.of(file1, file2, file3, file4, file5));
    }

    @Test
    public void whenFindById() {
        var file1 = new File(1, "Forrest Gump", "files/Forrest_Gump.jpeg");
        var result = sql2oFileRepository.findById(file1.getId()).get();
        assertThat(result).isEqualTo(file1);
    }

    @Test
    public void whenNotFindById() {
        assertThat(sql2oFileRepository.findById(
                RandomGenerator.getDefault().nextInt(100, 150))).isEqualTo(empty());
    }
}