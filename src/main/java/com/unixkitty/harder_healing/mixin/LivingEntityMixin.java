package com.unixkitty.harder_healing.mixin;

import com.unixkitty.harder_healing.RegenDelayManager;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
    @Redirect(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;lastDamageStamp:J", opcode = Opcodes.PUTFIELD))
    public void onHurtTimestamp(LivingEntity instance, long value)
    {
        RegenDelayManager.onDamageTimestamp(instance, value);
    }

    @Redirect(method = "handleDamageEvent(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;lastDamageStamp:J", opcode = Opcodes.PUTFIELD))
    public void onDamageEventTimestamp(LivingEntity instance, long value)
    {
        RegenDelayManager.onDamageTimestamp(instance, value);
    }
}
