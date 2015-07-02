package aperf.modules.entity;

import aperf.APerf;
import aperf.Constants;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.JsonSpawnLimitMarshaler;
import aperf.modules.entity.limiter.SpawnLimits;
import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.*;

/**
 */
public class Config {
    private static File entityModFolder;

    public static void load() {
        entityModFolder = new File(Constants.CONFIG_FOLDER, "/entity/");
        if (!entityModFolder.exists())
            entityModFolder.mkdirs();
    }

    public static void save() {
        saveSpawnLimits();
    }

    public static void loadSpawnLimits() {
        File spawnLimitsConfig = new File(entityModFolder, "spawnLimits.json");
        if (!spawnLimitsConfig.exists()) {
            try {
                spawnLimitsConfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JsonElement tree = getSpawnLimitTree(spawnLimitsConfig);
        if (tree == null || !tree.isJsonArray()) {
            APerf.LOG.info("Spawn limits config is not formatted properly!");
            return;
        }
        for (JsonElement element : tree.getAsJsonArray()) {
            if (!element.isJsonObject()) continue;
            JsonObject jsonObject = element.getAsJsonObject();
            SpawnLimits.Limits.add(JsonSpawnLimitMarshaler.unmarshal(jsonObject));
        }
    }

    private static void saveSpawnLimits() {
        File spawnLimitsConfig = new File(entityModFolder, "spawnLimits.json");
        try {
            FileWriter fileWriter = new FileWriter(spawnLimitsConfig);
            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            jsonWriter.setIndent("  ");
            Gson gson = new Gson();
            gson.toJson(makeSpawnLimitTree(), jsonWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonElement getSpawnLimitTree(File file) {
        JsonElement tree = null;
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            tree = new JsonParser().parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tree;
    }

    private static JsonArray makeSpawnLimitTree() {
        JsonArray spawnTree = new JsonArray();
        for (ISpawnLimit limit : SpawnLimits.Limits) {
            spawnTree.add(JsonSpawnLimitMarshaler.marshal(limit));
        }
        return spawnTree;
    }
}
