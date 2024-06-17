package com.unixkitty.harder_healing.mixin;

import com.unixkitty.harder_healing.RegenDelayManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public class FoodDataMixin
{
    @Shadow
    public int tickTimer;
    @Unique
    private Player harder_healing$player;

    @Redirect(method = "tick(Lnet/minecraft/world/entity/player/Player;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"))
    private void onPlayerHeal(Player player, float healAmount)
    {
        if (harder_healing$player != player)
        {
            harder_healing$player = player;
        }

        RegenDelayManager.handleHeal(player, player.getId(), this.tickTimer, healAmount);
    }

    @Redirect(method = "tick(Lnet/minecraft/world/entity/player/Player;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"))
    private void onAddExhaustion(FoodData foodData, float exhaustionAmount)
    {
        RegenDelayManager.handleExhaustion(harder_healing$player.getId(), this.tickTimer, foodData, exhaustionAmount);
    }
}
