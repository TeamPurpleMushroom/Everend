package net.purplemushroom.neverend.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import ru.timeconqueror.timecore.api.common.block.TimeSoundType;

public class NESoundTypes {
    public static final SoundType DRAGON_BONE = new TimeSoundType(
            1.0F,
            0.5F,
            () -> SoundEvents.BONE_BLOCK_BREAK,
            () -> SoundEvents.BONE_BLOCK_STEP,
            () -> SoundEvents.BONE_BLOCK_PLACE,
            () -> SoundEvents.BONE_BLOCK_HIT,
            () -> SoundEvents.BONE_BLOCK_FALL);
}
