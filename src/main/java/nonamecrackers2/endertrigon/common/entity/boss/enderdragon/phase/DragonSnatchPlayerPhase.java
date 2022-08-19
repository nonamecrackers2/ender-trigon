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

package nonamecrackers2.endertrigon.common.entity.boss.enderdragon.phase;

import javax.annotation.Nullable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.util.EnderDragonHelper;

public class DragonSnatchPlayerPhase extends AbstractDragonPhaseInstance
{
	private static final int MAX_CHARGE_TIME = 240;
	private @Nullable LivingEntity target;
	private int timeSinceCharge;
	
	public DragonSnatchPlayerPhase(EnderDragon dragon)
	{
		super(dragon);
	}
	
	@Override
	public void doServerTick()
	{
		if (this.target != null)
		{
			EnderDragonHelper.moveUnrestrictedY(this.dragon, this.target.getEyePosition(), this.getTurnSpeed(), 0.5F);
			
			this.timeSinceCharge++;
			if (this.target.getBoundingBox().intersects(this.dragon.getBoundingBox()))
			{
				this.target.startRiding(this.dragon);
				this.dragon.getPhaseManager().setPhase(EnderTrigonMod.CARRY_PLAYER);
				this.dragon.getPhaseManager().getPhase(EnderTrigonMod.CARRY_PLAYER).setTarget(this.target);
				return;
			}
			if (this.timeSinceCharge > MAX_CHARGE_TIME)
				this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
		}
		else
		{
			this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
		}
	}
	
	@Override
	public void begin()
	{
		this.target = null;
		this.timeSinceCharge = 0;
	}
	
	public void setTarget(@Nullable LivingEntity player)
	{
		this.target = player;
	}
	
	@Override
	public float getFlySpeed()
	{
		return 3.0F;
	}

	@Override
	public EnderDragonPhase<DragonSnatchPlayerPhase> getPhase()
	{
		return EnderTrigonMod.SNATCH_PLAYER;
	}
}
