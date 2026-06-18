package com.airline.insurance.tests;

import com.airline.insurance.pages.BookingPage;
import org.testng.annotations.Test;

public class TravelInsuranceTests extends BaseTest {

    @Test
    public void enterTripDetailsFF_BookingPage() {
        // 1. Navigate to the portal (it will open maximized based on your BaseTest update)
        page.navigate("https://booking.fireflyz.com.my/Search.aspx");

        // 2. Instantiate the page object
        BookingPage bookingPage = new BookingPage(page);

        // 3. Clear cookie banners or interstitial popups immediately
        bookingPage.handlePopups();

        bookingPage.enterFromDestination("KUL");
        bookingPage.enterToDestination("PEN");
        bookingPage.selectDepartDate("04 Jul 2026");
        bookingPage.selectReturnDate("08 Jul 2026");
        page.locator("[id='header-search-form-calendar-done']").click();
        page.locator("div:nth-of-type(10) > button").click();
        // Unable to proceed from this point forward due to site setting
        page.waitForTimeout(5000);
    }



}