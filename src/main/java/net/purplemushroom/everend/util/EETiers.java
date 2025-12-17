package net.purplemushroom.everend.util;

import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeTier;

public enum EETiers {
    DEVELOPER_SWORD(EETier.DEV, 1000, 0);

    private final Tier tier;
    private final int damage;
    private final float speedModifier;

    EETiers(Tier tier, int damage, float speed) {
        this.tier = tier;
        this.damage = damage;
        this.speedModifier = speed;
    }

    EETiers(Tier tier, int type) {
        int d = 1;          //hoe
        this.tier = tier;
        if(type == 1){      //sword
            d = 5;
        }
        if(type == 2){      //pickaxe
            d = 2;
        }
        if(type == 3){      //axe
            d = 7;
        }
        if(type == 4){      //shovel
            d = 3;
        }
        damage = d;
        this.speedModifier = 0;
    }

    public Tier getTier() {
        return tier;
    }

    public float getSpeedModifier() {
        return speedModifier;
    }

    public int getDamage() {
        return damage;
    }

    public static class EETier {
        public static final Tier DEV = new ForgeTier(3, 3000, 10F, 10000F, 25, null, () -> null);
    }
}
