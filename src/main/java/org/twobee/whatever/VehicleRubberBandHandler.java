package org.twobee.whatever;

import com.google.common.collect.EvictingQueue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.google.common.collect.EvictingQueue.create;
import static java.lang.Math.floor;
import static java.lang.System.currentTimeMillis;
import static net.minecraft.client.Minecraft.getMinecraft;
import static net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END;
import static net.minecraftforge.fml.relauncher.Side.CLIENT;
import static org.twobee.whatever.NetHandlerPlayClientOverrideHandler.getReplacementPlayClient;
import static org.twobee.whatever.WhateverMod.MODID;
import static org.twobee.whatever.WhateverMod.log;
import static org.twobee.whatever.util.PlayerChecker.isAnotherPlayer;
import static org.twobee.whatever.util.ServerChecker.serverAvailable;
import static org.twobee.whatever.util.WorldChecker.worldAvailable;

@Mod.EventBusSubscriber(modid = MODID, value = CLIENT)
public class VehicleRubberBandHandler {

    private static final int MOVEMENT_SAMPLE_SIZE = 20;
    private static final int MOVEMENT_THRESHOLD = 8;

    private static final long UNACCEPTABLE_PACKET_DELAY_MS = 1000;

    private static final long STOP_TICKS = 25;

    private static final EvictingQueue<Pair<Integer, Integer>> recentPositions = create(MOVEMENT_SAMPLE_SIZE);
    private static final Set<Pair<Integer, Integer>> distinctMovements = new LinkedHashSet<>();

    private static long stopTicksRemaining = 0;

    @SubscribeEvent
    public synchronized static void onEvent(
        TickEvent.PlayerTickEvent event
    ) {
        if (event.phase != END
            || isAnotherPlayer(event.player)
            || !serverAvailable(false)
            || !worldAvailable(false)) {
            return;
        }

        stopTicksRemaining--;

        if (stopTicksRemaining <= 0) {

            EntityPlayerSP player = getMinecraft().player;

            recentPositions.add(
                Pair.of(
                    (int) floor(player.posX),
                    (int) floor(player.posZ)
                )
            );

            distinctMovements.clear();
            distinctMovements.addAll(recentPositions);
        }
    }

    @SubscribeEvent
    public synchronized static void onEvent(
        LivingEvent.LivingUpdateEvent event
    ) {
        if (!isPlayerInVehicle()
            || !serverAvailable(false)
            || !worldAvailable(false)) {
            return;
        }

        EntityPlayerSP player = getMinecraft().player;

        boolean isPlayerUpdateEvent = event.getEntity().equals(player);
        boolean isPlayerVehicleUpdateEvent = event.getEntity().equals(player.getRidingEntity());

        if (!isPlayerUpdateEvent
            && !isPlayerVehicleUpdateEvent) {
            return;
        }

        if (isPlayerUpdateEvent
            && isPlayerMoving()) {

            if (isPacketDelayUnacceptable(UNACCEPTABLE_PACKET_DELAY_MS)
                || isPlayerTouchingUnloadedChunk()) {

                stopTicksRemaining = STOP_TICKS;

                log.warn(
                    "Risk of disconnection -- " +
                        "cancelling movement updates for the next {} tick(s)",
                    stopTicksRemaining
                );
            }
        }

        if (stopTicksRemaining > 0) {
            event.setCanceled(true);
        }
    }

    private static boolean isPacketDelayUnacceptable(
        long unnacceptableDelay
    ) {
        long lastPacket = getReplacementPlayClient().getLastPacketMillis();
        long packetDelay = (currentTimeMillis() - lastPacket);

        if (packetDelay > unnacceptableDelay) {

            log.warn("Client has not received a packet from server for {}ms", packetDelay);

            return true;
        }

        return false;
    }

    private static boolean isPlayerTouchingUnloadedChunk() {

        EntityPlayerSP player = getMinecraft().player;

        int lastBlockX = (int) floor(player.lastTickPosX);
        int lastBlockZ = (int) floor(player.lastTickPosZ);

        int lastChunkX = lastBlockX >> 4;
        int lastChunkZ = lastBlockZ >> 4;

        int targetBlockX = (int) floor(player.posX);
        int targetBlockZ = (int) floor(player.posZ);

        int targetChunkX = targetBlockX >> 4;
        int targetChunkZ = targetBlockZ >> 4;

        World world = player.getEntityWorld();

        Chunk targetChunk = world.getChunkFromChunkCoords(targetChunkX, targetChunkZ);

        Chunk nearbyChunkN = world.getChunkFromChunkCoords(lastChunkX, lastChunkZ - 1);
        Chunk nearbyChunkE = world.getChunkFromChunkCoords(lastChunkX + 1, lastChunkZ);
        Chunk nearbyChunkS = world.getChunkFromChunkCoords(lastChunkX, lastChunkZ + 1);
        Chunk nearbyChunkW = world.getChunkFromChunkCoords(lastChunkX - 1, lastChunkZ);

        boolean foundUnloadedChunk = false;

        if (!targetChunk.isLoaded()) {

            log.warn(
                "Player in chunk {} {} " +
                    "is trying to enter an unloaded chunk: {} {}",
                lastChunkX, lastChunkZ,
                targetChunkX, targetChunkZ
            );

            foundUnloadedChunk = true;
        }

        if (!nearbyChunkN.isLoaded()) {

            log.warn(
                "Player was in chunk {} {}, " +
                    "now in chunk {} {}, " +
                    "is too close to an unloaded chunk {} {} (N)",
                lastChunkX, lastChunkZ,
                targetChunkX, targetChunkZ,
                lastChunkX, lastChunkZ - 1
            );

            foundUnloadedChunk = true;
        }

        if (!nearbyChunkE.isLoaded()) {

            log.warn(
                "Player was in chunk {} {}, " +
                    "now in chunk {} {}, " +
                    "is too close to an unloaded chunk {} {} (E)",
                lastChunkX, lastChunkZ,
                targetChunkX, targetChunkZ,
                lastChunkX + 1, lastChunkZ
            );

            foundUnloadedChunk = true;
        }

        if (!nearbyChunkS.isLoaded()) {

            log.warn(
                "Player was in chunk {} {}, " +
                    "now in chunk {} {}, " +
                    "is too close to an unloaded chunk {} {} (S)",
                lastChunkX, lastChunkZ,
                targetChunkX, targetChunkZ,
                lastChunkX, lastChunkZ + 1
            );

            foundUnloadedChunk = true;
        }

        if (!nearbyChunkW.isLoaded()) {

            log.warn(
                "Player was in chunk {} {}, " +
                    "now in chunk {} {}, " +
                    "is too close to an unloaded chunk {} {} (W)",
                lastChunkX, lastChunkZ,
                targetChunkX, targetChunkZ,
                lastChunkX - 1, lastChunkZ
            );

            foundUnloadedChunk = true;
        }

        return foundUnloadedChunk;
    }

    private static boolean isPlayerMoving() {
        return distinctMovements.size() >= MOVEMENT_THRESHOLD;
    }

    private static boolean isPlayerInVehicle() {

        EntityPlayerSP player = getMinecraft().player;

        return (player != null && player.isRiding());
    }

}
