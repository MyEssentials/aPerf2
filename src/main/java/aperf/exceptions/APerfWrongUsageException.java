package aperf.exceptions;

import aperf.APerf;
import net.minecraft.command.WrongUsageException;

public class APerfWrongUsageException extends WrongUsageException {
    public APerfWrongUsageException(String key, Object... args) {
        super(APerf.LOCAL.getLocalization(key, args));
    }
}
