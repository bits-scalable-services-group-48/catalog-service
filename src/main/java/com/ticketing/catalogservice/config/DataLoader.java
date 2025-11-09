package com.ticketing.catalogservice.config;

import com.ticketing.catalogservice.entity.Event;
import com.ticketing.catalogservice.entity.EventStatus;
import com.ticketing.catalogservice.entity.Seat;
import com.ticketing.catalogservice.entity.Venue;
import com.ticketing.catalogservice.repository.EventRepository;
import com.ticketing.catalogservice.repository.SeatRepository;
import com.ticketing.catalogservice.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataLoader {

    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            if (venueRepository.count() > 0) {
                log.info("Data already loaded, skipping seed data import");
                return;
            }

            log.info("Starting to load seed data...");

            // Load venues first
            Map<Long, Venue> venueMap = loadVenues();
            log.info("Loaded {} venues", venueMap.size());

            // Load events
            Map<Long, Event> eventMap = loadEvents(venueMap);
            log.info("Loaded {} events", eventMap.size());

            // Load seats
            int seatsLoaded = loadSeats();
            log.info("Loaded {} seats", seatsLoaded);

            log.info("Seed data loading completed successfully!");
        };
    }

    private Map<Long, Venue> loadVenues() throws Exception {
        Map<Long, Venue> venueMap = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("seed-data/etsr_venues.csv").getInputStream()))) {
            
            String line = br.readLine(); // Skip header
            
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                Venue venue = Venue.builder()
                        .name(fields[1])
                        .city(fields[2])
                        .capacity(Integer.parseInt(fields[3]))
                        .build();
                
                Venue saved = venueRepository.save(venue);
                venueMap.put(Long.parseLong(fields[0]), saved);
            }
        }
        
        return venueMap;
    }

    private Map<Long, Event> loadEvents(Map<Long, Venue> venueMap) throws Exception {
        Map<Long, Event> eventMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("seed-data/etsr_events.csv").getInputStream()))) {
            
            String line = br.readLine(); // Skip header
            
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                Long venueId = Long.parseLong(fields[1]);
                Venue venue = venueMap.get(venueId);
                
                if (venue == null) {
                    log.warn("Venue {} not found for event {}", venueId, fields[0]);
                    continue;
                }
                
                Event event = Event.builder()
                        .venue(venue)
                        .title(fields[2])
                        .eventType(fields[3])
                        .eventDate(LocalDateTime.parse(fields[4], formatter))
                        .basePrice(new BigDecimal(fields[5]))
                        .status(EventStatus.valueOf(fields[6]))
                        .build();
                
                Event saved = eventRepository.save(event);
                eventMap.put(Long.parseLong(fields[0]), saved);
            }
        }
        
        return eventMap;
    }

    private int loadSeats() throws Exception {
        int count = 0;
        
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("seed-data/etsr_seats.csv").getInputStream()))) {
            
            String line = br.readLine(); // Skip header
            
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                Seat seat = new Seat();
                seat.setEventId(Long.parseLong(fields[1]));
                seat.setSection(fields[2]);
                seat.setRowNumber(Integer.parseInt(fields[3]));
                seat.setSeatNumber(Integer.parseInt(fields[4]));
                seat.setPrice(new BigDecimal(fields[5]));
                
                seatRepository.save(seat);
                count++;
            }
        }
        
        return count;
    }
}
