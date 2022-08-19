package nonamecrackers2.endertrigon.mixin;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;

@Mixin(EnderDragonPhaseManager.class)
public abstract class MixinEnderDragonPhaseManager
{
	@Shadow
	private static Logger LOGGER;
	@Shadow
	private EnderDragon dragon;
	@Shadow
	private @Nullable DragonPhaseInstance currentPhase;
	
	//Logs at the info level along with debug
	@Inject(
		method = "setPhase",
		at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;debug(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V")
	)
	public void setPhaseInvoke(EnderDragonPhase<?> phase, CallbackInfo ci)
	{
		LOGGER.info("Dragon is now in phase {} on the {}", phase, this.dragon.level.isClientSide ? "client" : "server");
	}
}
