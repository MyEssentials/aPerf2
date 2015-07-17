package aperf.exceptions;

import net.minecraft.command.WrongUsageException;

public class APerfWrongUsageException extends WrongUsageException {
    public APerfWrongUsageException(String key, Object... args) {
        super(key, args);
    }
}
