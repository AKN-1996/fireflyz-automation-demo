package com.airline.insurance.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class BookingPage {
    private final Page page;

    private final Locator departureInput;
    private final Locator arrivalInput;
    private final Locator cookieAcceptButton;

    public BookingPage(Page page) {
        this.page = page;
        this.cookieAcceptButton = page.locator("button#btn-accept-cookie, button:has-text('Accept All'), .cookie-close-icon");

        // The input fields are all we need now!
        this.departureInput = page.locator("[id='header-search-form-departure-station-input']");
        this.arrivalInput = page.locator("[id='header-search-form-arrival-station-input']");
    }

    /**
     * Simulates human typing and explicitly clicks the matching station code badge.
     */
    public void enterFromDestination(String stationCode) {
        departureInput.click();
        departureInput.clear(); // Always good practice to clear dynamic fields first

        // Simulates a human typing character-by-character to trigger the site's JS filter
        departureInput.pressSequentially(stationCode, new Locator.PressSequentiallyOptions().setDelay(150));

        // Look inside the departure dropdown specifically for the text (e.g., "KUL") and click it
        page.locator("[id='header-search-form-departure-station-list']")
                .getByText(stationCode, new Locator.GetByTextOptions().setExact(false))
                .first()
                .click();
    }

    /**
     * Simulates human typing and explicitly clicks the matching station code badge.
     */
    public void enterToDestination(String stationCode) {
        arrivalInput.click();
        arrivalInput.clear();

        // Simulates a human typing character-by-character
        arrivalInput.pressSequentially(stationCode, new Locator.PressSequentiallyOptions().setDelay(150));

        // Look inside the arrival dropdown specifically for the text (e.g., "PEN") and click it
        page.locator("[id='header-search-form-arrival-station-list']")
                .getByText(stationCode, new Locator.GetByTextOptions().setExact(false))
                .first()
                .click();
    }

    public void handlePopups() {
        try {
            cookieAcceptButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(2000));

            if (cookieAcceptButton.isVisible()) {
                cookieAcceptButton.click();
                System.out.println("Cookie banner/popup dismissed successfully.");
            }
        } catch (Exception e) {
            System.out.println("No cookie banner or popup appeared within timeout. Proceeding...");
        }
    }

    /**
     * Maps a 3-letter month abbreviation to its 0-indexed equivalent for the web calendar.
     */
    private String getZeroIndexedMonth(String monthAbbreviation) {
        return switch (monthAbbreviation.toUpperCase()) {
            case "JAN" -> "0";
            case "FEB" -> "1";
            case "MAR" -> "2";
            case "APR" -> "3";
            case "MAY" -> "4";
            case "JUN" -> "5";
            case "JUL" -> "6";
            case "AUG" -> "7";
            case "SEP" -> "8";
            case "OCT" -> "9";
            case "NOV" -> "10";
            case "DEC" -> "11";
            default -> throw new IllegalArgumentException("Invalid month abbreviation provided: " + monthAbbreviation);
        };
    }

    /**
     * Clicks the depart input field to open the calendar, then selects the exact date.
     * Expected format: "dd MMM yyyy" (e.g., "04 Jul 2026" or "18 Jun 2026")
     */
    public void selectDepartDate(String dateString) {
        // 1. Click the input to open the calendar UI (Update this locator to match your actual input field)
        page.locator("#header-search-form-departure-date").click();

        // 2. Select the specific date
        clickCalendarDate(dateString);
    }

    /**
     * Clicks the return input field to open the calendar, then selects the exact date.
     * Expected format: "dd MMM yyyy" (e.g., "08 Jul 2026")
     */
    public void selectReturnDate(String dateString) {
        // 1. Click the input to open the calendar UI (Update this locator to match your actual input field)
        //page.locator("#header-search-form-return-date").click();

        // 2. Select the specific date
        clickCalendarDate(dateString);
    }

    /**
     * Core logic to parse the date and click the precise DOM element.
     */
    private void clickCalendarDate(String dateString) {
        // Split "04 Jul 2026" into parts
        String[] dateParts = dateString.split(" ");
        String day = dateParts[0];             // "04"
        String monthAbbrev = dateParts[1];     // "Jul"
        String year = dateParts[2];            // "2026"

        // Translate the month
        String domMonthIndex = getZeroIndexedMonth(monthAbbrev);

        // Build the precise CSS selector using multiple attributes
        String dateLocator = String.format("div.calendar-day[data-day='%s'][data-month='%s'][data-year='%s']",
                day, domMonthIndex, year);

        // Wait for it to be visible in the DOM, then click it
        page.locator(dateLocator).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator(dateLocator).click();
    }
}