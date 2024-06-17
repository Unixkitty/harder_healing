package com.unixkitty.harder_healing;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

public class RegenDelayManager
{
    private static final Int2IntOpenHashMap no_damage_ticks_map = new Int2IntOpenHashMap();

    public static void resetMap()
    {
        no_damage_ticks_map.clear();
    }

    public static void onDamageTimestamp(LivingEntity livingEntity, long value)
    {
        if (livingEntity instanceof ServerPlayer)
        {
            no_damage_ticks_map.put(livingEntity.getId(), Config.regenDelay.get().intValue());
        }

        livingEntity.lastDamageStamp = value;
    }

    public static int getDelayFor(ServerPlayer player)
    {
        return no_damage_ticks_map.getOrDefault(player.getId(), 0);
    }

    public static void handleHeal(Player player, int entityId, int tickTimer, float healAmount)
    {
        int timer = getTimer(entityId);

        if (timer > 0)
        {
            no_damage_ticks_map.put(entityId, timer - tickTimer);
        }
        else
        {
            player.heal(healAmount);
        }
    }

    public static void handleExhaustion(int entityId, int tickTimer, FoodData foodData, float exhaustionAmount)
    {
        int timer = getTimer(entityId);

        if (timer > 0)
        {
            no_damage_ticks_map.put(entityId, timer - tickTimer);
        }
        else
        {
            foodData.addExhaustion(exhaustionAmount);
        }
    }

    private static int getTimer(int entityId)
    {
        return no_damage_ticks_map.getOrDefault(entityId, 0);
    }
}
