package com.ticketing.catalogservice.dto;

import java.math.BigDecimal;

public class SeatResponseDto {

    private Long id;
    private Long eventId;
    private String section;
    private Integer rowNumber;
    private Integer seatNumber;
    private BigDecimal price;

    public SeatResponseDto() {
    }

    public SeatResponseDto(Long id, Long eventId, String section,
                           Integer rowNumber, Integer seatNumber,
                           BigDecimal price) {
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
