package controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
    public String get(String request) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
