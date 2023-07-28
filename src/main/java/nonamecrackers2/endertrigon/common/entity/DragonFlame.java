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

package nonamecrackers2.endertrigon.common.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;
import nonamecrackers2.endertrigon.common.init.EnderTrigonDamageTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonEntityTypes;

public class DragonFlame extends AbstractHurtingProjectile
{
	public DragonFlame(EntityType<? extends DragonFlame> type, Level level)
	{
		super(type, level);
	}
	
	public DragonFlame(Level level, LivingEntity owner, double dx, double dy, double dz)
	{
		super(EnderTrigonEntityTypes.DRAGON_FLAME.get(), owner, dx, dy, dz, level);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if (!this.level().isClientSide && this.tickCount > 40)
			this.discard();
	}
	
	@Override
	protected void onHitEntity(EntityHitResult result)
	{
		super.onHitEntity(result);
		if (!this.level().isClientSide)
		{
			Entity hit = result.getEntity();
			Entity owner = this.getOwner();
			if (!this.ownedBy(hit))
			{
				int i = hit.getRemainingFireTicks();
				hit.setSecondsOnFire(8);
				if (!hit.hurt(flame(this, owner), 8.0F))
					hit.setRemainingFireTicks(i);
				else if (owner instanceof LivingEntity living)
					this.doEnchantDamageEffects(living, hit);
				this.discard();
			}
		}
	}
	
	@Override
	protected boolean shouldBurn()
	{
		return false;
	}
	
	@Override
	protected void onHitBlock(BlockHitResult result)
	{
		super.onHitBlock(result);
		if (!this.level().isClientSide)
		{
			Entity owner = this.getOwner();
			if (!(owner instanceof Mob) || ForgeEventFactory.getMobGriefingEvent(this.level(), owner))
			{
				BlockPos pos = result.getBlockPos().relative(result.getDirection());
				if (this.level().isEmptyBlock(pos))
					this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
			}
			this.discard();
		}
	}
	
	@Override
	protected boolean ownedBy(Entity entity)
	{
		UUID owner = null;
		if (this.getOwner() != null)
			owner = this.getOwner().getUUID();
		return super.ownedBy(entity) || (entity instanceof PartEntity<?> part && part.getParent().getUUID().equals(owner));
	}
	
	public static DamageSource flame(DragonFlame flame, @Nullable Entity hit)
	{
		return hit == null ? flame.damageSources().onFire() : (EnderTrigonDamageTypes.dragonFlame(flame, hit));
	}
	
	@Override
	public boolean isPickable()
	{
		return false;
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount)
	{
		return false;
	}
}
