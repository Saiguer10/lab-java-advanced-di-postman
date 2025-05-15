package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

/**
 * Service that calculates early bird discounts based on how far in advance
 * a booking is made relative to the event date.
 */
@Service
public class EarlyBirdDiscountService {

    /**
     * Calculates the discount percentage based on the number of days between
     * booking date and event date.
     *
     * @param eventDate the date of the event
     * @param bookingDate the date the booking is made
     * @return a DiscountResult containing discount information
     */
    public DiscountResult calculateDiscount(LocalDate eventDate, LocalDate bookingDate) {
        if (eventDate.isBefore(bookingDate)) {
            return new DiscountResult(0, "No discount available: Event date is before booking date");
        }

        long daysDifference = ChronoUnit.DAYS.between(bookingDate, eventDate);

        if (daysDifference >= 30) {
            return new DiscountResult(15, "15% early bird discount applied (30+ days in advance)");
        } else if (daysDifference >= 15) {
            return new DiscountResult(10, "10% early bird discount applied (15-29 days in advance)");
        } else if (daysDifference >= 7) {
            return new DiscountResult(5, "5% early bird discount applied (7-14 days in advance)");
        } else {
            return new DiscountResult(0, "No early bird discount available for bookings less than 7 days in advance");
        }
    }

    /**
     * Inner class to represent discount calculation results.
     */
    public static class DiscountResult {
        private final int discountPercentage;
        private final String message;

        public DiscountResult(int discountPercentage, String message) {
            this.discountPercentage = discountPercentage;
            this.message = message;
        }

        public int getDiscountPercentage() {
            return discountPercentage;
        }

        public String getMessage() {
            return message;
        }
    }
}
