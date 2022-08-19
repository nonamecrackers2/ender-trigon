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

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.init.EnderTrigonBlocks;
import nonamecrackers2.endertrigon.common.init.EnderTrigonSoundEvents;
import nonamecrackers2.endertrigon.common.util.EnderDragonHelper;

public class DragonDiveBombPlayerPhase extends AbstractDragonPhaseInstance
{
	private static final int MAX_EGGS = 4;
	private @Nullable LivingEntity target;
	private int eggsDropped;
	
	public DragonDiveBombPlayerPhase(EnderDragon dragon)
	{
		super(dragon);
	}
	
	@Override
	public void doServerTick()
	{
		if (this.target != null)
		{
			Vec3 target = this.target.position().add(0.0D, 10.0D, 0.0D);
			EnderDragonHelper.moveUnrestrictedY(this.dragon, target, this.getTurnSpeed(), 0.5F);
			
			if (this.eggsDropped < MAX_EGGS)
			{
				if (this.dragon.position().distanceTo(target) < 8.0D && this.dragon.getRandom().nextInt(8) == 0 || this.dragon.tickCount % 120 == 0)
				{
					BlockPos pos = this.dragon.blockPosition();
					if (this.dragon.level.getBlockState(pos).isAir())
					{
						FallingBlockEntity entity = FallingBlockEntity.fall(this.dragon.level, pos, EnderTrigonBlocks.BABY_DRAGON_EGG.get().defaultBlockState());
						entity.setDeltaMovement(this.dragon.getRandom().nextGaussian() * 0.2D, 0.0D, this.dragon.getRandom().nextGaussian() * 0.2D);
						this.eggsDropped++;
						this.dragon.playSound(EnderTrigonSoundEvents.ENDER_DRAGON_LAYS_EGG.get(), 10.0F, 1.0F);
					}
				}
			}
			else
			{
				this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
			}
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
		this.eggsDropped = 0;
	}
	
	public void setTarget(@Nullable LivingEntity target)
	{
		this.target = target;
	}
	
	@Override
	public EnderDragonPhase<DragonDiveBombPlayerPhase> getPhase()
	{
		return EnderTrigonMod.DIVE_BOMB_PLAYER;
	}
}
