package com.unusualmodding.opposing_force.world;

import com.unusualmodding.opposing_force.OpposingForceConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OPWorldData extends SavedData {

    private static final String IDENTIFIER = "hostile_takeover_world_data";
    private Map<UUID, Double> skyvernSpawnChance = new HashMap<>();
    private final double SKYVERN_CHANCE_STEP = OpposingForceConfig.SKYVERN_SPAWN_CHANCE.get();

    private OPWorldData() {
        super();
    }

    public static OPWorldData get(Level world) {
        if (world instanceof ServerLevel) {
            ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);
            DimensionDataStorage storage = overworld.getDataStorage();
            OPWorldData data = storage.computeIfAbsent(OPWorldData::load, OPWorldData::new, IDENTIFIER);
            if (data != null) {
                data.setDirty();
            }
            return data;
        }
        return null;
    }

    public static OPWorldData load(CompoundTag nbt) {
        OPWorldData data = new OPWorldData();
        if (nbt.contains("SkyvernSpawnChance")) {
            ListTag listtag = nbt.getList("SkyvernSpawnChance", 10);
            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag innerTag = listtag.getCompound(i);
                data.skyvernSpawnChance.put(innerTag.getUUID("UUID"), innerTag.getDouble("Chance"));
            }
        }
        return data;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compound) {
        if (!this.skyvernSpawnChance.isEmpty()) {
            ListTag listTag = new ListTag();
            for (Map.Entry<UUID, Double> reputations : skyvernSpawnChance.entrySet()) {
                CompoundTag tag = new CompoundTag();
                tag.putUUID("UUID", reputations.getKey());
                tag.putDouble("Chance", reputations.getValue());
                listTag.add(tag);
            }
            compound.put("SkyvernSpawnChance", listTag);
        }
        return compound;
    }

    public double getSkyvernSpawnChance(UUID uuid) {
        if(skyvernSpawnChance.containsKey(uuid)) {
            return skyvernSpawnChance.get(uuid);
        } else {
            skyvernSpawnChance.put(uuid, SKYVERN_CHANCE_STEP);
            return SKYVERN_CHANCE_STEP;
        }
    }

    public void incrementSkyvernSpawnChance(UUID uuid) {
        double chance = getSkyvernSpawnChance(uuid);
        chance += SKYVERN_CHANCE_STEP;
        skyvernSpawnChance.put(uuid, chance);
    }

    public void decreaseSkyvernSpawnChance(UUID uuid) {
        double chance = getSkyvernSpawnChance(uuid);
        chance -= SKYVERN_CHANCE_STEP;
        skyvernSpawnChance.put(uuid, chance);
    }

    public void resetSkyvernSpawnChance(UUID uuid) {
        skyvernSpawnChance.put(uuid, SKYVERN_CHANCE_STEP);
    }
}
