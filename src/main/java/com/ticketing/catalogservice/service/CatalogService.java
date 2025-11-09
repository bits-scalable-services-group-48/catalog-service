package com.ticketing.catalogservice.service;

import com.ticketing.catalogservice.dto.*;
import com.ticketing.catalogservice.entity.*;
import com.ticketing.catalogservice.repository.EventRepository;
import com.ticketing.catalogservice.repository.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CatalogService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    // ---------- VENUES CRUD ----------

    public List<VenueDto> getAllVenues() {
        return venueRepository.findAll()
                .stream()
                .map(this::toVenueDto)
                .toList();
    }

    public VenueDto getVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venue not found: " + id));
        return toVenueDto(venue);
    }

    public VenueDto createVenue(VenueDto dto) {
        Venue venue = Venue.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .capacity(dto.getCapacity())
                .build();
        Venue saved = venueRepository.save(venue);
        return toVenueDto(saved);
    }

    public VenueDto updateVenue(Long id, VenueDto dto) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venue not found: " + id));

        venue.setName(dto.getName());
        venue.setCity(dto.getCity());
        venue.setCapacity(dto.getCapacity());

        return toVenueDto(venueRepository.save(venue));
    }

    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new EntityNotFoundException("Venue not found: " + id);
        }
        venueRepository.deleteById(id);
    }

    // ---------- EVENTS CRUD + SEARCH ----------

    public EventResponseDto getEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + id));
        return toEventResponseDto(event);
    }

    /**
     * /v1/events?city=&type=&status=
     */
    public List<EventResponseDto> searchEvents(String city,
                                               String type,
                                               EventStatus status) {

        List<Event> events;

        if (city != null && type != null && status != null) {
            events = eventRepository.findByVenue_CityAndEventTypeAndStatus(city, type, status);
        } else if (city != null && status != null) {
            events = eventRepository.findByVenue_CityAndStatus(city, status);
        } else if (type != null && status != null) {
            events = eventRepository.findByEventTypeAndStatus(type, status);
        } else if (status != null) {
            events = eventRepository.findByStatus(status);
        } else {
            events = eventRepository.findAll();
        }

        return events.stream()
                .map(this::toEventResponseDto)
                .toList();
    }

    public EventResponseDto createEvent(EventRequestDto dto) {
        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found: " + dto.getVenueId()));

        Event event = Event.builder()
                .venue(venue)
                .title(dto.getTitle())
                .eventType(dto.getEventType())
                .status(dto.getStatus())
                .eventDate(dto.getEventDate())
                .basePrice(dto.getBasePrice())
                .build();

        Event saved = eventRepository.save(event);
        return toEventResponseDto(saved);
    }

    public EventResponseDto updateEvent(Long id, EventRequestDto dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + id));

        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found: " + dto.getVenueId()));

        event.setVenue(venue);
        event.setTitle(dto.getTitle());
        event.setEventType(dto.getEventType());
        event.setStatus(dto.getStatus());
        event.setEventDate(dto.getEventDate());
        event.setBasePrice(dto.getBasePrice());

        return toEventResponseDto(eventRepository.save(event));
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Event not found: " + id);
        }
        eventRepository.deleteById(id);
    }

    // ---------- SEATS (canonical inventory) ----------

    // ---------- MAPPERS ----------

    private VenueDto toVenueDto(Venue v) {
        return VenueDto.builder()
                .id(v.getId())
                .name(v.getName())
                .city(v.getCity())
                .capacity(v.getCapacity())
                .build();
    }

    private EventResponseDto toEventResponseDto(Event e) {
        return EventResponseDto.builder()
                .id(e.getId())
                .title(e.getTitle())
                .eventType(e.getEventType())
                .status(e.getStatus())
                .eventDate(e.getEventDate())
                .basePrice(e.getBasePrice())
                .venueId(e.getVenue().getId())
                .venueName(e.getVenue().getName())
                .venueCity(e.getVenue().getCity())
                .build();
    }
}

