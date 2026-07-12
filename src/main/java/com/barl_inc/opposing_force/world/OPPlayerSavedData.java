package com.barl_inc.opposing_force.world;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OPPlayerSavedData extends SavedData {
    public static final Logger LOGGER = LogManager.getLogger();

    private final Map<UUID, PlayerData> playerData = new ConcurrentHashMap<>();
    public static final SavedData.Factory<OPPlayerSavedData> OP_PLAYER_SAVED_DATA_FACTORY = new SavedData.Factory<>(OPPlayerSavedData::new, OPPlayerSavedData::load);

    public static OPPlayerSavedData get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access OPPlayerSavedData client-side!");
        }

        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(
                OP_PLAYER_SAVED_DATA_FACTORY,
                "player_data_manager"
        );
    }


    public boolean contains(UUID player) {
        return playerData.containsKey(player);
    }

    public PlayerData getPlayerData(UUID player) {
        return playerData.computeIfAbsent(player, id -> new PlayerData());
    }

    public void setPlayerData(UUID player, PlayerData data) {
        playerData.put(player, data);
        setDirty();
    }

    public void clear() {
        playerData.clear();
        setDirty();
    }


    public OPPlayerSavedData() {}


    public static OPPlayerSavedData load(CompoundTag nbt, HolderLookup.Provider provider) {
        OPPlayerSavedData data = new OPPlayerSavedData();

        ListTag list = nbt.getList("playerDataList", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);

            UUID uuid = entry.getUUID("playerUUID");
            CompoundTag pdTag = entry.getCompound("playerData");

            DataResult<PlayerData> result =
                    PlayerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, pdTag));

            result.resultOrPartial(err ->
                    LOGGER.error("Failed to deserialize PlayerData for {}: {}", uuid, err)
            ).ifPresent(pd -> data.playerData.put(uuid, pd));
        }

        return data;
    }


    @Override
    public CompoundTag save(CompoundTag compound, HolderLookup.Provider provider) {
        ListTag playerList = new ListTag();

        for (Map.Entry<UUID, PlayerData> entry : playerData.entrySet()) {
            CompoundTag playerEntry = new CompoundTag();
            playerEntry.putUUID("playerUUID", entry.getKey());

            PlayerData.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue())
                    .get()
                    .ifLeft(tag -> playerEntry.put("playerData", tag))
                    .ifRight(error -> LOGGER.error(
                            "Failed to save PlayerData for {}: {}",
                            entry.getKey(), error
                    ));

            playerList.add(playerEntry);
        }

        compound.put("playerDataList", playerList);
        return compound;
    }

    public void markEndMessageSent(Player player) {
        PlayerData data = getPlayerData(player.getUUID());
        if (!data.hasGottenEndMessage()) {
            data.sendEndMessageAndMarkComplete(player);
            setDirty();
        }
    }


    public void sendNetherMessageAndMarkComplete(Player player) {
        PlayerData data = getPlayerData(player.getUUID());
        if (!data.hasGottenNetherMessage()) {
            data.sendNetherMessageAndMarkComplete(player);
            setDirty();
        }
    }

}