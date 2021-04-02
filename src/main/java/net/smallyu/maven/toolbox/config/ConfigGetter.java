package net.smallyu.maven.toolbox.config;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import static net.smallyu.maven.toolbox.config.LinkedTreeMapGetter.*;


/**
 * @author smallyu
 */
public class ConfigGetter {

    private static String DEFAULT_CONFIG_FILENAME = "config.json";

    private static String configFileName = DEFAULT_CONFIG_FILENAME;
    private Gson gson = new Gson();
    private String config;

    private static ConfigGetter configGetter;

    /* Structure method */

    private ConfigGetter() {
        File configFile = this.getConfigFile();
        String configStr;
        if (configFile != null) {
            configStr = this.getContentFromFile(configFile);
        } else {
            configStr = gson.toJson(new LinkedTreeMap<>());
        }
        this.config = configStr;
    }

    private ConfigGetter(String defaultConfig) {
        this.config = defaultConfig;
    }

    public static ConfigGetter getInstance() {
        ConfigGetter.configFileName =DEFAULT_CONFIG_FILENAME;
        if (configGetter == null) {
            configGetter = new ConfigGetter();
        }
        return configGetter;
    }

    public static ConfigGetter getInstance(Boolean force) {
        ConfigGetter.configFileName = DEFAULT_CONFIG_FILENAME;
        if (force || configGetter == null) {
            configGetter = new ConfigGetter();
        }
        return configGetter;
    }

    public static ConfigGetter getInstance(String configFileName) {
        if (configFileName != null) {
            ConfigGetter.configFileName = configFileName;
        }
        if (configGetter == null) {
            configGetter = new ConfigGetter();
        }
        return configGetter;
    }

    public static ConfigGetter getInstance(Boolean force, String configFileName) {
        if (configFileName != null) {
            ConfigGetter.configFileName = configFileName;
        }
        if (force || configGetter == null) {
            configGetter = new ConfigGetter();
        }
        return configGetter;
    }

    public static ConfigGetter getInstance(String configFileName, String defaultConfig) {
        if (configFileName != null) {
            ConfigGetter.configFileName = configFileName;
        }
        if (configGetter == null) {
            if (defaultConfig != null) {
                configGetter = new ConfigGetter(defaultConfig);
            } else {
                configGetter = new ConfigGetter();
            }
        }
        return configGetter;
    }

    public static ConfigGetter getInstance(Boolean force, String configFileName, String defaultConfig) {
        if (configFileName != null) {
            ConfigGetter.configFileName = configFileName;
        }
        if (force || configGetter == null) {
            if (defaultConfig != null) {
                configGetter = new ConfigGetter(defaultConfig);
            } else {
                configGetter = new ConfigGetter();
            }
        }
        return configGetter;
    }

    /* Assist method */

    private File getConfigFile() {
        String outer = System.getProperty("user.dir");
        outer += File.separator + configFileName;

        // Outer config file
        File file = new File(outer);
        // Inner config file
        if (!file.exists()) {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(configFileName);
            // Both not found
            if (url == null) {
                return null;
            }
            String inner = url.getPath();
            file = new File(inner);
            if (!file.exists()) {
                return null;
            }
        }
        return file;
    }

    private String getContentFromFile(File file) {
        String configStr = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = "";
            while ((s = br.readLine()) != null) {
                configStr += s;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configStr;
    }

    /* Tool method */

    public String getConfig() {
        return config;
    }

    public Object getConfig(String... keys) {
        LinkedTreeMap linkedTreeMap = gson.fromJson(config, LinkedTreeMap.class);
        for (Integer i = 0; i < keys.length-1; i++) {
            linkedTreeMap = getLinkedTreeMap(linkedTreeMap, keys[i]);
        }
        Object object = getObject(linkedTreeMap, keys[keys.length - 1]);
        return object;
    }

    public <T> T getConfig(Class resultType, String... keys) {
        LinkedTreeMap linkedTreeMap = gson.fromJson(config, LinkedTreeMap.class);
        for (Integer i = 0; i < keys.length-1; i++) {
            linkedTreeMap = getLinkedTreeMap(linkedTreeMap, keys[i]);
        }
        T t = null;
        if (resultType == String.class) {
            String object = getString(linkedTreeMap, keys[keys.length - 1]);
            t = (T) object;
        }
        if (resultType == ArrayList.class) {
            ArrayList object = getArrayList(linkedTreeMap, keys[keys.length - 1]);
            t = (T) object;
        }
        if (resultType == Boolean.class) {
            Boolean object = getBoolean(linkedTreeMap, keys[keys.length - 1]);
            t = (T) object;
        }
        if (resultType == LinkedTreeMap.class) {
            LinkedTreeMap object = getLinkedTreeMap(linkedTreeMap, keys[keys.length - 1]);
            t = (T) object;
        }
        return t;
    }

}
