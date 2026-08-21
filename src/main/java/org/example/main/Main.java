package org.example.main;

import java.util.List;

public class Main {
    static void main() {
        //
        System.out.println("Starting scraper...");
        WebScraper scraper = new WebScraper();
        System.out.println("Scraper initialized, calling scrapeData...");
        try {
            List<String> data = scraper.scrapeData("https://google.com", "h1");
            System.out.println("Scraped data: " + data);



        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
        } finally {
            System.out.println("Closing scraper...");
            scraper.close();
            System.out.println("Done!");
        }

        //Scraper for dynamic page

        //Validation of robots
        RobotsValidator robotsValidator = new RobotsValidator();

        String url = "https://google.com/";

        if (robotsValidator.isAllowedToScrape(url)) {
            System.out.println("Scraping allowed for : " + url);

            // Scraper launched
            // webScraper.scrape(url);
            System.out.println("Starting dynamicScraper...");
            DynamicContentScraper dynamicScraper = new DynamicContentScraper();
            System.out.println("Dynamic Scraper initialized, calling scrapeData...");
            RetryHandler retryHandler = new RetryHandler();
            try {
                List<String> dataDynamic = retryHandler.withRetry(() -> dynamicScraper.scrapeDynamicContent(url, "h1"));
                System.out.println("Dynamic scraped data: " + dataDynamic);

                //save to JSON
                DataProcessor processor = new DataProcessor();
                processor.saveToJson(processor.cleanData(dataDynamic), "/home/olivier/IdeaProjects/260820scrapingant-playwright-java/src/main/java/org/example/main/scraped_data.json");
                System.out.println("JSON saved successfully!");

            } catch (Exception e) {
                System.err.println("Exception occurred: " + e.getMessage());
            } finally {
                System.out.println("Closing dynamicScraper...");
                dynamicScraper.close();
                System.out.println("Done!");
            }

        } else {
            System.out.println("Scraping interdit par robots.txt pour : " + url);
        }
        /*

        */


    }
}
