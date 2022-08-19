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
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonChargePlayerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.common.util.EnderDragonHelper;

@Mixin(DragonChargePlayerPhase.class)
public abstract class MixinDragonChargePlayerPhase extends AbstractDragonPhaseInstance
{
	private MixinDragonChargePlayerPhase(EnderDragon dragon)
	{
		super(dragon);
	}

	@Overwrite
	public void doServerTick()
	{
		if (this.getTargetLocation() == null)
		{
			getLogger().warn("Aborting charge player as no target was set.");
			this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
		}
		else if (this.getTimeSinceCharge() > 0)
		{
			EnderDragonHelper.doDragonFlame(this.dragon);
			
			if (this.countTimeSinceCharge() >= 240)
				this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
		}
		else
		{
			double d0 = this.getTargetLocation().distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
			if (d0 < 100.0D || d0 > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision)
				this.countTimeSinceCharge();
		}
	}
	
	@Accessor("LOGGER")
	public static Logger getLogger()
	{
		throw new AssertionError();
	}
	
	@Accessor
	public abstract @Nullable Vec3 getTargetLocation();
	
	@Accessor
	public abstract int getTimeSinceCharge();
	
	@Accessor
	public abstract void setTimeSinceCharge(int time);
	
	private int countTimeSinceCharge()
	{
		this.setTimeSinceCharge(this.getTimeSinceCharge() + 1);
		return this.getTimeSinceCharge();
	}
}
