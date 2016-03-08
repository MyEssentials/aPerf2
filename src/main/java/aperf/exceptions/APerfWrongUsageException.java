package aperf.exceptions;

import myessentials.localization.api.LocalManager;
import net.minecraft.command.WrongUsageException;

public class APerfWrongUsageException extends WrongUsageException {
    public APerfWrongUsageException(String key, Object... args) {
        super(LocalManager.get(key, args).getUnformattedText());
    }
}
