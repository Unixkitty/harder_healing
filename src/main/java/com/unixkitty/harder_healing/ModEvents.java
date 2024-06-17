package com.unixkitty.harder_healing;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = HarderHealing.MODID)
public class ModEvents
{
    @SubscribeEvent
    public static void onLivingDamaged(final LivingDamageEvent event)
    {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer player)
        {
            HarderHealing.LOG.debug("Setting {}'s damage timer, was: {} | delay: {}", player.getDisplayName().getString(), player.lastDamageStamp, RegenDelayManager.getDelayFor(player));
        }
    }
}
