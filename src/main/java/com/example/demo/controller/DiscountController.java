package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EarlyBirdDiscountService;
import com.example.demo.service.EarlyBirdDiscountService.DiscountResult;

/**
 * REST controller for discount operations.
 * This controller is designed to work whether or not the EarlyBirdDiscountService
 * is available (enabled by configuration).
 */
@RestController
@RequestMapping("/api")
public class DiscountController {

    private final EarlyBirdDiscountService discountService;
    private final boolean serviceEnabled;

    /**
     * Constructor with optional dependency injection.
     * If the discount service is not available, the controller will still function
     * but will return appropriate messages indicating the feature is disabled.
     *
     * @param discountService the discount service (may be null if not enabled)
     */
    @Autowired(required = false)
    public DiscountController(EarlyBirdDiscountService discountService) {
        this.discountService = discountService;
        this.serviceEnabled = (discountService != null);
    }

    /**
     * Endpoint to calculate early bird discounts.
     *
     * @param eventDateStr the date of the event (format: yyyy-MM-dd)
     * @param bookingDateStr the date of booking (format: yyyy-MM-dd)
     * @return discount information or an error message
     */
    @GetMapping("/discount")
    public ResponseEntity<?> calculateDiscount(
            @RequestParam("eventDate") String eventDateStr,
            @RequestParam("bookingDate") String bookingDateStr) {

        // Servicio disponible
        if (!serviceEnabled) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ErrorResponse("Early bird discount feature is currently disabled"));
        }

        try {
            // Analisis de fecha en objetos LocalDate
            LocalDate eventDate = LocalDate.parse(eventDateStr, DateTimeFormatter.ISO_DATE);
            LocalDate bookingDate = LocalDate.parse(bookingDateStr, DateTimeFormatter.ISO_DATE);

            // Descuento calculado
            DiscountResult result = discountService.calculateDiscount(eventDate, bookingDate);

            // Devolvemos la respuesta
            return ResponseEntity.ok(new DiscountResponse(
                    result.getDiscountPercentage(),
                    result.getMessage(),
                    eventDate,
                    bookingDate
            ));

        } catch (DateTimeParseException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Invalid date format. Please use yyyy-MM-dd format."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred while calculating the discount: " + e.getMessage()));
        }
    }

    /**
     * Response class for discount calculations.
     */
    public static class DiscountResponse {
        private final int discountPercentage;
        private final String message;
        private final LocalDate eventDate;
        private final LocalDate bookingDate;

        public DiscountResponse(int discountPercentage, String message, LocalDate eventDate, LocalDate bookingDate) {
            this.discountPercentage = discountPercentage;
            this.message = message;
            this.eventDate = eventDate;
            this.bookingDate = bookingDate;
        }

        public int getDiscountPercentage() {
            return discountPercentage;
        }

        public String getMessage() {
            return message;
        }

        public LocalDate getEventDate() {
            return eventDate;
        }

        public LocalDate getBookingDate() {
            return bookingDate;
        }
    }

    /**
     * Error response class.
     */
    public static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
