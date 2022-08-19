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

package nonamecrackers2.endertrigon.common.entity.goal;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;

public class BabyEnderDragonSweepGoal extends BabyEnderDragonMoveTargetGoal
{
	public BabyEnderDragonSweepGoal(BabyEnderDragon dragon)
	{
		super(dragon);
	}

	@Override
	public boolean canUse()
	{
		return this.dragon.getTarget() != null && this.dragon.getPhase() == BabyEnderDragon.AttackPhase.SWOOP;
	}
	
	@Override
	public boolean canContinueToUse()
	{
		LivingEntity target = this.dragon.getTarget();
		if (target == null)
		{
			return false;
		}
		else if (!target.isAlive())
		{
			return false;
		}
		else
		{
			if (target instanceof Player player)
			{
				if (player.isSpectator() || player.isCreative())
					return false;
			}
			
			if (!this.canUse())
				return false;
			else
				return true;
		}
	}
	
	@Override
	public void stop()
	{
		this.dragon.setTarget(null);
		this.dragon.setPhase(BabyEnderDragon.AttackPhase.CIRCLE);
	}
	
	@Override
	public void tick()
	{
		LivingEntity target = this.dragon.getTarget();
		if (target != null)
		{
			this.dragon.setMoveTarget(new Vec3(target.getX(), target.getY(0.5D), target.getZ()));
			if (this.dragon.getBoundingBox().inflate(0.2D).intersects(target.getBoundingBox()))
			{
				this.dragon.doHurtTarget(target);
				if (this.dragon.getRandom().nextInt(10) == 0)
					this.dragon.setPhase(BabyEnderDragon.AttackPhase.CIRCLE);
				this.dragon.playSound(SoundEvents.ENDER_DRAGON_GROWL, 2.0F, this.dragon.getVoicePitch());
			}
			else if (this.dragon.horizontalCollision)
			{
				this.dragon.setPhase(BabyEnderDragon.AttackPhase.CIRCLE);
			}
		}
	}
}
