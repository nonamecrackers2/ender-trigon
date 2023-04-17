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

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.util.EnderDragonHelper;

public class DragonCrashPlayerPhase extends AbstractDragonPhaseInstance
{
	private static final int MAX_CHARGE_TIME = 180;
	private @Nullable LivingEntity target;
	private int timeSinceCharge;
	
	public DragonCrashPlayerPhase(EnderDragon dragon)
	{
		super(dragon);
	}
	
	@Override
	public void doClientTick()
	{
		if (this.dragon.inWall)
			this.dragon.level.playLocalSound(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), SoundEvents.ENDER_DRAGON_GROWL, this.dragon.getSoundSource(), 10.0F, 0.8F + this.dragon.getRandom().nextFloat() * 0.3F, false);
	}
	
	@Override
	public void doServerTick()
	{
		if (this.target != null)
		{
			EnderDragonHelper.moveUnrestrictedY(this.dragon, this.target.position().add(0.0D, -2.0D, 0.0D), this.getTurnSpeed(), 0.5F, 0.9D);
			
			this.timeSinceCharge++;
			if (this.dragon.inWall && ForgeEventFactory.getMobGriefingEvent(this.dragon.level, this.dragon))
			{
				Explosion explosion = this.dragon.level.explode(this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), 7.0F, Level.ExplosionInteraction.NONE);
				int radius = 3;
				for (int x = -radius; x < radius; x++)
				{
					for (int z = -radius; z < radius; z++)
					{
						BlockPos pos = BlockPos.containing(this.dragon.getX() + x, 0.0D, this.dragon.getZ() + z);
						pos = this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos).below();
						BlockState state = this.dragon.level.getBlockState(pos);
						FluidState fluid = this.dragon.level.getFluidState(pos);
						ExplosionDamageCalculator calculator = new ExplosionDamageCalculator();
						Optional<Float> resistance = calculator.getBlockExplosionResistance(explosion, this.dragon.level, pos, state, fluid);
						if (resistance.isPresent() && !state.isAir() && resistance.get() < 20.0F && this.dragon.getRandom().nextInt(12) == 0)
						{
							FallingBlockEntity block = FallingBlockEntity.fall(this.dragon.level, pos, state);
							block.time = 560;
							double xDelta = block.getX() - this.dragon.getX();
							double zDelta = block.getZ() - this.dragon.getZ();
							Vec3 delta = new Vec3(xDelta, 3.0D, zDelta).normalize().scale(0.8D);
							block.setDeltaMovement(delta);
						}
					}
				}
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
	
	public void setTarget(@Nullable LivingEntity target)
	{
		this.target = target;
	}

	@Override
	public EnderDragonPhase<DragonCrashPlayerPhase> getPhase()
	{
		return EnderTrigonMod.CRASH_PLAYER;
	}
}
