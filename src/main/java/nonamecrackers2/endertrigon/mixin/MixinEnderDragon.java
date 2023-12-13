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

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.EnderDragonHead;
import nonamecrackers2.endertrigon.common.util.EnderDragonExtension;

@Mixin(EnderDragon.class)
public abstract class MixinEnderDragon extends Mob implements EnderDragonExtension, Enemy
{
	private static final Predicate<Entity> NON_KNOCKBACKABLE = (entity) ->
	{
		return entity instanceof BabyEnderDragon;
	};
	@Shadow
	private EnderDragonPart[] subEntities;
	private EnderDragonHead[] otherHeads;
	
	private MixinEnderDragon(EntityType<? extends Mob> type, Level level)
	{
		super(type, level);
	}

	@Inject(
		method = "<init>",
		at = @At("TAIL")
	)
	public void constructorTail(EntityType<? extends EnderDragon> type, Level level, CallbackInfo ci)
	{
		var head1 = new EnderDragonPart(this.getSelf(), "head1", 1.0F, 1.0F);
		var neck1 = new EnderDragonPart(this.getSelf(), "neck1", 3.0F, 3.0F);
		var head2 = new EnderDragonPart(this.getSelf(), "head2", 1.0F, 1.0F);
		var neck2 = new EnderDragonPart(this.getSelf(), "neck2", 3.0F, 3.0F);
		this.otherHeads = new EnderDragonHead[] {
				new EnderDragonHead(this.getSelf(), head1, neck1, -30.0F, 5.0F, -18.0F, -0.5F, 1),
				new EnderDragonHead(this.getSelf(), head2, neck2, 30.0F, 5.0F, 18.0F, -0.5F, 2)
		};
		var entities = new EnderDragonPart[] {head1, neck1, head2, neck2};
		this.subEntities = ArrayUtils.addAll(this.subEntities, entities);
	}
	
	@Overwrite
	private void hurt(List<Entity> entities)
	{
		for (Entity entity : entities)
		{
			if (entity instanceof LivingEntity)
			{
				if (!NON_KNOCKBACKABLE.test(entity) && !this.getSelf().getPassengers().contains(entity))
				{
					entity.hurt(this.damageSources().mobAttack(this), 10.0F);
					this.doEnchantDamageEffects(this, entity);
				}
			}
		}
	}
	
	@Overwrite
	private void knockBack(List<Entity> entities)
	{
		EnderDragon self = this.getSelf();
		double d0 = (this.getBody().getBoundingBox().minX + this.getBody().getBoundingBox().maxX) / 2.0D;
		double d1 = (this.getBody().getBoundingBox().minZ + this.getBody().getBoundingBox().maxZ) / 2.0D;

		for (Entity entity : entities)
		{
			if (!NON_KNOCKBACKABLE.test(entity) && entity instanceof LivingEntity living)
			{
				double d2 = entity.getX() - d0;
				double d3 = entity.getZ() - d1;
				double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
				entity.push(d2 / d4 * 4.0D, (double) 0.2F, d3 / d4 * 4.0D);
				if (!self.getPhaseManager().getCurrentPhase().isSitting() && living.getLastHurtByMobTimestamp() < entity.tickCount - 2)
				{
					entity.hurt(this.damageSources().mobAttack(this), 5.0F);
					this.doEnchantDamageEffects(this, entity);
				}
			}
		}
	}
	
	@Override
	public void positionRider(Entity entity, Entity.MoveFunction moveFunction)
	{
		super.positionRider(entity, moveFunction);
		EnderDragonHead head = this.getOtherHeads()[EnderDragonExtension.THIRD_HEAD];
		if (entity.distanceTo(head.head) < 20.0D)
			moveFunction.accept(entity, head.head.getX(), head.head.getY(), head.head.getZ());
	}
	
	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity passenger)
	{
		EnderDragonHead head = this.getOtherHeads()[EnderDragonExtension.THIRD_HEAD];
		if (passenger.distanceTo(head.head) < 20.0D)
			return head.head.position();
		else
			return super.getDismountLocationForPassenger(passenger);
	}
	
	@Override
	public boolean ignoreExplosion(Explosion explosion)
	{
		return this.getSelf().getPhaseManager().getCurrentPhase().getPhase() == EnderTrigonMod.CRASH_PLAYER;
	}
	
	@Inject(
		method = "aiStep",
		at = @At("TAIL")
	)
	public void aiStepTail(CallbackInfo ci)
	{
		for (EnderDragonHead head : this.otherHeads)
			head.tick();
	}
	
	@Accessor
	public abstract EnderDragonPart getBody();
	
	@Override
	public EnderDragonHead[] getOtherHeads()
	{
		return this.otherHeads;
	}
	
	private EnderDragon getSelf()
	{
		return ((EnderDragon)(Mob)this);
	}
}
