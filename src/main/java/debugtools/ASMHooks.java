package debugtools;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.Target;

import java.util.HashSet;

@SuppressWarnings("unused")
public class ASMHooks {
    public static void pathFindingPacket(Level level, Mob mob, Path path, float maxDistanceToWaypoint) {
        if (path != null && level instanceof ServerLevel serverLevel) {
            path.targetNodes = new HashSet<>();
            path.targetNodes.add(new Target(0, 0, 0));
            FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
            friendlyByteBuf.writeInt(mob.getId());
            friendlyByteBuf.writeFloat(maxDistanceToWaypoint);
            path.writeToStream(friendlyByteBuf);
//            sendPacketToAllPlayers(serverLevel, friendlyByteBuf, ClientboundCustomPayloadPacket.DEBUG_PATHFINDING_PACKET);

            // TODO
            Packet<?> packet = new ClientboundCustomPayloadPacket(ClientboundCustomPayloadPacket.DEBUG_PATHFINDING_PACKET, friendlyByteBuf);

            for(Player player : level.players()) {
                ((ServerPlayer)player).connection.send(packet);
            }
        }
    }

    public static void neighborsUpdatePacket() {

    }

    public static void structurePacket() {

    }
}
