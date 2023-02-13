package debugtools;

import debugtools.command.DebugRendererCommand;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DebugMod.MODID)
public class DebugMod {
    public static final String MODID = "debugtools";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public DebugMod() {
        MinecraftForge.EVENT_BUS.addListener(this::registerClientCommands);
        MinecraftForge.EVENT_BUS.addListener(this::registerServerCommands);

    }

    public void registerClientCommands(RegisterClientCommandsEvent event) {
        DebugRendererCommand.register(event.getDispatcher());
    }

    public void registerServerCommands(RegisterCommandsEvent event) {

    }
}
