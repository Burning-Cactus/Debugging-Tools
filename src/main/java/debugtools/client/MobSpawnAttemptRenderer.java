package debugtools.client;

import com.mojang.blaze3d.vertex.PoseStack;
import debugtools.SpawnResult;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

import java.util.HashSet;
import java.util.Set;

public class MobSpawnAttemptRenderer implements DebugRenderer.SimpleDebugRenderer {
    private final Set<SpawnInfo> spawnAttempts = new HashSet<>();

    public void addSpawnInfo(BlockPos pos, int entityId, SpawnResult result) {
        this.spawnAttempts.add(new SpawnInfo(pos, BuiltInRegistries.ENTITY_TYPE.byId(entityId), result));
    }

    @Override
    public void clear() {
        this.spawnAttempts.clear();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, double camX, double camY, double camZ) {
        for (SpawnInfo spawnInfo : spawnAttempts) {
            BlockPos pos = spawnInfo.pos;
            Component mob = spawnInfo.mob.getDescription();
            DebugRenderer.renderFloatingText(poseStack, buffer, mob.getString(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, -10, 0.02F);
        }
    }

    /**
     * This class holds the data from the mob spawn.
     */
    static class SpawnInfo {
        public final BlockPos pos;
        public final EntityType<?> mob;
        public final SpawnResult result;
        public final long timeStamp;

        public SpawnInfo(BlockPos pos, EntityType<?> mob, SpawnResult result) {
            this.pos = pos;
            this.mob = mob;
            this.result = result;
            this.timeStamp = Util.getMillis();
        }
    }
}
