package debugtools.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class EnhancedDebugRenderer extends DebugRenderer {

    private final Map<String, SimpleDebugRenderer> renderers;
    private final List<SimpleDebugRenderer> activeRenderers;

    public EnhancedDebugRenderer(Minecraft minecraft) {
        super(minecraft);
        this.renderers = new HashMap<>();
        this.activeRenderers = new ArrayList<>();
        this.fillRenderers();
    }

    protected void fillRenderers() {
        this.renderers.put("pathFindingRenderer", this.pathfindingRenderer);
        this.renderers.put("waterDebugRenderer", this.waterDebugRenderer);
        this.renderers.put("heightMapRenderer", this.heightMapRenderer);
        this.renderers.put("collisionBoxRenderer", this.collisionBoxRenderer);
        this.renderers.put("neighborsUpdateRenderer", this.neighborsUpdateRenderer);
        this.renderers.put("structureRenderer", this.structureRenderer);
        this.renderers.put("lightDebugRenderer", this.lightDebugRenderer);
        this.renderers.put("worldGenAttemptRenderer", this.worldGenAttemptRenderer);
        this.renderers.put("solidFaceRenderer", this.solidFaceRenderer);
        this.renderers.put("chunkRenderer", this.chunkRenderer);
        this.renderers.put("brainDebugRenderer", this.brainDebugRenderer);
        this.renderers.put("villageSectionsDebugRenderer", this.villageSectionsDebugRenderer);
        this.renderers.put("beeDebugRenderer", this.beeDebugRenderer);
        this.renderers.put("raidDebugRenderer", this.raidDebugRenderer);
        this.renderers.put("goalSelectorRenderer", this.goalSelectorRenderer);
        this.renderers.put("gameEventListenerRenderer", this.gameEventListenerRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, double camX, double camY, double camZ) {
        super.render(poseStack, bufferSource, camX, camY, camZ);
        this.activeRenderers.forEach(renderer -> renderer.render(poseStack, bufferSource, camX, camY, camZ));
    }

    public Map<String, SimpleDebugRenderer> getRenderers() {
        return this.renderers;
    }

    public boolean toggleRenderer(String name) {
        SimpleDebugRenderer renderer = this.renderers.get(name);
        if (renderer == null) {
            return false;
        }
        if (this.activeRenderers.contains(renderer)) {
            this.activeRenderers.remove(renderer);
        } else {
            this.activeRenderers.add(renderer);
        }
        return true;
    }

    public void disableAllRenderers() {
        this.activeRenderers.clear();
    }
}
