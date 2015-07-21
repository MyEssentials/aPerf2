package aperf.modules.spawnlimiter.util;

import aperf.api.spawnlimit.ISpawnLimit;
import aperf.exceptions.APerfCommandException;
import net.minecraft.command.ICommandSender;

import java.util.Map;
import java.util.WeakHashMap;

public class SpawnLimitCreation {
    public static Map<ICommandSender, SpawnLimitCreationState> creationStateMap = new WeakHashMap<ICommandSender, SpawnLimitCreationState>();

    public static SpawnLimitCreationState getCreationState(ICommandSender sender) {
        SpawnLimitCreationState state = creationStateMap.get(sender);
        if (state == null) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.notstarted"); // TODO: Is the name ok?!?
        }
        return state;
    }

    public static class SpawnLimitCreationState {
        private final ISpawnLimit limit;
        private final boolean newLimit;

        public SpawnLimitCreationState(ISpawnLimit limit, boolean newLimit) {
            this.limit = limit;
            this.newLimit = newLimit;
        }

        public boolean isNewLimit() {
            return newLimit;
        }

        public ISpawnLimit limit() {
            return limit;
        }
    }
}
