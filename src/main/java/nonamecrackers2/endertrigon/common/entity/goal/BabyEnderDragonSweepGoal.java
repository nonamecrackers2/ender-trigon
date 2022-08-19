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
