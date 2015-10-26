package controller.request;

import java.io.*;
import java.net.HttpURLConnection;
//import java.net.InetSocketAddress;
//import java.net.Proxy;
import java.net.URL;

public class RequestImpl implements IRequest {
    @Override
    public String get(String request) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(request);
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("icc.csu.ru", 8080));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(/*proxy*/);
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
        String line;
        while((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        try {
            Thread.sleep(250L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
