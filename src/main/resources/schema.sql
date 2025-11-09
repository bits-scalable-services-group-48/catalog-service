-- Catalog Service Database Schema
-- Tables: venues, events, seats (read-only copy for catalog)

CREATE TABLE IF NOT EXISTS etsr_venues (
    venue_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    capacity INTEGER
);

CREATE TABLE IF NOT EXISTS etsr_events (
    event_id BIGSERIAL PRIMARY KEY,
    venue_id BIGINT NOT NULL REFERENCES etsr_venues(venue_id),
    title VARCHAR(255) NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    base_price DECIMAL(10,2),
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED'
);

CREATE TABLE IF NOT EXISTS etsr_seats (
    seat_id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL,
    section VARCHAR(50),
    row INTEGER,
    seat_number INTEGER,
    price DECIMAL(10,2),
    CONSTRAINT fk_seat_event FOREIGN KEY (event_id) REFERENCES etsr_events(event_id)
);

CREATE INDEX IF NOT EXISTS idx_events_venue ON etsr_events(venue_id);
CREATE INDEX IF NOT EXISTS idx_events_status ON etsr_events(status);
CREATE INDEX IF NOT EXISTS idx_events_date ON etsr_events(event_date);
CREATE INDEX IF NOT EXISTS idx_seats_event ON etsr_seats(event_id);
