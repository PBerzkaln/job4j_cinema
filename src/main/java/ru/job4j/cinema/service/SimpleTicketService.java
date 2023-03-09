package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;
    private final HallService hallService;

    public SimpleTicketService(TicketRepository ticketRepository, HallService hallService) {
        this.ticketRepository = ticketRepository;
        this.hallService = hallService;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Collection<Ticket> findAll() {
        return null;
    }
}