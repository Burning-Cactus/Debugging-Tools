package debugtools.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import debugtools.client.EnhancedDebugRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;

public class DebugRendererCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(buildDebugCommand("debug"));
        dispatcher.register(buildDebugCommand("debugtools"));
        dispatcher.register(buildDebugCommand("dt"));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> buildDebugCommand(String prefix) {
        return Commands.literal(prefix).then(Commands.literal("renderer")
                .then(Commands.literal("toggle")
                        .then(Commands.argument("renderer_type", RendererNameArgument.rendererName())
                                .executes(
                                        context -> executeOnDebugRenderer(context, DebugRendererCommand::toggleRenderer)
                                )
                        )
                )
                .then(Commands.literal("clear").executes(context -> executeOnDebugRenderer(context, DebugRendererCommand::disableAllRenderers))));
    }

    private static void toggleRenderer(CommandContext<CommandSourceStack> context, EnhancedDebugRenderer debugRenderer) {
        CommandSourceStack source = context.getSource();
        String rendererType = StringArgumentType.getString(context, "renderer_type");
        boolean success = debugRenderer.toggleRenderer(rendererType);
        if (success) {
            source.sendSuccess(Component.literal(String.format("Toggled %s debug renderer.", rendererType)), false);
        } else {
            source.sendFailure(Component.literal("That debug renderer doesn't exist."));
        }
    }

    private static void disableAllRenderers(CommandContext<CommandSourceStack> context, EnhancedDebugRenderer debugRenderer) {

        debugRenderer.disableAllRenderers();
        context.getSource().sendSuccess(Component.literal("Disabled all debug renderers."), false);
    }

    private static int executeOnDebugRenderer(CommandContext<CommandSourceStack> context, BiConsumer<CommandContext<CommandSourceStack>, EnhancedDebugRenderer> consumer) {
        DebugRenderer renderer = Minecraft.getInstance().debugRenderer;
        if (renderer instanceof EnhancedDebugRenderer debugRenderer) {
            consumer.accept(context, debugRenderer);
        } else {
            context.getSource().sendFailure(Component.literal("ERROR! The enhanced debug renderer doesn't exist."));
        }
        return 0;
    }
}
