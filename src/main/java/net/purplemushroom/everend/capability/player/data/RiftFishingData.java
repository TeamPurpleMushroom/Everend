package net.purplemushroom.everend.capability.player.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.purplemushroom.everend.capability.player.data.serializers.ResourceLocationSerializer;
import net.purplemushroom.everend.capability.player.data.serializers.UUIDSerializer;
import net.purplemushroom.everend.content.entities.FishingRift;
import net.purplemushroom.everend.packet.SRiftFishingPacket;
import net.purplemushroom.everend.registry.EEPackets;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

import java.util.UUID;

public class RiftFishingData extends PropertyContainer {
    private final CoffeeProperty<UUID> fishingRift = prop("fishing_rift", (UUID) null, UUIDSerializer.INSTANCE);
    private final CoffeeProperty<Float> fishingProgress = nullableProp("rift_fishing_progress", (Float) null);

    // client-sided stuff
    private int clientRiftID = -1;
    private boolean needsUpdate = true;

    public boolean isActive() {
        return fishingProgress.get() != null;
    }

    public FishingRift getFishingRift(Level level) {
        Entity entity;
        if (level instanceof ServerLevel serverLevel) {
            entity = serverLevel.getEntity(fishingRift.get());
        } else {
            if (clientRiftID == -1) return null;
            entity = level.getEntity(clientRiftID);
        }
        if (entity instanceof FishingRift rift) return rift;
        return null;
    }

    public void startFishingFromRift(FishingRift fishingRift) {
        this.fishingRift.set(fishingRift.getUUID());
        this.fishingProgress.set(0.0f);
        needsUpdate = true;
    }

    public void stopFishingFromRift() {
        fishingRift.set(null);
        fishingProgress.set(null);
        needsUpdate = true;
    }

    public boolean progressFishing(float amount) {
        float progress = (fishingProgress.get() != null ? fishingProgress.get() : 0.0f) + amount;
        if (progress >= 1.0f) {
            stopFishingFromRift();
            return true;
        }
        fishingProgress.set(progress);
        needsUpdate = true;
        return false;
    }

    public float getProgress() {
        Float progress = fishingProgress.get();
        if (progress == null) return 0.0f;
        return fishingProgress.get();
    }

    public void sendChangesToClient(ServerPlayer player) {
        if (needsUpdate) {
            FishingRift rift = getFishingRift(player.level());
            EEPackets.sendToPlayer(player, new SRiftFishingPacket(rift, fishingProgress.get()));
            needsUpdate = false;
        }
    }

    public void recieveChanges(int rift, float progress) {
        this.clientRiftID = rift;
        this.fishingProgress.set(progress >= 0.0f ? progress : null);
    }
}
