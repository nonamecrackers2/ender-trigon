package nonamecrackers2.endertrigon.common.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;

public class BabyEnderDragonMoveAnchorGoal extends BabyEnderDragonMoveTargetGoal
{
	private float angle;
	private float distance;
	private float height;
	private float clockwise;
	
	public BabyEnderDragonMoveAnchorGoal(BabyEnderDragon dragon)
	{
		super(dragon);
	}
	
	@Override
	public boolean canUse()
	{
		return this.dragon.getTarget() == null || this.dragon.getPhase() == BabyEnderDragon.AttackPhase.CIRCLE;
	}
	
	@Override
	public void start()
	{
		this.distance = 5.0F + this.dragon.getRandom().nextFloat() * 10.0F;
		this.height = -4.0F + this.dragon.getRandom().nextFloat() * 8.0F;
		this.clockwise = this.dragon.getRandom().nextBoolean() ? 1.0F : -1.0F;
		this.selectNext();
	}
	
	@Override
	public void tick()
	{
		if (this.dragon.getRandom().nextInt(this.adjustedTickDelay(350)) == 0)
			this.height = -4.0F + this.dragon.getRandom().nextFloat() * 8.0F;
		
		if (this.dragon.getRandom().nextInt(this.adjustedTickDelay(250)) == 0)
		{
			this.distance++;
			if (this.distance > 15.0F)
			{
				this.distance = 5.0F;
				this.clockwise = -this.clockwise;
			}
		}
		
		if (this.dragon.getRandom().nextInt(this.adjustedTickDelay(450)) == 0)
		{
			this.angle = this.dragon.getRandom().nextFloat() * 2.0F * (float)Math.PI;
			this.selectNext();
		}
		
		if (this.touchingTarget())
			this.selectNext();
		
		if (this.dragon.getMoveTarget().y < this.dragon.getY() && !this.dragon.level.isEmptyBlock(this.dragon.blockPosition().below()))
		{
			this.height = Math.max(1.0F, this.height);
			this.selectNext();
		}
		
		if (this.dragon.getMoveTarget().y > this.dragon.getY() && !this.dragon.level.isEmptyBlock(this.dragon.blockPosition().above()))
		{
			this.height =  Math.min(-1.0F, this.height);
			this.selectNext();
		}
		
		if (this.dragon.horizontalCollision && this.dragon.getRandom().nextInt(3) == 0)
			this.selectNext();
	}
	
	private void selectNext()
	{
		if (BlockPos.ZERO.equals(this.dragon.getAnchor()))
			this.dragon.setAnchor(this.dragon.blockPosition());
		
		this.angle += this.clockwise * 15.0F * ((float)Math.PI / 180.0F);
		this.dragon.setMoveTarget(Vec3.atLowerCornerOf(this.dragon.getAnchor()).add(this.distance * Mth.cos(this.angle), -4.0D + this.height, this.distance * Mth.sin(this.angle)));
	}
}
