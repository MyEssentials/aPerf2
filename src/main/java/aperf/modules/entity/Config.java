package aperf.modules.entity;

import aperf.Constants;

import java.io.*;

public class Config {
    private static File entityModFolder;

    public static void load() {
        entityModFolder = new File(Constants.CONFIG_FOLDER, "/entity/");
        if (!entityModFolder.exists())
            entityModFolder.mkdirs();
    }

    public static void save() {
    }
}
