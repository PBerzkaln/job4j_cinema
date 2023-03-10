package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@ThreadSafe
public class SimpleHallService implements HallService {
    private final HallRepository sql2oHallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.sql2oHallRepository = sql2oHallRepository;
    }

    @Override
    public Collection<Hall> findAll() {
        return sql2oHallRepository.findAll();
    }

    @Override
    public Optional<Hall> findById(int id) {
        return Optional.ofNullable(sql2oHallRepository.findById(id).get());
    }

    public int[] getRowCountInHallInSelectedSession(int id) {
        return IntStream.rangeClosed(1, sql2oHallRepository.findById(id).get().getRowCount()).toArray();

    }

    public int[] getPlaceCountInRowInCurrentHall(int id) {
        return IntStream.rangeClosed(1, sql2oHallRepository.findById(id).get().getPlaceCount()
                / sql2oHallRepository.findById(id).get().getRowCount()).toArray();
    }
}