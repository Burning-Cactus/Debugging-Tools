package debugtools.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import debugtools.client.EnhancedDebugRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.SharedSuggestionProvider;

import java.util.concurrent.CompletableFuture;

public class RendererNameArgument implements ArgumentType<String> {

    public static RendererNameArgument rendererName() {
        return new RendererNameArgument();
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        EnhancedDebugRenderer debugRenderer = (EnhancedDebugRenderer) Minecraft.getInstance().debugRenderer;
        return SharedSuggestionProvider.suggest(debugRenderer.getRenderers().keySet(), builder);
    }
}
