package net.smallyu.maven.toolbox.http;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HTTPClientTest {

    Gson gson = new Gson();

    @Test
    void get() {
        String url = "http://localhost:20250/get";
        String res = HTTPClient.get(url);
        assertEquals(res, "{\"key\":\"value\"}");
    }

    @Test
    void post() {
        String url = "http://localhost:20250/post";

        LinkedTreeMap<String, String> arg = new LinkedTreeMap<>();
        arg.put("key", "value");
        String argJson = gson.toJson(arg);

        String res = HTTPClient.post(url, argJson);
        assertEquals(res, "true");
    }
}