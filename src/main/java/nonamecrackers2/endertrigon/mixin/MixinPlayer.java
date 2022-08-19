package nonamecrackers2.endertrigon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import nonamecrackers2.endertrigon.EnderTrigonMod;

@Mixin(Player.class)
public class MixinPlayer
{
	@Inject(
		method = "wantsToStopRiding",
		at = @At("HEAD"),
		cancellable = true
	)
	protected void wantsToStopRiding(CallbackInfoReturnable<Boolean> callback)
	{
		if (((Player)(Object)this).getVehicle() instanceof EnderDragon dragon)
		{
			EnderDragonPhase<?> phase = dragon.getPhaseManager().getCurrentPhase().getPhase();
			if (phase == EnderTrigonMod.SNATCH_PLAYER || phase == EnderTrigonMod.CARRY_PLAYER)
				callback.setReturnValue(false);
		}
	}
}
