package io.github.myessentials.aperf.core.limits;

import com.flowpowered.math.vector.Vector3i;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitRegistration;
import io.github.myessentials.aperf.api.spawnlimit.impl.CountLimit;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Chunk;

import java.util.Optional;

public class ChunkCount extends CountLimit {
    private int range = 0;

    @Override
    public String getName() {
        return "ChunkCount";
    }

    @Override
    public boolean canSpawn(Entity e) {
        int count = 0;
        Vector3i chunkPos = e.getLocation().getChunkPosition();

        for (int z = chunkPos.getZ() - range; z <= chunkPos.getZ()+range; z++) {
            for (int x = chunkPos.getX() - range; x <= chunkPos.getX()+range; x++) {
                Optional<Chunk> theChunk = e.getWorld().getChunk(x, chunkPos.getY(), z);
                if (theChunk.isPresent()) {
                    count += this.countEntities(theChunk.get());
                }
            }
        }

        return count < limit;
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        value.getNode("limit").setValue(limit);
        value.getNode("range").setValue(range);
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        limit = Optional.ofNullable(value.getNode("limit"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("Limit is required"))
                .getInt();

        range = Optional.ofNullable(value.getNode("range"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("Range is required"))
                .getInt();
    }

    public static class Registration implements SpawnLimitRegistration {
        @Override
        public String getId() {
            return "ChunkCount";
        }

        @Override
        public String getName() {
            return "ChunkCount";
        }

        @Override
        public String desc() {
            return "Limits by counting entities in a chunk and around it";
        }

        @Override
        public Class<? extends SpawnLimit> getSpawnLimitClass() {
            return ChunkCount.class;
        }
    }
}
