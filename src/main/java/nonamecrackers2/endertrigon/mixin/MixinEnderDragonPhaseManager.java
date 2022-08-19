/*
 * Copyright 2022 nonamecrackers2

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
