package nonamecrackers2.endertrigon.common.entity.boss.enderdragon.phase;

import javax.annotation.Nullable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.util.EnderDragonHelper;

public class DragonCarryPlayerPhase extends AbstractDragonPhaseInstance
{
	private @Nullable LivingEntity target;
	
	public DragonCarryPlayerPhase(EnderDragon dragon)
	{
		super(dragon);
	}
	
	@Override
	public void doServerTick()
	{
		if (this.target != null && this.target.getVehicle() == this.dragon)
		{
			Vec3 pos = Vec3.atBottomCenterOf(this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).above(20));
			EnderDragonHelper.moveUnrestrictedY(this.dragon, pos, this.getTurnSpeed(), 0.3F);
			if (this.dragon.position().distanceTo(pos) < 10 && this.dragon.getRandom().nextInt(2) == 0)
			{
				this.target.stopRiding();
				this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
			}
		}
		else
		{
			this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
		}
	}
	
	public void setTarget(@Nullable LivingEntity entity)
	{
		this.target = entity;
	}
	
	@Override
	public void begin()
	{
		this.target = null;
	}
	
	@Override
	public EnderDragonPhase<DragonCarryPlayerPhase> getPhase()
	{
		return EnderTrigonMod.CARRY_PLAYER;
	}
}
