package org.example.main;

import com.microsoft.playwright.Page;

import java.util.Collections;
import java.util.List;

public class DynamicContentScraper extends WebScraper {
    private static final int RATE_LIMIT_DELAY = 1000; // 1 second delay

    // Method to handle dynamic content loading
    public List<String> scrapeDynamicContent(String url, String selector) {
        try {
            page.navigate(url);
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(10000));
            // Implement rate limiting
            Thread.sleep(RATE_LIMIT_DELAY);
            return page.locator(selector).allTextContents();
        } catch (InterruptedException e) {
            System.err.println("Error waiting for dynamic content: " + e.getMessage());
            return Collections.emptyList();
        }

    }

}
