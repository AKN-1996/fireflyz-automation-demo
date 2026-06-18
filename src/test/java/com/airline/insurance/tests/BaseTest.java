package com.airline.insurance.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import java.util.Arrays;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();

        // Pass the OS-level argument to start Chromium maximized
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(Arrays.asList("--start-maximized")));
    }

    @BeforeMethod
    public void createContextAndPage() {
        // Set viewport to null so it automatically adapts to the maximized browser window
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(null));

        page = context.newPage();
    }

    @AfterMethod
    public void closeContext() {
        if (context != null) {
            context.close();
        }
    }

    @AfterClass
    public void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}