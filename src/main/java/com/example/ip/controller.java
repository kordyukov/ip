package com.example.ip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class controller {

    @GetMapping
    public Optional<String> getIp(@RequestParam String host) throws IOException {

        URL url = new URL("http://www.geoplugin.net/xml.gp?ip=" + host);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        String req = null;
        return getRawResponse("http://www.geoplugin.net/xml.gp?ip=193.161.215.89","no response");
    }

    public Optional<String> getRawResponse(String url, String requestBody)
            throws  IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(10000);
        connection.setRequestMethod("GET");

        connection.setDoOutput(true);

        try(OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(requestBody);
        }

        if (connection.getResponseCode() != 200) {
            System.err.println("connection failed");
            return Optional.empty();
        }

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), Charset.forName("utf-8")))) {
            return Optional.of(reader.lines().collect(Collectors.joining("\n")));
        }
    }


}
