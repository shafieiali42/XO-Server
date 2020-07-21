package Util.Constants;

import Util.Config.ConfigLoader;
import java.io.IOException;
import java.util.Properties;

public class Constant {


    public static int tileWidth = 0;
    public static int tileHeight = 0;

    static {

        try {
            Properties logicProperties = ConfigLoader.getInstance().readProperties("src/main/resources/ConfigFiles/LogicConfigFiles/LogicConstants.properties");
            tileWidth = Integer.parseInt(logicProperties.getProperty("tileWidth"));
            tileHeight = Integer.parseInt(logicProperties.getProperty("tileHeight"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int boardWidth = 7 * tileWidth;
    public static int boardHeight = 7 * tileHeight;
    public static int widthOfMainFrame = 0;
    public static int heightOfMainFrame = 0;
    public static int defaultCloseOperation;
    public static boolean resizable;

    static {
        try {
            Properties properties = ConfigLoader.getInstance().readProperties("src/main/resources/ConfigFiles/GraphicConfigFiles/MyMainFrame/MainFrameConfigFiles.properties");
            widthOfMainFrame = Integer.parseInt(properties.getProperty("width"));
            heightOfMainFrame = Integer.parseInt(properties.getProperty("height"));
            defaultCloseOperation = Integer.parseInt(properties.getProperty("CloseOperation"));
            resizable = Boolean.parseBoolean(properties.getProperty("Resizable"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
