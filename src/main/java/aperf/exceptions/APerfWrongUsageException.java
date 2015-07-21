package aperf.exceptions;

import aperf.proxy.LocalizationProxy;
import net.minecraft.command.WrongUsageException;

public class APerfWrongUsageException extends WrongUsageException {
    public APerfWrongUsageException(String key, Object... args) {
        super(LocalizationProxy.getLocalization().getLocalization(key, args));
    }
}
