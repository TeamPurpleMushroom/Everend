package net.purplemushroom.neverend.content.capability.player.data.serializers;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.purplemushroom.neverend.content.capability.player.data.PlayerLocationUtil;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class ResourceLocationSerializer implements IPropertySerializer<ResourceLocation> {
    public static final IPropertySerializer<ResourceLocation> INSTANCE = new ResourceLocationSerializer();

    @Override
    public ResourceLocation deserialize(@NotNull String string, @NotNull CompoundTag compoundTag) {
        String key = compoundTag.getString(string);
        try {
            return ResourceLocation.read(new StringReader(key));
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void serialize(@NotNull String string, ResourceLocation levelResourceKey, @NotNull CompoundTag compoundTag) {
        compoundTag.putString(string, levelResourceKey.toString());
    }
}
