package debugtools.client;

import debugtools.DebugMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DebugMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Minecraft.getInstance().debugRenderer = new EnhancedDebugRenderer(minecraft);
    }
}
