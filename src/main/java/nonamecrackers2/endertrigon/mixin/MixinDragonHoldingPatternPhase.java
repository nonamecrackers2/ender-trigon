package nonamecrackers2.endertrigon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonHoldingPatternPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.common.util.DragonStrafeExtension;

@Mixin(DragonHoldingPatternPhase.class)
public abstract class MixinDragonHoldingPatternPhase extends AbstractDragonPhaseInstance
{
	private MixinDragonHoldingPatternPhase(EnderDragon dragon)
	{
		super(dragon);
	}
	
	@Inject(
		method = "doServerTick",
		at = @At("TAIL")
	)
	public void doServerTickTail(CallbackInfo ci)
	{
		int i = this.dragon.getDragonFight() == null ? 0 : this.dragon.getDragonFight().getCrystalsAlive();
		if (this.dragon.tickCount % 20 == 0 && this.dragon.getRandom().nextInt(i + 3) == 0)
		{
			BlockPos pos = this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.END_PODIUM_LOCATION));
			Player player = this.dragon.level.getNearestPlayer(getTargeting(), this.dragon, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
			if (player != null && this.dragon.hasLineOfSight(player))
			{
				Vec3 view = this.dragon.getViewVector(1.0F);
				double x = this.dragon.head.getX() - view.x;
				double y = this.dragon.head.getY(0.5D) + 0.5D;
				double z = this.dragon.head.getZ() - view.z;
				double xDelta = player.getX() - x;
				double yDelta = player.getY(0.5D) - y;
				double zDelta = player.getZ() - z;
				if (!this.dragon.isSilent())
					this.dragon.level.levelEvent((Player)null, 1017, this.dragon.blockPosition(), 0);
				DragonFireball fireball = new DragonFireball(this.dragon.level, this.dragon, xDelta, yDelta, zDelta);
				fireball.moveTo(x, y, z, 0.0F, 0.0F);
				this.dragon.level.addFreshEntity(fireball);
				this.findNewTarget();
			}
		}
	}
	
	@Shadow
	public abstract void findNewTarget();

	@Inject(
		method = "findNewTarget",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/phases/EnderDragonPhaseManager;setPhase(Lnet/minecraft/world/entity/boss/enderdragon/phases/EnderDragonPhase;)V"),
		cancellable = true
	)
	public void findNewTargetInvoke(CallbackInfo ci)
	{
		ci.cancel();
	}
	
	@Inject(
		method = "findNewTarget",
		at = @At("HEAD")
	)
	public void findNewTargetHead(CallbackInfo ci)
	{
		DragonStrafeExtension strafeExtension = (DragonStrafeExtension)this.dragon.getPhaseManager().getPhase(EnderDragonPhase.STRAFE_PLAYER);
		int times = strafeExtension.getTimesStrafing();
		int crystals = this.dragon.getDragonFight() == null ? 0 : this.dragon.getDragonFight().getCrystalsAlive();
		if (times > 4 + crystals)
		{
			this.dragon.getPhaseManager().setPhase(EnderDragonPhase.LANDING_APPROACH);
			strafeExtension.setTimesStrafing(0);
		}
	}
	
	@Accessor("NEW_TARGET_TARGETING")
	public static TargetingConditions getTargeting()
	{
		throw new AssertionError();
	}
}
