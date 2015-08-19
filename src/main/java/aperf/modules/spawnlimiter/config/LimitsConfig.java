package aperf.modules.spawnlimiter.config;

import aperf.APerf;
import aperf.Constants;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.JsonSpawnLimitMarshaler;
import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LimitsConfig {
    public static final List<ISpawnLimit> Limits = new ArrayList<ISpawnLimit>();
    private static File spawnLimitModuleFolder;

    public static void load() {
        // Load spawn limits
        File spawnLimitsConfig = new File(getFolder(), "spawnLimits.json");
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
            Limits.add(JsonSpawnLimitMarshaler.unmarshal(jsonObject));
        }
    }

    public static void save() {
        File spawnLimitsConfig = new File(getFolder(), "spawnLimits.json");
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
        for (ISpawnLimit limit : Limits) {
            spawnTree.add(JsonSpawnLimitMarshaler.marshal(limit));
        }
        return spawnTree;
    }

    private static File getFolder() {
        if (spawnLimitModuleFolder == null) {
            spawnLimitModuleFolder = new File(Constants.CONFIG_FOLDER, "/spawnLimits/");
            if (!spawnLimitModuleFolder.exists())
                spawnLimitModuleFolder.mkdirs();
        }

        return spawnLimitModuleFolder;
    }
}
