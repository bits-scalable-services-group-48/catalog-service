package com.ticketing.catalogservice.controller;

import com.ticketing.catalogservice.dto.SeatGenerationRequest;
import com.ticketing.catalogservice.dto.SeatRequestDto;
import com.ticketing.catalogservice.dto.SeatResponseDto;
import com.ticketing.catalogservice.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // ---- CRUD ----

    @PostMapping("/seats")
    public ResponseEntity<SeatResponseDto> createSeat(@RequestBody SeatRequestDto request) {
        return ResponseEntity.ok(seatService.createSeat(request));
    }

    @GetMapping("/seats/{id}")
    public ResponseEntity<SeatResponseDto> getSeat(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeat(id));
    }

    @GetMapping("/seats")
    public ResponseEntity<List<SeatResponseDto>> getSeatsByEvent(
            @RequestParam("eventId") Long eventId) {
        return ResponseEntity.ok(seatService.getSeatsByEvent(eventId));
    }

    @PutMapping("/seats/{id}")
    public ResponseEntity<SeatResponseDto> updateSeat(
            @PathVariable Long id,
            @RequestBody SeatRequestDto request) {
        return ResponseEntity.ok(seatService.updateSeat(id, request));
    }

    @DeleteMapping("/seats/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Bulk generation for event ----
    // POST /v1/events/{eventId}/seats/generate
    @PostMapping("/events/{eventId}/seats/generate")
    public ResponseEntity<Map<String, Object>> generateSeats(
            @PathVariable Long eventId,
            @RequestBody SeatGenerationRequest request) {

        int created = seatService.generateSeatsForEvent(eventId, request);
        return ResponseEntity.ok(
                Map.of(
                        "eventId", eventId,
                        "section", request.getSection(),
                        "createdSeats", created
                )
        );
    }
}
