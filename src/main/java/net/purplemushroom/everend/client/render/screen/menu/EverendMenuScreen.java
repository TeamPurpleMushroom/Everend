package net.purplemushroom.everend.client.render.screen.menu;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.registry.EEMusic;
import net.purplemushroom.everend.client.registry.EERenderTypes;
import net.purplemushroom.everend.client.registry.EEShaderRegistry;
import net.purplemushroom.everend.client.registry.EESoundRegistry;
import net.purplemushroom.everend.client.render.screen.EEButton;
import net.purplemushroom.everend.client.render.screen.menu.splash.ClickMeSplash;
import net.purplemushroom.everend.client.render.screen.menu.splash.EverendSplash;
import net.purplemushroom.everend.client.render.screen.menu.splash.SplashProvider;
import net.purplemushroom.everend.mixin.accessor.client.TitleScreenAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class EverendMenuScreen extends TitleScreen {
    private long time = 0;
    private long surpriseTime = -1;

    public static final ResourceLocation BEAUTIFUL_IMAGE = Everend.rl("textures/gui/blob.png");

    public EverendMenuScreen() {
        super(false, new EverendLogoRender());
    }

    //TODO: Forge requires 'Button' instance instead of using `AbstractButton` like a normal person, so changing this texture will be a challenge

    @Override
    protected void init() {
        if (splash == null) splash = SplashProvider.getRandomSplash();
        if (!(panorama instanceof EverendMenuBackground)) panorama = new EverendMenuBackground();
        assert this.minecraft != null;
        int i = this.font.width(COPYRIGHT_TEXT);
        int j = this.width - i - 2;
        int l = this.height / 4 + 48;
        this.createNormalMenuOptions(l, 24);

        Button modButton = this.addRenderableWidget(Button.builder(Component.translatable("fml.menu.mods"), (button) -> this.minecraft.setScreen(new ModListScreen(this))).pos(this.width / 2 - 100, l + 48).size(98, 20).build());
        ((TitleScreenAccessor) this).setModUpdateNotification(TitleScreenModUpdateIndicator.init(this, modButton));

        this.addRenderableWidget(new ImageButton(this.width / 2 - 124, l + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (button) -> this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager())), Component.translatable("narrator.button.language")));
        this.addRenderableWidget(EEButton.builder(Component.translatable("menu.options"), (button) -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options))).bounds(this.width / 2 - 100, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(EEButton.builder(Component.translatable("menu.quit"), (button) -> this.minecraft.stop()).bounds(this.width / 2 + 2, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(new ImageButton(this.width / 2 + 104, l + 72 + 12, 20, 20, 0, 0, 20, Button.ACCESSIBILITY_TEXTURE, 32, 64, (button) -> this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options)), Component.translatable("narrator.button.accessibility")));
        this.addRenderableWidget(new PlainTextButton(j, this.height - 10, i, 10, COPYRIGHT_TEXT, (button) -> this.minecraft.setScreen(new CreditsAndAttributionScreen(this)), this.font));
        this.minecraft.setConnectedToRealms(false);
        if (this.realmsNotificationsScreen == null) {
            this.realmsNotificationsScreen = new RealmsNotificationsScreen();
        }

        if (this.realmsNotificationsEnabled()) {
            this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
        }

        RenderSystem.setShader(EEShaderRegistry::getShaderMenu);
        ShaderInstance shaderinstance = RenderSystem.getShader();

        Uniform seedUniform = shaderinstance.getUniform("Seed");
        if (seedUniform != null) seedUniform.set(new Random().nextFloat(1024.0f));
    }

    private boolean renderSurprise() {
        return surpriseTime >= 0 && System.nanoTime() < surpriseTime + 2e9;
    }

    @Nullable
    @Override
    public Music getBackgroundMusic() {
        return renderSurprise() ? null : EEMusic.MENU;
    }

    @Override
    public void tick() {
        super.tick();
        time++;
    }

    protected EverendSplash getSplash() {
        return (EverendSplash) splash;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (renderSurprise()) {
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            pGuiGraphics.blit(BEAUTIFUL_IMAGE, 0, 0, 0, 0, 0, width, height, width, height);
            return;
        }

        RenderSystem.setShader(EEShaderRegistry::getShaderMenu);
        RenderSystem.setShaderGameTime(time, pPartialTick);
        ShaderInstance shaderinstance = RenderSystem.getShader();

        if (shaderinstance.GAME_TIME != null) {
            shaderinstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
        }

        shaderinstance.apply();
        pGuiGraphics.fill(EERenderTypes.getMenuRenderType(), 0, 0, width, height, 0);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (getSplash() instanceof ClickMeSplash) {
            float textX = (float) width / 2.0F + 123.0F;
            float textY = 69.0F;
            double newMouseX = pMouseX - textX;
            double newMouseY = pMouseY - textY;
            double relativeX = newMouseX * Math.cos(Math.toRadians(20)) - newMouseY * Math.sin(Math.toRadians(20));
            double relativeY = newMouseY * Math.cos(Math.toRadians(20)) + newMouseX * Math.sin(Math.toRadians(20));
            if (Math.abs(relativeX) <= (double) this.font.width(getSplash().getText()) * 1.8f / 2 && relativeY <= 0 && relativeY >= -this.font.lineHeight * 1.8f) {
                surpriseTime = System.nanoTime();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(EESoundRegistry.BLOB, 1.0F, 20.0f));
                splash = SplashProvider.getRandomSplash();
                return true;
            }
        }
        if (renderSurprise()) return false;
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private void createNormalMenuOptions(int pY, int pRowHeight) {
        assert this.minecraft != null;
        this.addRenderableWidget(EEButton.builder(Component.translatable("menu.singleplayer"), (button) -> this.minecraft.setScreen(new SelectWorldScreen(this))).bounds(this.width / 2 - 100, pY, 200, 20).build());
        Component component = this.getMultiplayerDisabledReason();
        boolean flag = component == null;
        Tooltip tooltip = component != null ? Tooltip.create(component) : null;
        this.addRenderableWidget(EEButton.builder(Component.translatable("menu.multiplayer"), (button) -> {
            Screen screen = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
            this.minecraft.setScreen(screen);
        }).bounds(this.width / 2 - 100, pY + pRowHeight, 200, 20).tooltip(tooltip).build()).active = flag;
        this.addRenderableWidget(EEButton.builder(Component.translatable("menu.online"), (button) -> this.realmsButtonClicked()).bounds(this.width / 2 + 2, pY + pRowHeight * 2, 98, 20).tooltip(tooltip).build()).active = flag;
    }
}

