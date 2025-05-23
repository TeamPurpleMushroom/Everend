package net.purplemushroom.everend.content.entities;

import net.minecraft.world.entity.LivingEntity;
import net.purplemushroom.everend.util.EverendBossInfo;

public interface IEverendBoss<T extends LivingEntity> {
    EverendBossInfo<T> getBossInfo();
}
