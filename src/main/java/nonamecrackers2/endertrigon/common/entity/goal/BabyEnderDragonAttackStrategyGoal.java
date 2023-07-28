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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.levelgen.Heightmap;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;

public class BabyEnderDragonAttackStrategyGoal extends Goal
{
	private static final TargetingConditions TARGETING = TargetingConditions.DEFAULT;
	private final BabyEnderDragon dragon;
	private int nextAttack;
	
	public BabyEnderDragonAttackStrategyGoal(BabyEnderDragon dragon)
	{
		this.dragon = dragon;
	}
	
	@Override
	public boolean canUse()
	{
		LivingEntity target = this.dragon.getTarget();
		return target != null && this.dragon.canAttack(target, TARGETING);
	}
	
	@Override
	public void start()
	{
		this.nextAttack = this.adjustedTickDelay(40) + this.dragon.getRandom().nextInt(20);
		this.dragon.setPhase(BabyEnderDragon.AttackPhase.CIRCLE);
		this.setAnchorAboveTarget();
	}
	
	@Override
	public void tick()
	{
		if (this.dragon.getPhase() == BabyEnderDragon.AttackPhase.CIRCLE)
		{
			this.nextAttack--;
			if (this.nextAttack <= 0)
			{
				this.dragon.setPhase(BabyEnderDragon.AttackPhase.SWOOP);
				this.setAnchorAboveTarget();
				this.nextAttack = 80 + this.dragon.getRandom().nextInt(60);
				this.dragon.playSound(SoundEvents.ENDER_DRAGON_AMBIENT, 3.0F, this.dragon.getVoicePitch());
			}
		}
	}
	
	@Override
	public void stop()
	{
		this.dragon.setAnchor(this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.dragon.getAnchor()).above(10 + this.dragon.getRandom().nextInt(5)));
	}
	
	private void setAnchorAboveTarget()
	{
		this.dragon.setAnchor(this.dragon.getTarget().blockPosition().above(10 + this.dragon.getRandom().nextInt(5)));
	}
}
