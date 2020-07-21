package Util.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigLoader {

    private static ConfigLoader configLoader = new ConfigLoader();

    public static ConfigLoader getInstance() {
        return configLoader;
    }

    private ConfigLoader() {

    }

    public Properties readProperties(String path) throws IOException {
        Properties prop = new Properties();
        FileInputStream inp = new FileInputStream(path);
        prop.load(inp);
        return (prop);
    }


}
