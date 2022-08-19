package nonamecrackers2.endertrigon.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.common.entity.goal.BabyEnderDragonAttackStrategyGoal;
import nonamecrackers2.endertrigon.common.entity.goal.BabyEnderDragonMoveAnchorGoal;
import nonamecrackers2.endertrigon.common.entity.goal.BabyEnderDragonSweepGoal;

public class BabyEnderDragon extends FlyingMob implements Enemy
{
	public float flapTime;
	public float oFlapTime;
	public final double[][] latencyYs = new double[64][3];
	public int latencyPointer = -1;
	private Vec3 moveTarget = Vec3.ZERO;
	private BlockPos anchor = BlockPos.ZERO;
	private BabyEnderDragon.AttackPhase phase = BabyEnderDragon.AttackPhase.CIRCLE;
	
	public BabyEnderDragon(EntityType<? extends BabyEnderDragon> type, Level level)
	{
		super(type, level);
		this.lookControl = new BabyEnderDragon.DragonLookControl(this);
		this.moveControl = new BabyEnderDragon.DragonMoveControl(this);
	}
	
	@Override
	protected BodyRotationControl createBodyControl()
	{
		return new BabyEnderDragon.DragonBodyControl(this);
	}
	
	public static AttributeSupplier.Builder createAttributes()
	{
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.ATTACK_DAMAGE, 6.0D);
	}
	
	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(1, new BabyEnderDragonAttackStrategyGoal(this));
		this.goalSelector.addGoal(2, new BabyEnderDragonSweepGoal(this));
		this.goalSelector.addGoal(3, new BabyEnderDragonMoveAnchorGoal(this));
		this.targetSelector.addGoal(1, new BabyEnderDragon.TargetGoal(this));
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag)
	{
		super.addAdditionalSaveData(tag);
		tag.put("Anchor", NbtUtils.writeBlockPos(this.getAnchor()));
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag tag)
	{
		super.readAdditionalSaveData(tag);
		this.setAnchor(NbtUtils.readBlockPos(tag.getCompound("Anchor")));
	}
	
	@Override
	public boolean isFlapping()
	{
		float f = Mth.cos(this.flapTime * ((float) Math.PI * 2F));
		float f1 = Mth.cos(this.oFlapTime * ((float) Math.PI * 2F));
		return f1 <= -0.3F && f >= -0.3F;
	}
	
	@Override
	public void onFlap()
	{
		if (this.level.isClientSide && !this.isSilent())
			this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENDER_DRAGON_FLAP, this.getSoundSource(), 5.0F, 1.7F + this.random.nextFloat() * 0.3F, false);
	}
	
	public double[] getLatency(int pointer, float partialTicks)
	{
		if (this.isDeadOrDying())
			partialTicks = 0.0F;

		partialTicks = 1.0F - partialTicks;
		int i = this.latencyPointer - pointer & 63;
		int j = this.latencyPointer - pointer - 1 & 63;
		double[] adouble = new double[3];
		double d0 = this.latencyYs[i][0];
		double d1 = Mth.wrapDegrees(this.latencyYs[j][0] - d0);
		adouble[0] = d0 + d1 * (double)partialTicks;
		d0 = this.latencyYs[i][1];
		d1 = this.latencyYs[j][1] - d0;
		adouble[1] = d0 + d1 * (double)partialTicks;
		adouble[2] = Mth.lerp((double)partialTicks, this.latencyYs[i][2], this.latencyYs[j][2]);
		return adouble;
	}
	
	@Override
	public void aiStep()
	{
		super.aiStep();
		if (!this.isDeadOrDying())
		{
			if (this.latencyPointer < 0)
			{
				for (int i = 0; i < this.latencyYs.length; i++)
				{
					this.latencyYs[i][0] = this.getYRot();
					this.latencyYs[i][1] = this.getY();
				}
			}
			
			if (++this.latencyPointer == this.latencyYs.length)
				this.latencyPointer = 0;
			
			this.latencyYs[this.latencyPointer][0] = this.getYRot();
			this.latencyYs[this.latencyPointer][1] = this.getY();
		}
		
		this.oFlapTime = this.flapTime;
		Vec3 delta = this.getDeltaMovement();
		float flapPower = 0.2F / ((float)delta.horizontalDistance() * 2.0F + 1.0F);
		this.flapTime += flapPower;
		if (this.level.isClientSide)
			this.processFlappingMovement();
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENDER_DRAGON_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENDER_DRAGON_HURT;
	}
	
	@Override
	public float getVoicePitch()
	{
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F;
	}
	
	public Vec3 getMoveTarget()
	{
		return this.moveTarget;
	}
	
	public void setMoveTarget(Vec3 moveTarget)
	{
		this.moveTarget = moveTarget;
	}
	
	public BlockPos getAnchor()
	{
		return this.anchor;
	}
	
	public void setAnchor(BlockPos anchor)
	{
		this.anchor = anchor;
	}
	
	public BabyEnderDragon.AttackPhase getPhase()
	{
		return this.phase;
	}
	
	public void setPhase(BabyEnderDragon.AttackPhase phase)
	{
		this.phase = phase;
	}
	
	protected boolean shouldDespawnInPeaceful()
	{
		return true;
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType type, SpawnGroupData groupData, CompoundTag data)
	{
		this.setAnchor(this.blockPosition().above(5));
		return super.finalizeSpawn(level, difficulty, type, groupData, data);
	}
	
	private static class DragonLookControl extends LookControl
	{
		public DragonLookControl(BabyEnderDragon dragon)
		{
			super(dragon);
		}
		
		@Override
		public void tick() {}
	}
	
	private static class DragonMoveControl extends MoveControl
	{
		private final BabyEnderDragon dragon;
		private float speed;
		
		public DragonMoveControl(BabyEnderDragon dragon)
		{
			super(dragon);
			this.dragon = dragon;
		}
		
		@Override
		public void tick()
		{
			double d0 = this.dragon.moveTarget.x - this.dragon.getX();
			double d1 = this.dragon.moveTarget.y - this.dragon.getY();
			double d2 = this.dragon.moveTarget.z - this.dragon.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			if (Math.abs(d3) > (double) 1.0E-5F)
			{
				double d4 = 1.0D - Math.abs(d1 * (double) 0.7F) / d3;
				d0 *= d4;
				d2 *= d4;
				d3 = Math.sqrt(d0 * d0 + d2 * d2);
				double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
				float f = this.dragon.getYRot();
				float f1 = (float) Mth.atan2(d2, d0);
				float f2 = Mth.wrapDegrees(this.dragon.getYRot() + 90.0F) + (this.dragon.horizontalCollision ? 25.0F : 0.0F);
				float f3 = Mth.wrapDegrees(f1 * (180F / (float) Math.PI));
				this.dragon.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
				this.dragon.yBodyRot = this.dragon.getYRot();
				if (Mth.degreesDifferenceAbs(f, this.dragon.getYRot()) < 3.0F)
					this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
				else
					this.speed = Mth.approach(this.speed, 0.8F, 0.25F);

				float f4 = (float) (-(Mth.atan2(-d1, d3) * (double) (180F / (float) Math.PI)));
				this.dragon.setXRot(f4);
				float f5 = this.dragon.getYRot() + 90.0F;
				double d6 = (double) (this.speed * Mth.cos(f5 * ((float) Math.PI / 180F))) * Math.abs(d0 / d5);
				double d7 = (double) (this.speed * Mth.sin(f5 * ((float) Math.PI / 180F))) * Math.abs(d2 / d5);
				double d8 = (double) (this.speed * Mth.sin(f4 * ((float) Math.PI / 180F))) * Math.abs(d1 / d5);
				Vec3 vec3 = this.dragon.getDeltaMovement();
				this.dragon.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
			}
		}
	}
	
	private static class DragonBodyControl extends BodyRotationControl
	{
		private final BabyEnderDragon dragon;
		
		public DragonBodyControl(BabyEnderDragon dragon)
		{
			super(dragon);
			this.dragon = dragon;
		}
		
		@Override
		public void clientTick()
		{
			this.dragon.yHeadRot = this.dragon.yBodyRot;
			this.dragon.yBodyRot = this.dragon.getYRot();
		}
	}
	
	public static enum AttackPhase
	{
		CIRCLE,
		SWOOP
	}
	
	private static class TargetGoal extends NearestAttackableTargetGoal<Player>
	{
		public TargetGoal(BabyEnderDragon dragon)
		{
			super(dragon, Player.class, false);
		}
		
		@Override
		protected AABB getTargetSearchArea(double reach)
		{
			return this.mob.getBoundingBox().inflate(reach);
		}
	}
}
