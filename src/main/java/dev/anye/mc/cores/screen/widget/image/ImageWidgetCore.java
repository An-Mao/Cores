package dev.anye.mc.cores.screen.widget.image;

import dev.anye.mc.cores.screen.widget.DT_XYWHUV;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public abstract class ImageWidgetCore<T extends ImageWidgetCore<T>> extends RenderWidgetCore<T> {
    protected ResourceLocation texture;

    public ImageWidgetCore(int x, int y, int w, int h, Component pMessage) {
        super(x, y, w, h, pMessage);
    }
    public ImageWidgetCore(ResourceLocation texture,int x, int y, int w, int h, Component pMessage) {
        super(x, y, w, h, pMessage);
        setTexture(texture);
    }
    public ResourceLocation getTexture() {
        return texture;
    }
    public T setTexture(ResourceLocation texture) {
        this.texture = texture;
        return self();
    }

    public void drawImage(GuiGraphics guiGraphics, DT_XYWHUV xywhuv){
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,texture,xywhuv.getX(),xywhuv.getY(),xywhuv.getUOffset(),xywhuv.getVOffset(),xywhuv.getWidth(),xywhuv.getHeight(),xywhuv.getWidth(),xywhuv.getHeight());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.visible) {
            renderContent(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }
    }
    protected abstract void renderContent(GuiGraphics guiGraphics,int mouseX, int mouseY, float partialTick);
}
