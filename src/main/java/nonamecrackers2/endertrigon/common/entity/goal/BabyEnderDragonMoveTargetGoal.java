package nonamecrackers2.endertrigon.common.entity.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;

public abstract class BabyEnderDragonMoveTargetGoal extends Goal
{
	protected final BabyEnderDragon dragon;
	
	public BabyEnderDragonMoveTargetGoal(BabyEnderDragon dragon)
	{
		this.dragon = dragon;
	}
	
	protected boolean touchingTarget()
	{
		return this.dragon.getMoveTarget().distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ()) < 4.0D;
	}
}
