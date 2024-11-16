package net.purplemushroom.neverend.client.render.gui;

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
import net.purplemushroom.neverend.Neverend;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class NEButton extends AbstractButton {

    public static final ResourceLocation BUTTONS_NORMAL = Neverend.rl("textures/gui/widgets_normal.png");

    public static final int SMALL_WIDTH = 120;
    public static final int DEFAULT_WIDTH = 150;
    public static final int DEFAULT_HEIGHT = 20;
    protected static final NEButton.CreateNarration DEFAULT_NARRATION = Supplier::get;
    protected final NEButton.OnPress onPress;
    protected final NEButton.CreateNarration createNarration;

    //TODO: REMOVE THIS CLASS and mixin to `renderWidget` in AbstractButton class instead - where the texture can be set depending on config setting (???)

    public NEButton(int x, int y, int width, int height, Component title, NEButton.OnPress pressedAction, NEButton.CreateNarration pCreateNarration) {
        super(x, y, width, height, title);
        this.onPress = pressedAction;
        this.createNarration = pCreateNarration;
    }

    public NEButton(Builder builder) {
        this(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.createNarration);
        this.setTooltip(builder.tooltip);
    }

    public static NEButton.Builder builder(Component pMessage, NEButton.OnPress pOnPress) {
        return new NEButton.Builder(pMessage, pOnPress);
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
        private final NEButton.OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private NEButton.CreateNarration createNarration;

        public Builder(Component pMessage, NEButton.OnPress pOnPress) {
            this.createNarration = NEButton.DEFAULT_NARRATION;
            this.message = pMessage;
            this.onPress = pOnPress;
        }

        public NEButton.Builder pos(int pX, int pY) {
            this.x = pX;
            this.y = pY;
            return this;
        }

        public NEButton.Builder width(int pWidth) {
            this.width = pWidth;
            return this;
        }

        public NEButton.Builder size(int pWidth, int pHeight) {
            this.width = pWidth;
            this.height = pHeight;
            return this;
        }

        public NEButton.Builder bounds(int pX, int pY, int pWidth, int pHeight) {
            return this.pos(pX, pY).size(pWidth, pHeight);
        }

        public NEButton.Builder tooltip(@Nullable Tooltip pTooltip) {
            this.tooltip = pTooltip;
            return this;
        }

        public NEButton.Builder createNarration(NEButton.CreateNarration pCreateNarration) {
            this.createNarration = pCreateNarration;
            return this;
        }

        public NEButton build() {
            return this.build(builder -> new NEButton(builder));
        }

        public NEButton build(Function<NEButton.Builder, NEButton> builder) {
            return builder.apply(this);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(NEButton var1);
    }

    @OnlyIn(Dist.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> var1);
    }
}