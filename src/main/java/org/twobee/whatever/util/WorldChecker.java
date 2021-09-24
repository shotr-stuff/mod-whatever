package org.twobee.whatever.util;

import static net.minecraft.client.Minecraft.getMinecraft;
import static org.twobee.whatever.WhateverMod.log;

public class WorldChecker {

    public static boolean worldAvailable(
        boolean warn
    ) {
        if (getMinecraft().world == null) {

            if (warn) {
                log.warn("World is not available");
            }

            return false;
        }

        return true;
    }

}
