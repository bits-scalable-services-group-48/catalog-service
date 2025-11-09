package com.ticketing.catalogservice.dto;

import java.math.BigDecimal;

public class SeatGenerationRequest {

    /**
     * Number of rows to create (e.g., 10)
     */
    private int rows;

    /**
     * Seats per row (e.g., 20)
     */
    private int seatsPerRow;

    /**
     * Section name (e.g., "A", "VIP").
     * If you want multiple sections, just call the API multiple times.
     */
    private String section;

    /**
     * Price for these seats.
     */
    private BigDecimal price;

    /**
     * If true and seats already exist for this event & section,
     * they will be deleted and recreated.
     */
    private boolean replaceIfExists = false;

    public SeatGenerationRequest() {
    }

    public SeatGenerationRequest(int rows, int seatsPerRow, String section,
                                 BigDecimal price, boolean replaceIfExists) {
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.section = section;
        this.price = price;
        this.replaceIfExists = replaceIfExists;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isReplaceIfExists() {
        return replaceIfExists;
    }

    public void setReplaceIfExists(boolean replaceIfExists) {
        this.replaceIfExists = replaceIfExists;
    }
}
