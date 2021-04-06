package net.smallyu.maven.toolbox.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author: smallyu
 **/
public class HTTPClient {

    public static String get(String url) {
        URL oracle = null;
        try {
            oracle = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection conn = null;
        try {
            conn = oracle.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String stringFromBufferedReader = getStringFromBufferedReader(in);
        return stringFromBufferedReader;
    }

    public static String post(String url, String arg) {
        URL oracle = null;
        try {
            oracle = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection conn = null;
        try {
            conn = oracle.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpURLConnection http = (HttpURLConnection) conn;
        try {
            http.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        http.setDoOutput(true);

        // send
        byte[] out = arg.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json");
        try {
            http.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // input
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // format
        String stringFromBufferedReader = getStringFromBufferedReader(in);
        return stringFromBufferedReader;
    }

    private static String getStringFromBufferedReader(BufferedReader in) {
        String result = "";
        String inputLine = null;
        while (true) {
            try {
                if ((inputLine = in.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            result += inputLine;
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
