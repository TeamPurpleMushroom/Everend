package net.purplemushroom.everend.util.shader;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.purplemushroom.everend.Everend;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ShaderManager {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    private static final HashMap<PostProcessingShader, PostChain> map = new HashMap<>();
    private static final HashSet<PostChain> preGUIShaders = new HashSet<>();
    private static final HashSet<PostChain> postGUIShaders = new HashSet<>();
    private static final HashSet<PostChain> everythingShaders = new HashSet<>();

    public static void addEffect(PostProcessingShader shader) {
        if (map.containsKey(shader)) return;

        try {
            PostChain effect = new PostChain(MINECRAFT.getTextureManager(), MINECRAFT.getResourceManager(), MINECRAFT.getMainRenderTarget(), shader.location);
            effect.resize(MINECRAFT.getWindow().getWidth(), MINECRAFT.getWindow().getHeight());

            switch (shader.layer) {
                case NO_GUI -> preGUIShaders.add(effect);
                case GUI -> postGUIShaders.add(effect);
                case EVERYTHING -> everythingShaders.add(effect);
            }

            map.put(shader, effect);
        } catch (IOException ioexception) {
            Everend.LOGGER.warn("Failed to load shader: {}", shader.location, ioexception);
        } catch (JsonSyntaxException jsonsyntaxexception) {
            Everend.LOGGER.warn("Failed to parse shader: {}", shader.location, jsonsyntaxexception);
        }
    }

    public static void removeEffect(PostProcessingShader shader) {
        PostChain chain = map.remove(shader);

        if (chain != null) {
            switch (shader.layer) {
                case NO_GUI -> preGUIShaders.remove(chain);
                case GUI -> postGUIShaders.remove(chain);
                case EVERYTHING -> everythingShaders.remove(chain);
            }

            chain.close();
        }
    }

    /*public static void setUniformFor(PostProcessingShader shader, String uniform, float value) {
        PostChain chain = map.get(shader);
        if (chain != null) {
            chain.setUniform(uniform, value);
        } else {
            Everend.LOGGER.warn("Could not set uniform " + uniform + " because shader " + shader.location + " was not active.");
        }
    }*/

    public static void removeClientWorldShaders() {
        for (PostProcessingShader shader : map.keySet()) {
            if (shader.removeOnWorldClose) removeEffect(shader);
        }
    }

    public static void resize(int width, int height) {
        for (PostChain effect : preGUIShaders) effect.resize(width, height);
        for (PostChain effect : postGUIShaders) effect.resize(width, height);
        for (PostChain effect : everythingShaders) effect.resize(width, height);
    }

    public static void closeShaders() {
        for (PostChain effect : preGUIShaders) effect.close();
        for (PostChain effect : postGUIShaders) effect.close();
        for (PostChain effect : everythingShaders) effect.close();

        map.clear();
        preGUIShaders.clear();
        postGUIShaders.clear();
        everythingShaders.clear();
    }

    public static void processPreGUI(float f) {
        for (PostChain effect : preGUIShaders) effect.process(f);
    }

    public static void processPostGUI(float f) {
        for (PostChain effect : postGUIShaders) effect.process(f);
    }

    public static void processEverything(float f) {
        for (PostChain effect : everythingShaders) effect.process(f);
    }
}
