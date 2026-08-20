package org.example.main;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    // Initialize Playwright with proper configurations for scraping
    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    protected final Page page;

    public WebScraper () {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(50));
        context = browser.newContext();
        page = context.newPage();
    }
    // Method to extract data from a webpage
    public List<String> scrapeData(String url, String selector) {
        List<String> results = new ArrayList<>();
        try {
            page.navigate(url);
            page.waitForLoadState(LoadState.NETWORKIDLE);
            ElementHandle[] elements = page.querySelectorAll(selector).toArray(new ElementHandle[0]);
            for (ElementHandle element : elements) {
                results.add(element.textContent());
            }
        } catch (PlaywrightException e) {
            System.err.println("Error scraping data: " + e.getMessage());
        }
        return results;
    }

    public void close() {
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}
