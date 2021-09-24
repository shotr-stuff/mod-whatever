package org.twobee.whatever.util;

import static net.minecraft.client.Minecraft.getMinecraft;
import static org.twobee.whatever.WhateverMod.log;

public class ServerChecker {

    public static boolean serverAvailable(
        boolean warn
    ) {
        if (getMinecraft().getCurrentServerData() == null) {

            if (warn) {
                log.warn("Server is not available");
            }

            return false;
        }

        return true;
    }

}
