package com.ticketing.catalogservice.repository;

import com.ticketing.catalogservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByEventId(Long eventId);

    List<Seat> findByEventIdAndSection(Long eventId, String section);

    long countByEventIdAndSection(Long eventId, String section);

    void deleteByEventIdAndSection(Long eventId, String section);
}
