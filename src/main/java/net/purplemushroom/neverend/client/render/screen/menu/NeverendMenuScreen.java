package net.purplemushroom.neverend.client.render.screen.menu;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import net.purplemushroom.neverend.client.registry.NERenderTypes;
import net.purplemushroom.neverend.client.registry.NEShaderRegistry;
import net.purplemushroom.neverend.client.render.screen.NEButton;
import net.purplemushroom.neverend.client.render.screen.menu.splash.NeverendSplash;
import net.purplemushroom.neverend.client.render.screen.menu.splash.SplashProvider;
import net.purplemushroom.neverend.mixin.client.accessor.TitleScreenAccessor;

import java.util.Random;

public class NeverendMenuScreen extends TitleScreen {
    private long time = 0;
    public NeverendMenuScreen() {
        super(false, new NeverendLogoRender());
    }

    //TODO: Forge requires 'Button' instance instead of using `AbstractButton` like a normal person, so changing this texture will be a challenge

    @Override
    protected void init() {
        if (splash == null) splash = SplashProvider.getRandomSplash();
        if (!(panorama instanceof NeverendMenuBackground)) panorama = new NeverendMenuBackground();
        assert this.minecraft != null;
        int i = this.font.width(COPYRIGHT_TEXT);
        int j = this.width - i - 2;
        int l = this.height / 4 + 48;
        this.createNormalMenuOptions(l, 24);

        Button modButton = this.addRenderableWidget(Button.builder(Component.translatable("fml.menu.mods"), (button) -> this.minecraft.setScreen(new ModListScreen(this))).pos(this.width / 2 - 100, l + 48).size(98, 20).build());
        ((TitleScreenAccessor) this).setModUpdateNotification(TitleScreenModUpdateIndicator.init(this, modButton));

        this.addRenderableWidget(new ImageButton(this.width / 2 - 124, l + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (button) -> this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager())), Component.translatable("narrator.button.language")));
        this.addRenderableWidget(NEButton.builder(Component.translatable("menu.options"), (button) -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options))).bounds(this.width / 2 - 100, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(NEButton.builder(Component.translatable("menu.quit"), (button) -> this.minecraft.stop()).bounds(this.width / 2 + 2, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(new ImageButton(this.width / 2 + 104, l + 72 + 12, 20, 20, 0, 0, 20, Button.ACCESSIBILITY_TEXTURE, 32, 64, (button) -> this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options)), Component.translatable("narrator.button.accessibility")));
        this.addRenderableWidget(new PlainTextButton(j, this.height - 10, i, 10, COPYRIGHT_TEXT, (button) -> this.minecraft.setScreen(new CreditsAndAttributionScreen(this)), this.font));
        this.minecraft.setConnectedToRealms(false);
        if (this.realmsNotificationsScreen == null) {
            this.realmsNotificationsScreen = new RealmsNotificationsScreen();
        }

        if (this.realmsNotificationsEnabled()) {
            this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
        }

        RenderSystem.setShader(NEShaderRegistry::getShaderMenu);
        ShaderInstance shaderinstance = RenderSystem.getShader();

        Uniform seedUniform = shaderinstance.getUniform("Seed");
        if (seedUniform != null) seedUniform.set(new Random().nextFloat(1024.0f));
    }

    @Override
    public void tick() {
        super.tick();
        time++;
    }

    protected NeverendSplash getSplash() {
        return (NeverendSplash) splash;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(NEShaderRegistry::getShaderMenu);
        RenderSystem.setShaderGameTime(time, pPartialTick);
        ShaderInstance shaderinstance = RenderSystem.getShader();

        if (shaderinstance.GAME_TIME != null) {
            shaderinstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
        }

        shaderinstance.apply();
        pGuiGraphics.fill(NERenderTypes.getMenuRenderType(), 0, 0, width, height, 0);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    private void createNormalMenuOptions(int pY, int pRowHeight) {
        assert this.minecraft != null;
        this.addRenderableWidget(NEButton.builder(Component.translatable("menu.singleplayer"), (button) -> this.minecraft.setScreen(new SelectWorldScreen(this))).bounds(this.width / 2 - 100, pY, 200, 20).build());
        Component component = this.getMultiplayerDisabledReason();
        boolean flag = component == null;
        Tooltip tooltip = component != null ? Tooltip.create(component) : null;
        this.addRenderableWidget(NEButton.builder(Component.translatable("menu.multiplayer"), (button) -> {
            Screen screen = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
            this.minecraft.setScreen(screen);
        }).bounds(this.width / 2 - 100, pY + pRowHeight, 200, 20).tooltip(tooltip).build()).active = flag;
        this.addRenderableWidget(NEButton.builder(Component.translatable("menu.online"), (button) -> this.realmsButtonClicked()).bounds(this.width / 2 + 2, pY + pRowHeight * 2, 98, 20).tooltip(tooltip).build()).active = flag;
    }
}

