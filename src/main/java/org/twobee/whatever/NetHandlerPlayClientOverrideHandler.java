package org.twobee.whatever;

import lombok.SneakyThrows;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.twobee.whatever.networking.TimeTrackingNetHandlerPlayClient;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import static net.minecraft.client.Minecraft.getMinecraft;
import static net.minecraftforge.fml.relauncher.Side.CLIENT;
import static org.twobee.whatever.WhateverMod.MODID;
import static org.twobee.whatever.WhateverMod.log;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid = MODID, value = CLIENT)
public class NetHandlerPlayClientOverrideHandler {

    private static NetHandlerPlayClient originalPlayClient = null;
    private static TimeTrackingNetHandlerPlayClient replacementPlayClient = null;

    private static boolean isPlayerUsingReplacement = false;

    public static TimeTrackingNetHandlerPlayClient getReplacementPlayClient() {
        return replacementPlayClient;
    }

    @SubscribeEvent
    @SneakyThrows
    public static void onEvent(
        FMLNetworkEvent.ClientConnectedToServerEvent event
    ) {
        FMLClientHandler clientHandler = FMLClientHandler.instance();

        if (!(clientHandler.getClientPlayHandler() instanceof NetHandlerPlayClient)
            || clientHandler.getClientPlayHandler() instanceof TimeTrackingNetHandlerPlayClient) {

            log.error(
                "Cannot install TimeTrackingNetHandlerPlayClient -- " +
                    "playClient is not compatible"
            );

            return;
        }

        originalPlayClient = (NetHandlerPlayClient) clientHandler.getClientPlayHandler();
        replacementPlayClient = new TimeTrackingNetHandlerPlayClient(originalPlayClient);

        // Override play client in NetworkManager

        event.getManager().setNetHandler(replacementPlayClient);

        // Override play client in FMLClientHandler

        Field currentPlayClientField = FMLClientHandler.class.getDeclaredField("currentPlayClient");

        currentPlayClientField.setAccessible(true);
        currentPlayClientField.set(clientHandler, new WeakReference<>(replacementPlayClient));
    }

    @SubscribeEvent
    @SneakyThrows
    public static void onEvent(
        TickEvent.PlayerTickEvent event
    ) {
        if (isPlayerUsingReplacement
            || replacementPlayClient == null
            || !(event.player instanceof EntityPlayerSP)) {
            return;
        }

        isPlayerUsingReplacement = true;

        // Override play client in EntityPlayerSP

        EntityPlayerSP player = (EntityPlayerSP) event.player;

        Field playerConnectionField = EntityPlayerSP.class.getDeclaredField("field_71174_a");

        playerConnectionField.setAccessible(true);
        playerConnectionField.set(player, replacementPlayClient);

        // Override play client in PlayerControllerMP

        Field playerControllerConnectionField = PlayerControllerMP.class.getDeclaredField("field_78774_b");

        playerControllerConnectionField.setAccessible(true);
        playerControllerConnectionField.set(getMinecraft().playerController, replacementPlayClient);
    }

}
