package net.purplemushroom.everend.client.render.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.purplemushroom.everend.Everend;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class EEButton extends AbstractButton {

    public static final ResourceLocation BUTTONS_NORMAL = Everend.rl("textures/gui/widgets_normal.png");

    public static final int SMALL_WIDTH = 120;
    public static final int DEFAULT_WIDTH = 150;
    public static final int DEFAULT_HEIGHT = 20;
    protected static final EEButton.CreateNarration DEFAULT_NARRATION = Supplier::get;
    protected final EEButton.OnPress onPress;
    protected final EEButton.CreateNarration createNarration;

    //TODO: REMOVE THIS CLASS and mixin to `renderWidget` in AbstractButton class instead - where the texture can be set depending on config setting (???)

    public EEButton(int x, int y, int width, int height, Component title, EEButton.OnPress pressedAction, EEButton.CreateNarration pCreateNarration) {
        super(x, y, width, height, title);
        this.onPress = pressedAction;
        this.createNarration = pCreateNarration;
    }

    public EEButton(Builder builder) {
        this(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.createNarration);
        this.setTooltip(builder.tooltip);
    }

    public static EEButton.Builder builder(Component pMessage, EEButton.OnPress pOnPress) {
        return new EEButton.Builder(pMessage, pOnPress);
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.visible) {
            Minecraft minecraft = Minecraft.getInstance();
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            pGuiGraphics.blitNineSliced(BUTTONS_NORMAL, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = this.getFGColor();
            this.renderString(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }

        return 46 + i * 20;
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected @NotNull MutableComponent createNarrationMessage() {
        return this.createNarration.createNarrationMessage(super::createNarrationMessage);
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
        this.defaultButtonNarrationText(pNarrationElementOutput);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final Component message;
        private final EEButton.OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private EEButton.CreateNarration createNarration;

        public Builder(Component pMessage, EEButton.OnPress pOnPress) {
            this.createNarration = EEButton.DEFAULT_NARRATION;
            this.message = pMessage;
            this.onPress = pOnPress;
        }

        public EEButton.Builder pos(int pX, int pY) {
            this.x = pX;
            this.y = pY;
            return this;
        }

        public EEButton.Builder width(int pWidth) {
            this.width = pWidth;
            return this;
        }

        public EEButton.Builder size(int pWidth, int pHeight) {
            this.width = pWidth;
            this.height = pHeight;
            return this;
        }

        public EEButton.Builder bounds(int pX, int pY, int pWidth, int pHeight) {
            return this.pos(pX, pY).size(pWidth, pHeight);
        }

        public EEButton.Builder tooltip(@Nullable Tooltip pTooltip) {
            this.tooltip = pTooltip;
            return this;
        }

        public EEButton.Builder createNarration(EEButton.CreateNarration pCreateNarration) {
            this.createNarration = pCreateNarration;
            return this;
        }

        public EEButton build() {
            return this.build(builder -> new EEButton(builder));
        }

        public EEButton build(Function<EEButton.Builder, EEButton> builder) {
            return builder.apply(this);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(EEButton var1);
    }

    @OnlyIn(Dist.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> var1);
    }
}