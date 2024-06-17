package com.unixkitty.harder_healing;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HarderHealing.MODID)
public class HarderHealing
{
    // The MODID value here should match an entry in the META-INF/mods.toml file
    public static final String MODID = "harder_healing";
    public static final String MODNAME = "Harder Healing";

    public static final Logger LOG = LogManager.getLogger(MODNAME);

    public HarderHealing()
    {
        Config.register(ModLoadingContext.get());

        MinecraftForge.EVENT_BUS.addListener(this::onLevelLoad);
    }

    private void onLevelLoad(final LevelEvent.Load event)
    {
        if (event.getLevel() instanceof ServerLevel)
        {
            RegenDelayManager.resetMap();
        }
    }
}
