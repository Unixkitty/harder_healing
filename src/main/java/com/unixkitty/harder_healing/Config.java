package com.unixkitty.harder_healing;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = HarderHealing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.IntValue regenDelay;

    static
    {
        ForgeConfigSpec.Builder commonConfig = new ForgeConfigSpec.Builder();

        regenDelay = commonConfig.comment("Ticks to wait before allowing health to regenerate after taking damage").comment("Set to 0 to disable the feature").defineInRange("regenDelay", 100, 0, Integer.MAX_VALUE);

        COMMON_CONFIG = commonConfig.build();
    }

    private static void reload(ModConfig config, ModConfig.Type type)
    {
        if (Objects.requireNonNull(type) == ModConfig.Type.COMMON)
        {
            COMMON_CONFIG.setConfig(config.getConfigData());
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event)
    {
        if (!event.getConfig().getModId().equals(HarderHealing.MODID)) return;

        reload(event.getConfig(), event.getConfig().getType());
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading event)
    {
        if (!event.getConfig().getModId().equals(HarderHealing.MODID)) return;

        reload(event.getConfig(), event.getConfig().getType());
    }

    public static void register(ModLoadingContext modLoadingContext)
    {
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }
}
