package debugtools.network;

import debugtools.SpawnResult;
import debugtools.client.EnhancedDebugRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Debugging packet used to send mob spawn info to the client.
 * @param pos The blockpos the attempt was made at
 * @param entityType The entity that attempted to spawn
 */
public record MobSpawnInfoPacket(BlockPos pos, int entityType, SpawnResult result) implements DebugPacket {

    public static MobSpawnInfoPacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int type = buf.readInt();
        SpawnResult result = buf.readEnum(SpawnResult.class);
        return new MobSpawnInfoPacket(pos, type, result);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(entityType);
        buf.writeEnum(result);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (Minecraft.getInstance().debugRenderer instanceof EnhancedDebugRenderer renderer) {
                renderer.mobSpawnAttemptRenderer.addSpawnInfo(pos, entityType, result);
            }
        });
    }
}
