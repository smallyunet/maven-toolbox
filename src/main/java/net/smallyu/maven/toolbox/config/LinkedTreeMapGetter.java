package net.smallyu.maven.toolbox.config;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author smallyu
 */
public class LinkedTreeMapGetter {

    public static int getIntFromDouble(Map map, String key) {
        int integer = -1;
        try {
            integer = (int) (double) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            return integer;
        }
    }

    public static long getLongFromDouble(Map map, String key) {
        long l = -1;
        try {
            l = (long) (double) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            return l;
        }
    }

    public static String getString(Map map, String key) {
        String string = "";
        try {
            string = (String) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            return string;
        }
    }

    public static LinkedTreeMap getLinkedTreeMap(Map map, String key) {
        LinkedTreeMap linkedTreeMap = new LinkedTreeMap();
        try {
            linkedTreeMap = (LinkedTreeMap) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            return linkedTreeMap;
        }
    }

    public static Boolean getBoolean(Map map, String key) {
        Boolean aBoolean = false;
        try {
            aBoolean = (Boolean) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            return aBoolean;
        }
    }

    public static ArrayList getArrayList(Map map, String key) {
        ArrayList<Object> objects = new ArrayList<>();
        try {
            objects = (ArrayList) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            return objects;
        }
    }

    public static Object getObject(Map map, String key) {
        Object object = new Object();
        try {
            object = map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            return object;
        }
    }
}
