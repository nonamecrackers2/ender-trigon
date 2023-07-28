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

import java.util.function.BiConsumer;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonSittingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import nonamecrackers2.endertrigon.EnderTrigonMod;

public class DragonChargeUpPhase extends AbstractDragonSittingPhase
{
	private static final int CHARGE_UP_TIME = 50;
	private static final TargetingConditions CHARGE_TARGETING = TargetingConditions.forCombat();
	private int chargeUpTicks;
	private DragonChargeUpPhase.AttackType previousAttack = DragonChargeUpPhase.AttackType.CRASH_PLAYER;
	
	public DragonChargeUpPhase(EnderDragon dragon)
	{
		super(dragon);
	}
	
	@Override
	public void doClientTick()
	{
		this.dragon.level().playLocalSound(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), SoundEvents.ENDER_DRAGON_GROWL, this.dragon.getSoundSource(), 10.0F, 0.8F + this.dragon.getRandom().nextFloat() * 0.3F, false);
	}
	
	@Override
	public void doServerTick()
	{
		BlockPos pos = this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(BlockPos.ZERO));
		Player player = this.dragon.level().getNearestPlayer(CHARGE_TARGETING, this.dragon, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
		this.chargeUpTicks++;
		if (this.chargeUpTicks > CHARGE_UP_TIME)
		{
			if (player != null)
			{
				DragonChargeUpPhase.AttackType type = DragonChargeUpPhase.AttackType.random(this.dragon.getRandom(), this.previousAttack);
				type.activate(this.dragon.getPhaseManager(), player);
				this.previousAttack = type;
			}
			else
			{
				this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
			}
		}
		else
		{
			if (player != null)
			{
				double x = player.getX() - this.dragon.getX();
				double z = player.getZ() - this.dragon.getZ();
				float yRot = (float)(Mth.atan2(z, x) * (double)(180F / (float)Math.PI)) + 90.0F;
				this.dragon.setYRot(yRot);
			}
		}
	}
	
	@Override
	public void begin()
	{
		this.chargeUpTicks = 0;
	}

	@Override
	public EnderDragonPhase<DragonChargeUpPhase> getPhase()
	{
		return EnderTrigonMod.CHARGE_UP;
	}
	
	public static enum AttackType
	{
		SNATCH_PLAYER((manager, entity) -> 
		{
			manager.setPhase(EnderTrigonMod.SNATCH_PLAYER);
			manager.getPhase(EnderTrigonMod.SNATCH_PLAYER).setTarget(entity);
		}),
		CRASH_PLAYER((manager, entity) -> 
		{
			manager.setPhase(EnderTrigonMod.CRASH_PLAYER);
			manager.getPhase(EnderTrigonMod.CRASH_PLAYER).setTarget(entity);
		}),
		DIVE_BOMB_PLAYER((manager, entity) ->
		{
			manager.setPhase(EnderTrigonMod.DIVE_BOMB_PLAYER);
			manager.getPhase(EnderTrigonMod.DIVE_BOMB_PLAYER).setTarget(entity);
		});
		
		private final BiConsumer<EnderDragonPhaseManager, LivingEntity> consumer;
		
		private AttackType(BiConsumer<EnderDragonPhaseManager, LivingEntity> consumer)
		{
			this.consumer = consumer;
		}
		
		public void activate(EnderDragonPhaseManager manager, LivingEntity target)
		{
			this.consumer.accept(manager, target);
		}
		
		public static AttackType random(RandomSource random, AttackType toExclude)
		{
			AttackType[] types = values();
			if (ArrayUtils.contains(types, toExclude))
				types = ArrayUtils.removeElement(types, toExclude);
			return Util.getRandom(types, random);
		}
	}
}
