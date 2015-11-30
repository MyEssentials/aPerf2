package aperf.exceptions;

import aperf.APerf;
import net.minecraft.command.CommandException;

public class APerfCommandException extends CommandException {
    public APerfCommandException(String key, Object... args) {
        super(APerf.LOCAL.getLocalization(key, args));
    }

    public APerfCommandException(String key, Throwable cause, Object... args) {
        this(key, args);
        initCause(cause);
    }
}
