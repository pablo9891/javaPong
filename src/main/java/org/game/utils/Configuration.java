package org.game.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private Properties properties;
    private static Configuration instance = null;

    private Configuration() {
        properties = new Properties();

        try(InputStream fis = getClass().getClassLoader().getResourceAsStream(Constants.CONFIG_FILE);) {
                properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        if(instance == null)
            instance = new Configuration();
        return instance;
    }

    public String getStringProperty(String key) {
        String result = null;
        if(key !=null && !key.trim().isEmpty()){
            result = this.properties.getProperty(key);
        }
        return result;
    }

    public int getIntegerProperty(String key) {
        return Integer.parseInt(this.getStringProperty(key));
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(this.getStringProperty(key));
    }

    public double getDoubleProperty(String key) {
        return Double.parseDouble(this.getStringProperty(key));
    }
}
