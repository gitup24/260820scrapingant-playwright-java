import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    private final Playwright playwright;
    private final Browser browser;
    private final Page page;

    public WebScraper () {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(50));
        page =  browser.newContext().newPage();
    }
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
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

}
