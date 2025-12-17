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

    EETiers(Tier tier, Type type) {
        int damageModifier = 1;
        this.tier = tier;
        if(type == type.getSword()){
            damageModifier = 5;
        }
        if(type == type.getPickaxe()){
            damageModifier = 2;
        }
        if(type == type.getAxe()){
            damageModifier = 7;
        }
        if(type == type.getShovel()){
            damageModifier = 3;
        }
        this.damage = damageModifier;
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

    public enum Type {
        SWORD(1),
        PICKAXE(2),
        SHOVEL(3),
        AXE(4),
        HOE(5);

        private final int typeID;

        Type(int typeID) {
            this.typeID = typeID;
        }

        public int getTypeID() {
            return typeID;
        }

        public Type getSword() {
            return SWORD;
        }

        public Type getPickaxe() {
            return PICKAXE;
        }

        public Type getShovel() {
            return SHOVEL;
        }

        public Type getAxe() {
            return AXE;
        }

        public Type getHoe() {
            return HOE;
        }
    }
}
