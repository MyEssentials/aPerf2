package aperf.exceptions;

import myessentials.localization.api.LocalManager;
import net.minecraft.command.CommandException;

public class APerfCommandException extends CommandException {
    public APerfCommandException(String key, Object... args) {
        super(LocalManager.get(key, args).getUnformattedText());
    }

    public APerfCommandException(String key, Throwable cause, Object... args) {
        this(key, args);
        initCause(cause);
    }
}
