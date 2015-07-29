package aperf.proxy;

import aperf.Constants;
import aperf.Config;
import myessentials.Localization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class LocalizationProxy {
    private static final Logger LOG = LogManager.getLogger("aPerf2.Localization");
    private static Localization localization;

    private LocalizationProxy() {
    }

    /**
     * Loads the {@link Localization}. First tries to load it from config/aPerf/Localization, then the classpath, then loads en_US in its place
     */
    public static void load() {
        try {
            InputStream is = null;

            File file = new File(Constants.CONFIG_FOLDER + "/localization/" + Config.General.Localization + ".lang");
            if (file.exists()) {
                is = new FileInputStream(file);
            }
            if (is == null) {
                is = LocalizationProxy.class.getResourceAsStream("/localization/" + Config.General.Localization + ".lang");
            }
            if (is == null) {
                is = LocalizationProxy.class.getResourceAsStream("/localization/en_US.lang");
                LOG.warn("Reverting to en_US.lang because {} does not exist!", Config.General.Localization + ".lang");
            }

            LocalizationProxy.localization = new Localization(new InputStreamReader(is));
            LocalizationProxy.localization.load();
        } catch (Exception ex) {
            LOG.warn("Failed to load localization!", ex);
        }
    }

    public static Localization getLocalization() {
        if(localization == null)
            load();
        return LocalizationProxy.localization;
    }
}
