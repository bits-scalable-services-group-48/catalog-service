package com.ticketing.catalogservice.dto;

import java.math.BigDecimal;

public class SeatRequestDto {

    private Long eventId;
    private String section;
    private Integer rowNumber;
    private Integer seatNumber;
    private BigDecimal price;

    public SeatRequestDto() {
    }

    public SeatRequestDto(Long eventId, String section, Integer rowNumber,
                          Integer seatNumber, BigDecimal price) {
        this.eventId = eventId;
        this.section = section;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
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
