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

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonStrafePlayerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.util.DragonStrafeExtension;

@Mixin(DragonStrafePlayerPhase.class)
public abstract class MixinDragonStrafePlayerPhase extends AbstractDragonPhaseInstance implements DragonStrafeExtension
{
	private int timesStrafing;
	
	private MixinDragonStrafePlayerPhase(EnderDragon dragon)
	{
		super(dragon);
	}

	@Inject(
		method = "doServerTick",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/phases/EnderDragonPhaseManager;setPhase(Lnet/minecraft/world/entity/boss/enderdragon/phases/EnderDragonPhase;)V"),
		cancellable = true
	)
	public void doServerTickInvoke(CallbackInfo ci)
	{
		if (this.getAttackTarget() != null)
		{
			int i = this.dragon.getDragonFight() == null ? 0 : this.dragon.getDragonFight().getCrystalsAlive();
			if (i == 0 || this.dragon.getRandom().nextInt(i) == 0)
			{
				this.dragon.getPhaseManager().setPhase(EnderTrigonMod.CHARGE_UP);
			}
			else
			{
				this.dragon.getPhaseManager().setPhase(EnderDragonPhase.CHARGING_PLAYER);
				this.dragon.getPhaseManager().getPhase(EnderDragonPhase.CHARGING_PLAYER).setTarget(this.getAttackTarget().position());
			}
			ci.cancel();
		}
	}
	
	@Accessor
	public abstract @Nullable LivingEntity getAttackTarget();
	
	@Inject(
		method = "begin",
		at = @At("HEAD")
	)
	public void beginHead(CallbackInfo ci)
	{
		this.countStrafe();
	}
	
	@Override
	public int getTimesStrafing()
	{
		return this.timesStrafing;
	}
	
	@Override
	public void setTimesStrafing(int amount)
	{
		this.timesStrafing = amount;
	}
}
