package org.twobee.whatever;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = WhateverMod.MODID,
    name = WhateverMod.NAME,
    version = WhateverMod.VERSION,
    clientSideOnly = true
)
@Mod.EventBusSubscriber(modid = WhateverMod.MODID, value = {Side.CLIENT})
public class WhateverMod {

    public static final Logger log = LogManager.getLogger(WhateverMod.MODID);

    public static final String MODID = "whatever";
    public static final String NAME = "Whatever";
    public static final String VERSION = "1.0";

}
