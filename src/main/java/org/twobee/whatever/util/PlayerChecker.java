package org.twobee.whatever.util;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class PlayerChecker {

    public static boolean isPlayer(
        Entity entity
    ) {
        return (entity instanceof EntityPlayerSP);
    }

    public static boolean isAnotherPlayer(
        Entity player
    ) {
        return (player instanceof EntityOtherPlayerMP);
    }

}
