package com.ticketing.catalogservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "etsr_seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "section", length = 50)
    private String section;

    // "row" is a keyword in some places, so use rowNumber in Java
    @Column(name = "row")
    private Integer rowNumber;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    public Seat() {
    }

    public Seat(Long id, Long eventId, String section, Integer rowNumber,
                Integer seatNumber, BigDecimal price) {
        this.id = id;
        this.eventId = eventId;
        this.section = section;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
