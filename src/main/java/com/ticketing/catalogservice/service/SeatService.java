package com.ticketing.catalogservice.service;

import com.ticketing.catalogservice.dto.SeatGenerationRequest;
import com.ticketing.catalogservice.dto.SeatRequestDto;
import com.ticketing.catalogservice.dto.SeatResponseDto;
import com.ticketing.catalogservice.entity.Seat;
import com.ticketing.catalogservice.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // ---- CRUD ----

    public SeatResponseDto createSeat(SeatRequestDto request) {
        Seat seat = new Seat();
        seat.setEventId(request.getEventId());
        seat.setSection(request.getSection());
        seat.setRowNumber(request.getRowNumber());
        seat.setSeatNumber(request.getSeatNumber());
        seat.setPrice(request.getPrice());

        Seat saved = seatRepository.save(seat);
        return toDto(saved);
    }

    public SeatResponseDto getSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + id));
        return toDto(seat);
    }

    public List<SeatResponseDto> getSeatsByEvent(Long eventId) {
        return seatRepository.findByEventId(eventId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SeatResponseDto updateSeat(Long id, SeatRequestDto request) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + id));

        seat.setEventId(request.getEventId());
        seat.setSection(request.getSection());
        seat.setRowNumber(request.getRowNumber());
        seat.setSeatNumber(request.getSeatNumber());
        seat.setPrice(request.getPrice());

        Seat saved = seatRepository.save(seat);
        return toDto(saved);
    }

    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new IllegalArgumentException("Seat not found: " + id);
        }
        seatRepository.deleteById(id);
    }

    // ---- Bulk generation ----

    /**
     * Generate seats for one event + one section.
     * Example: rows=10, seatsPerRow=20 -> 200 seats created.
     */
    @Transactional
    public int generateSeatsForEvent(Long eventId, SeatGenerationRequest request) {
        if (request.isReplaceIfExists()) {
            seatRepository.deleteByEventIdAndSection(eventId, request.getSection());
        } else {
            long existing = seatRepository.countByEventIdAndSection(eventId, request.getSection());
            if (existing > 0) {
                // nothing to do; keep it idempotent
                return 0;
            }
        }

        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= request.getRows(); row++) {
            for (int seatNum = 1; seatNum <= request.getSeatsPerRow(); seatNum++) {
                Seat s = new Seat();
                s.setEventId(eventId);
                s.setSection(request.getSection());
                s.setRowNumber(row);
                s.setSeatNumber(seatNum);
                s.setPrice(request.getPrice());
                seats.add(s);
            }
        }

        seatRepository.saveAll(seats);
        return seats.size();
    }

    // ---- mapper ----

    private SeatResponseDto toDto(Seat seat) {
        return new SeatResponseDto(
                seat.getId(),
                seat.getEventId(),
                seat.getSection(),
                seat.getRowNumber(),
                seat.getSeatNumber(),
                seat.getPrice()
        );
    }
}
