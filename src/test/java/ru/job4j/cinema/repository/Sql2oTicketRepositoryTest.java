package ru.job4j.cinema.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Sql2oTicketRepositoryTest {
    private static Sql2oTicketRepository sql2oTicketRepository;
    private static Sql2oUserRepository sql2oUserRepository;
    private static User user;
    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);
        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
        user = new User(0, "Vasya Pupkin", "vasya@gmail.com", "12345");
        sql2oUserRepository.save(user);
    }

    @AfterEach
    public void clearRegister() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("DELETE FROM tickets");
            query.executeUpdate();
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var ticket = new Ticket(0, 1, 1, 1, user.getId());
        sql2oTicketRepository.save(ticket);
        var ticketFromBD = sql2oTicketRepository.findAll();
        assertThat(ticketFromBD.stream().findFirst().get()).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var ticket1 = new Ticket(0, 1, 1, 1, user.getId());
        var ticket2 = new Ticket(0, 2, 2, 2, user.getId());
        var ticket3 = new Ticket(0, 3, 3, 3, user.getId());
        sql2oTicketRepository.save(ticket1);
        sql2oTicketRepository.save(ticket2);
        sql2oTicketRepository.save(ticket3);
        var ticketsFromBD = sql2oTicketRepository.findAll();
        assertThat(new ArrayList<>(ticketsFromBD)).usingRecursiveComparison()
                .isEqualTo(List.of(ticket1, ticket2, ticket3));
    }

    @Test
    public void whenFindNothingAndReturnEmpty() {
        assertThat(sql2oTicketRepository.findAll().isEmpty());
    }

    @Test
    public void whenFailToSaveTheSameEmail() {
        var ticket1 = new Ticket(0, 1, 1, 1, user.getId());
        var ticket2 = new Ticket(0, 1, 1, 1, user.getId());
        sql2oTicketRepository.save(ticket1);
        var user2Saved = sql2oTicketRepository.save(ticket2);
        assertThat(user2Saved).isEmpty();
    }
}