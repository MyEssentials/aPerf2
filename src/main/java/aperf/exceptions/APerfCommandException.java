package aperf.exceptions;

import aperf.proxy.LocalizationProxy;
import net.minecraft.command.CommandException;

public class APerfCommandException extends CommandException {
    public APerfCommandException(String key, Object... args) {
        super(LocalizationProxy.getLocalization().getLocalization(key, args));
    }

    public APerfCommandException(String key, Throwable cause, Object... args) {
        this(key, args);
        initCause(cause);
    }
}
