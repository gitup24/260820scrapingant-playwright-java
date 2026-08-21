package org.example.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RobotsValidator {
    public boolean isAllowedToScrape(String url) {
        try {
            URL baseURL = new URL(url);
            String robotsUrl = baseURL.getProtocol() + "://" + baseURL.getHost() + "/robots.txt";
            // Implement robots.txt parsing logic

            return checkRobotsPermission(robotsUrl, baseURL.getPath());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkRobotsPermission(String robotsUrl, String path) {
        try {
            URL url = new URL(robotsUrl);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            boolean userAgentFound = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (line.startsWith("user-agent:")) {
                    String agent = line.substring(11).trim();
                    if (agent.equals("*") || agent.equals("mybot")) {
                        userAgentFound = true;
                    }
                } else if (userAgentFound && line.startsWith("disallow:")) {
                    String disallowedPath = line.substring(9).trim();
                    if (disallowedPath.equals("/") || disallowedPath.equals("/*") || path.startsWith(disallowedPath)) {
                        reader.close();
                        return false;
                    }
                }
            }
            reader.close();
            return true;
        } catch (Exception e) {
            return true;
        }
    }
}
