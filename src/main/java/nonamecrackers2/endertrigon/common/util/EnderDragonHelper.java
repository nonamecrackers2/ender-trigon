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

package nonamecrackers2.endertrigon.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.common.entity.DragonFlame;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.EnderDragonHead;

public class EnderDragonHelper
{
	public static void doDragonFlame(EnderDragon dragon)
	{
		BlockPos pos = dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(BlockPos.ZERO));
		Player player = dragon.level().getNearestPlayer(TargetingConditions.forCombat(), dragon, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
		if (player != null && dragon.hasLineOfSight(player))
		{
			float angle = (float)(Mth.atan2(player.getX() - dragon.getX(), player.getZ() - dragon.getZ()) * (180.0D / Math.PI));
			float angleDiff = (Mth.wrapDegrees((float)-dragon.getLatencyPos(7, 1.0F)[0]) - angle + 180 + 360) % 360 - 180;
			if (!(angleDiff <= 80 && angleDiff >= -80))
			{
				EnderDragonExtension mixinDragon = (EnderDragonExtension)dragon;
				EnderDragonHead head = mixinDragon.getOtherHeads()[EnderDragonExtension.SECOND_HEAD];
				Vec3 view = dragon.getViewVector(1.0F);
				double x = head.head.getX() - view.x;
				double y = head.head.getY(0.5D) + 0.5D;
				double z = head.head.getZ() - view.z;
				double xDelta = (player.getX() - x) * 2.0D;
				double yDelta = (player.getEyeY() - y) * 2.0D;
				double zDelta = (player.getZ() - z) * 2.0D;
				if (!dragon.isSilent() && dragon.tickCount % 4 == 0)
					dragon.level().playSound(null, head.head.blockPosition(), SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.HOSTILE, 10.0F, dragon.getRandom().nextFloat());
				DragonFlame flame = new DragonFlame(dragon.level(), dragon, xDelta, yDelta, zDelta);
				flame.moveTo(x, y, z, 0.0F, 0.0F);
				dragon.level().addFreshEntity(flame);
			}
		}
	}
	
	public static void moveUnrestrictedY(EnderDragon dragon, Vec3 to, float turnSpeed, float yAscendSpeed)
	{
		moveUnrestrictedY(dragon, to, turnSpeed, yAscendSpeed, 1.0D);
	}
	
	public static void moveUnrestrictedY(EnderDragon dragon, Vec3 to, float turnSpeed, float yAscendSpeed, double speedMultiplier)
	{
		double d7 = to.x - dragon.getX();
		double d8 = to.y - dragon.getY();
		double d9 = to.z - dragon.getZ();
		double d3 = d7 * d7 + d8 * d8 + d9 * d9;
		d8 = d8 * yAscendSpeed;

		dragon.setDeltaMovement(dragon.getDeltaMovement().add(0.0D, d8 * 0.01D, 0.0D));
		dragon.setYRot(Mth.wrapDegrees(dragon.getYRot()));
		Vec3 vec32 = to.subtract(dragon.getX(), dragon.getY(), dragon.getZ()).normalize();
		Vec3 vec33 = (new Vec3((double) Mth.sin(dragon.getYRot() * ((float) Math.PI / 180F)), dragon.getDeltaMovement().y, (double) (-Mth.cos(dragon.getYRot() * ((float) Math.PI / 180F))))).normalize();
		float f6 = Math.max(((float) vec33.dot(vec32) + 0.5F) / 1.5F, 0.0F);
		if (Math.abs(d7) > (double) 1.0E-5F || Math.abs(d9) > (double) 1.0E-5F)
		{
			float f7 = Mth.clamp(Mth.wrapDegrees(180.0F - (float) Mth.atan2(d7, d9) * (180F / (float) Math.PI) - dragon.getYRot()), -50.0F, 50.0F);
			dragon.yRotA *= 0.8F;
			dragon.yRotA += f7 * turnSpeed;
			dragon.setYRot(dragon.getYRot() + dragon.yRotA * 0.1F);
		}

		float f19 = (float) (2.0D / (d3 + 1.0D));
		dragon.moveRelative(0.06F * (f6 * f19 + (1.0F - f19)), new Vec3(0.0D, 0.0D, -1.0D));
		if (dragon.inWall)
			dragon.move(MoverType.SELF, dragon.getDeltaMovement().scale((double) 0.8F));
		else
			dragon.move(MoverType.SELF, dragon.getDeltaMovement());

		Vec3 vec34 = dragon.getDeltaMovement().normalize();
		double d5 = (0.8D + 0.15D * (vec34.dot(vec33) + 1.0D) / 2.0D) * speedMultiplier;
		dragon.setDeltaMovement(dragon.getDeltaMovement().multiply(d5, (double) 0.91F, d5));
	}
}
