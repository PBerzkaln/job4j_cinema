package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.SchedulePreview;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.*;

import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
public class SimpleScheduleService implements ScheduleService {
    private final FilmSessionRepository sql2oFilmSessionRepository;
    private final FilmRepository sql2oFilmRepository;
    private final HallRepository sql2oHallRepository;

    public SimpleScheduleService(FilmSessionRepository sql2oFilmSessionRepository,
                                 FilmRepository sql2oFilmRepository,
                                 HallRepository sql2oHallRepository) {
        this.sql2oFilmSessionRepository = sql2oFilmSessionRepository;
        this.sql2oFilmRepository = sql2oFilmRepository;
        this.sql2oHallRepository = sql2oHallRepository;
    }

    @Override
    public Collection<SchedulePreview> findAll() {
        return sql2oFilmSessionRepository.findAll()
                .stream()
                .map(s -> new SchedulePreview(s.getId(), s.getPrice(), s.getStartTime(),
                        s.getEndTime(), sql2oFilmRepository.findNameById(s.getFilmId()),
                        sql2oHallRepository.findNameById(s.getHallsId())))
                .toList();
    }

    @Override
    public Optional<FilmSession> findSessionById(int id) {
        return Optional.ofNullable(sql2oFilmSessionRepository.findById(id));
    }

    @Override
    public Optional<SchedulePreview> findPreviewById(int id) {
        var session = sql2oFilmSessionRepository.findById(id);
        return Optional.of(new SchedulePreview(id, session.getPrice(), session.getStartTime(),
                session.getEndTime(), sql2oFilmRepository.findNameById(session.getFilmId()),
                sql2oHallRepository.findNameById(session.getHallsId())));
    }
}