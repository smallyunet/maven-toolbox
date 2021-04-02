package net.smallyu.maven.toolbox.config;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConfigGetterTest {

    @Test
    void getInstance() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        assertNotNull(instance);
    }

    @Test
    void getInstanceCustom() {
        ConfigGetter instance = ConfigGetter.getInstance(true, "test.json");
        assertNotNull(instance);
    }

    @Test
    void getConfig() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        String config = instance.getConfig();
        assertEquals(config, "{  \"key1\": \"value1\",  \"key2\": {    \"key2.1\": \"value2.1\",    \"key2.2\": {      \"key2.2.1\": \"value2.2.1\"    }  },  \"key3\": 1024,  \"key4\": [{    \"key4.1\": \"value4.1\"  }]}");
    }

    @Test
    void getConfigCustom1() {
        ConfigGetter instance = ConfigGetter.getInstance(true, "user-config.json");
        String config = instance.getConfig();
        assertEquals(config,"{  \"ukey1\": \"value1\",  \"ukey2\": {    \"key2.1\": \"value2.1\",    \"key2.2\": {      \"key2.2.1\": \"value2.2.1\"    }  },  \"ukey3\": 1024,  \"ukey4\": [{    \"key4.1\": \"value4.1\"  }]}");
    }

    @Test
    void getConfigCustom2() {
        LinkedTreeMap map = new LinkedTreeMap();
        map.put("ckey1", "value1");

        Gson gson = new Gson();
        String s = gson.toJson(map);

        ConfigGetter instance = ConfigGetter.getInstance(true, null, s);
        String config = instance.getConfig();

        assertEquals(config, "{\"ckey1\":\"value1\"}");
    }

    @Test
    void getConfigWithParameter1() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        Object config = instance.getConfig("key1");
        assertEquals(config, "value1");
    }

    @Test
    void getConfigWithParameter2() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        Object config = instance.getConfig("key2", "key2.1");
        assertEquals(config, "value2.1");
    }

    @Test
    void getConfigWithParameter3() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        Object config = instance.getConfig("key2", "key2.2", "key2.2.1");
        assertEquals(config, "value2.2.1");
    }

    @Test
    void getConfigWithParameter4() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        // Purposely use error type
        String config = instance.getConfig(String.class, "key4");
        assertEquals(config, "");
    }

    @Test
    void getConfigWithParameter5() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        ArrayList config = instance.getConfig(ArrayList.class,"key4");

        assertEquals(config, new ArrayList(){{
            LinkedTreeMap map = new LinkedTreeMap();
            map.put("key4.1", "value4.1");
            add(map);
        }});
    }

    @Test
    void getConfigWithParameter6() {
        ConfigGetter instance = ConfigGetter.getInstance(true);
        LinkedTreeMap config = instance.getConfig(LinkedTreeMap.class,"key2", "key2.2");
        String string = LinkedTreeMapGetter.getString(config, "key2.2.1");

        LinkedTreeMap map = new LinkedTreeMap();
        map.put("key2.2.1", "value2.2.1");
        assertEquals(config, map);
        assertEquals(string, "value2.2.1");
    }

}