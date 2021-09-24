package org.twobee.whatever.networking;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketDisplayObjective;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketScoreboardObjective;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateScore;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.network.play.server.SPacketWorldBorder;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public class TimeTrackingNetHandlerPlayClient extends NetHandlerPlayClient {

    private final NetHandlerPlayClient decoratedPlayClient;

    private long lastPacketMillis = -1;

    public TimeTrackingNetHandlerPlayClient(
        NetHandlerPlayClient decoratedPlayClient
    ) {
        super(null, null, null, null);

        this.decoratedPlayClient = decoratedPlayClient;
    }

    public long getLastPacketMillis() {
        return lastPacketMillis;
    }

    @Override
    public void sendPacket(
        Packet<?> packetOut
    ) {
        decoratedPlayClient.sendPacket(packetOut);
    }

    @Override
    public void handleAdvancementInfo(
        SPacketAdvancementInfo packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleAdvancementInfo(packetIn);
    }

    @Override
    public void handleAnimation(
        SPacketAnimation packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleAnimation(packetIn);
    }

    @Override
    public void handleBlockAction(
        SPacketBlockAction packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleBlockAction(packetIn);
    }

    @Override
    public void handleBlockBreakAnim(
        SPacketBlockBreakAnim packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleBlockBreakAnim(packetIn);
    }

    @Override
    public void handleBlockChange(
        SPacketBlockChange packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleBlockChange(packetIn);
    }

    @Override
    public void handleCamera(
        SPacketCamera packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleCamera(packetIn);
    }

    @Override
    public void handleChangeGameState(
        SPacketChangeGameState packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleChangeGameState(packetIn);
    }

    @Override
    public void handleChat(
        SPacketChat packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleChat(packetIn);
    }

    @Override
    public void handleChunkData(
        SPacketChunkData packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleChunkData(packetIn);
    }

    @Override
    public void handleCloseWindow(
        SPacketCloseWindow packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleCloseWindow(packetIn);
    }

    @Override
    public void handleCollectItem(
        SPacketCollectItem packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleCollectItem(packetIn);
    }

    @Override
    public void handleCombatEvent(
        SPacketCombatEvent packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleCombatEvent(packetIn);
    }

    @Override
    public void handleConfirmTransaction(
        SPacketConfirmTransaction packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleConfirmTransaction(packetIn);
    }

    @Override
    public void handleCooldown(
        SPacketCooldown packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleCooldown(packetIn);
    }

    @Override
    public void handleCustomPayload(
        SPacketCustomPayload packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleCustomPayload(packetIn);
    }

    @Override
    public void handleCustomSound(
        SPacketCustomSound packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleCustomSound(packetIn);
    }

    @Override
    public void handleDestroyEntities(
        SPacketDestroyEntities packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleDestroyEntities(packetIn);
    }

    @Override
    public void handleDisconnect(
        SPacketDisconnect packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleDisconnect(packetIn);
    }

    @Override
    public void handleDisplayObjective(
        SPacketDisplayObjective packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleDisplayObjective(packetIn);
    }

    @Override
    public void handleEffect(
        SPacketEffect packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEffect(packetIn);
    }

    @Override
    public void handleEntityAttach(
        SPacketEntityAttach packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityAttach(packetIn);
    }

    @Override
    public void handleEntityEffect(
        SPacketEntityEffect packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityEffect(packetIn);
    }

    @Override
    public void handleEntityEquipment(
        SPacketEntityEquipment packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityEquipment(packetIn);
    }

    @Override
    public void handleEntityHeadLook(
        SPacketEntityHeadLook packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityHeadLook(packetIn);
    }

    @Override
    public void handleEntityMetadata(
        SPacketEntityMetadata packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityMetadata(packetIn);
    }

    @Override
    public void handleEntityMovement(
        SPacketEntity packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityMovement(packetIn);
    }

    @Override
    public void handleEntityProperties(
        SPacketEntityProperties packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityProperties(packetIn);
    }

    @Override
    public void handleEntityStatus(
        SPacketEntityStatus packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityStatus(packetIn);
    }

    @Override
    public void handleEntityTeleport(
        SPacketEntityTeleport packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityTeleport(packetIn);
    }

    @Override
    public void handleEntityVelocity(
        SPacketEntityVelocity packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleEntityVelocity(packetIn);
    }

    @Override
    public void handleExplosion(
        SPacketExplosion packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleExplosion(packetIn);
    }

    @Override
    public void handleHeldItemChange(
        SPacketHeldItemChange packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleHeldItemChange(packetIn);
    }

    @Override
    public void handleJoinGame(
        SPacketJoinGame packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleJoinGame(packetIn);
    }

    @Override
    public void handleKeepAlive(
        SPacketKeepAlive packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleKeepAlive(packetIn);
    }

    @Override
    public void handleMaps(
        SPacketMaps packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleMaps(packetIn);
    }

    @Override
    public void handleMoveVehicle(
        SPacketMoveVehicle packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleMoveVehicle(packetIn);
    }

    @Override
    public void handleMultiBlockChange(
        SPacketMultiBlockChange packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleMultiBlockChange(packetIn);
    }

    @Override
    public void handleOpenWindow(
        SPacketOpenWindow packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleOpenWindow(packetIn);
    }

    @Override
    public void handleParticles(
        SPacketParticles packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleParticles(packetIn);
    }

    @Override
    public void handlePlayerAbilities(
        SPacketPlayerAbilities packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handlePlayerAbilities(packetIn);
    }

    @Override
    public void handlePlayerListHeaderFooter(
        SPacketPlayerListHeaderFooter packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handlePlayerListHeaderFooter(packetIn);
    }

    @Override
    public void handlePlayerListItem(
        SPacketPlayerListItem packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handlePlayerListItem(packetIn);
    }

    @Override
    public void handlePlayerPosLook(
        SPacketPlayerPosLook packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handlePlayerPosLook(packetIn);
    }

    @Override
    public void handleRecipeBook(
        SPacketRecipeBook packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleRecipeBook(packetIn);
    }

    @Override
    public void handleRemoveEntityEffect(
        SPacketRemoveEntityEffect packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleRemoveEntityEffect(packetIn);
    }

    @Override
    public void handleResourcePack(
        SPacketResourcePackSend packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleResourcePack(packetIn);
    }

    @Override
    public void handleRespawn(
        SPacketRespawn packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleRespawn(packetIn);
    }

    @Override
    public void handleScoreboardObjective(
        SPacketScoreboardObjective packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleScoreboardObjective(packetIn);
    }

    @Override
    public void handleSelectAdvancementsTab(
        SPacketSelectAdvancementsTab packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSelectAdvancementsTab(packetIn);
    }

    @Override
    public void handleServerDifficulty(
        SPacketServerDifficulty packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleServerDifficulty(packetIn);
    }

    @Override
    public void handleSetExperience(
        SPacketSetExperience packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSetExperience(packetIn);
    }

    @Override
    public void handleSetPassengers(
        SPacketSetPassengers packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSetPassengers(packetIn);
    }

    @Override
    public void handleSetSlot(
        SPacketSetSlot packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSetSlot(packetIn);
    }

    @Override
    public void handleSignEditorOpen(
        SPacketSignEditorOpen packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSignEditorOpen(packetIn);
    }

    @Override
    public void handleSoundEffect(
        SPacketSoundEffect packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSoundEffect(packetIn);
    }

    @Override
    public void handleSpawnExperienceOrb(
        SPacketSpawnExperienceOrb packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSpawnExperienceOrb(packetIn);
    }

    @Override
    public void handleSpawnGlobalEntity(
        SPacketSpawnGlobalEntity packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSpawnGlobalEntity(packetIn);
    }

    @Override
    public void handleSpawnMob(
        SPacketSpawnMob packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSpawnMob(packetIn);
    }

    @Override
    public void handleSpawnObject(
        SPacketSpawnObject packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSpawnObject(packetIn);
    }

    @Override
    public void handleSpawnPainting(
        SPacketSpawnPainting packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSpawnPainting(packetIn);
    }

    @Override
    public void handleSpawnPlayer(
        SPacketSpawnPlayer packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSpawnPlayer(packetIn);
    }

    @Override
    public void handleSpawnPosition(
        SPacketSpawnPosition packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleSpawnPosition(packetIn);
    }

    @Override
    public void handleStatistics(
        SPacketStatistics packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleStatistics(packetIn);
    }

    @Override
    public void handleTabComplete(
        SPacketTabComplete packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleTabComplete(packetIn);
    }

    @Override
    public void handleTeams(
        SPacketTeams packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleTeams(packetIn);
    }

    @Override
    public void handleTimeUpdate(
        SPacketTimeUpdate packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleTimeUpdate(packetIn);
    }

    @Override
    public void handleTitle(
        SPacketTitle packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleTitle(packetIn);
    }

    @Override
    public void handleUpdateBossInfo(
        SPacketUpdateBossInfo packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleUpdateBossInfo(packetIn);
    }

    @Override
    public void handleUpdateHealth(
        SPacketUpdateHealth packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleUpdateHealth(packetIn);
    }

    @Override
    public void handleUpdateScore(
        SPacketUpdateScore packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleUpdateScore(packetIn);
    }

    @Override
    public void handleUpdateTileEntity(
        SPacketUpdateTileEntity packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleUpdateTileEntity(packetIn);
    }

    @Override
    public void handleUseBed(
        SPacketUseBed packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleUseBed(packetIn);
    }

    @Override
    public void handleWindowItems(
        SPacketWindowItems packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleWindowItems(packetIn);
    }

    @Override
    public void handleWindowProperty(
        SPacketWindowProperty packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleWindowProperty(packetIn);
    }

    @Override
    public void handleWorldBorder(
        SPacketWorldBorder packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.handleWorldBorder(packetIn);
    }

    @Override
    public void processChunkUnload(
        SPacketUnloadChunk packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.processChunkUnload(packetIn);
    }

    @Override
    public void func_194307_a(
        SPacketPlaceGhostRecipe packetIn
    ) {
        lastPacketMillis = currentTimeMillis();

        decoratedPlayClient.func_194307_a(packetIn);
    }

    @Override
    public void onDisconnect(
        ITextComponent reason
    ) {
        decoratedPlayClient.onDisconnect(reason);
    }

    @Override
    public void cleanup() {
        decoratedPlayClient.cleanup();
    }

    @Override
    public NetworkManager getNetworkManager() {
        return decoratedPlayClient.getNetworkManager();
    }

    @Override
    public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
        return decoratedPlayClient.getPlayerInfoMap();
    }

    @Override
    public NetworkPlayerInfo getPlayerInfo(
        UUID uniqueId
    ) {
        return decoratedPlayClient.getPlayerInfo(uniqueId);
    }

    @Nullable
    @Override
    public NetworkPlayerInfo getPlayerInfo(
        String name
    ) {
        return decoratedPlayClient.getPlayerInfo(name);
    }

    @Override
    public GameProfile getGameProfile() {
        return decoratedPlayClient.getGameProfile();
    }

    @Override
    public ClientAdvancementManager getAdvancementManager() {
        return decoratedPlayClient.getAdvancementManager();
    }

}
