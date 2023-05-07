package debugtools.network;

import debugtools.DebugMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class DebugPacketRegistry {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DebugMod.MODID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    public static void register() {
        register(MobSpawnInfoPacket.class, MobSpawnInfoPacket::decode, 0);
    }

    private static <MSG extends DebugPacket> void register(Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder, int index) {
        INSTANCE.messageBuilder(packet, index).encoder(MSG::encode).decoder(decoder).consumerMainThread(MSG::handle).add();
    }

    public static <MSG> void sendToDimension(MSG message, ResourceKey<Level> dimension) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
    }
}
