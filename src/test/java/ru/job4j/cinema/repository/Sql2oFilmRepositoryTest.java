package ru.job4j.cinema.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Properties;

public class Sql2oFilmRepositoryTest {
    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFindAllThenGetAll() {
        var film1 = new Film(1, 1994, 1, 16, 142, 1,
                "Форрест Гамп", new StringBuilder()
                .append("В центре действия фильма находится главный герой — Форрест Гамп ")
                .append("созвучно с англ. forest gump, то есть «лесной болван») из вымышленного города Гринбоу, ")
                .append("штат Алабама. По сюжету, умственно отсталого Форреста жизнь как птичье пёрышко ")
                .append("проносит через важнейшие события американской истории второй половины XX века. ")
                .append("Впрочем, он не теряется, и благодаря своим спортивным способностям, ")
                .append("дружелюбному характеру, а также привитой матерью необыкновенной жизнестойкости ")
                .append("совершает военный подвиг, добивается «американской мечты» и невольно, ")
                .append("походя, оказывает влияние на политику и популярную культуру США.").toString());
        var film2 = new Film(2, 2022, 2, 18, 68, 2,
                "Дом Дракона", new StringBuilder()
                .append("Сериал рассказывает о событиях на вымышленном континенте Вестерос, ")
                .append("происходивших примерно за 200 лет до событий «Игры престолов». ")
                .append("Сюжет сконцентрирован на «Пляске Драконов» — гражданской войне ")
                .append("(129—131 год от Завоевания Эйгона I), причиной которой стала борьба ")
                .append("между двумя ветвями дома Таргариенов. «Пляска» началась после болезненной ")
                .append("смерти короля Визериса I, когда принцесса Рейнира, поддержанная партией «чёрных», ")
                .append("и принц Эйгон Таргариен, поддержанный партией «зелёных», одновременно объявили ")
                .append("о своих правах на Железный трон.").toString());
        var film3 = new Film(3, 1994, 3, 18, 154, 3,
                "Криминальное чтиво", new StringBuilder()
                .append("Вслед за предыдущим фильмом Тарантино «Бешеные псы»")
                .append("части сюжета «Криминального чтива» были разделены перемешаны ")
                .append("и показаны в «неправильном» порядке; ")
                .append("техника, до этого использовавшаяся режиссёрами французской «Новой волны», ")
                .append("в частности Жан-Люком Годаром и Франсуа Трюффо, а также Стэнли Кубриком в «Убийстве».")
                .append("Всего в сценарии можно насчитать шесть частей, ")
                .append("при этом авторские названия имеют три из них: ")
                .append("«Винсент Вега и жена Марселласа Уоллеса» (Vincent Vega and Marsellus Wallace’s Wife), ")
                .append("«Золотые часы» (The Gold Watch) и «Ситуация с Бонни» (The Bonnie Situation).")
                .append("Первая и последняя части (Ограбление) пересекаются во времени и происходят в одном ")
                .append("и том же месте. Две хронологически последовательные части ")
                .append("(«Винсент Вега и жена Марселласа Уоллеса» и «Золотые часы») ")
                .append("показаны также одна за другой.").toString());
        var film4 = new Film(4, 1981, 4, 6, 115, 4,
                "Индиана Джонс: В поисках утраченного ковчега", new StringBuilder()
                .append("Фильм рассказывает о том, как Джонс, ")
                .append("работая по заказу военной разведки США, ")
                .append("отправляется на поиски загадочного Ковчега Завета, ")
                .append("в которых ему помогают его старый друг Саллах ")
                .append("и экс-возлюблённая Мэрион Рэйвенвуд. ")
                .append("Он должен добыть ковчег раньше, чем это сделают нацисты ")
                .append("и его оппонент, французский археолог Рене Беллок.").toString());
        var film5 = new Film(5, 1972, 5, 16, 175, 5,
                "Крестный отец", new StringBuilder()
                .append("Считается величайшим гангстерским фильмом ")
                .append("по мнению Американского института киноискусства ")
                .append("и одним из лучших фильмов в истории кинематографа. ")
                .append("Стабильно входит в первую пятёрку 250 лучших фильмов ")
                .append("по версии веб-сайта IMDb (2-е место по состоянию на 12 января 2022 года).").toString());
        var result = sql2oFilmRepository.findAll();
        assertThat(result).isEqualTo(List.of(film1, film2, film3, film4, film5));
    }
}