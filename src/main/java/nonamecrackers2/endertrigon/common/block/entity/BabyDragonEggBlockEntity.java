package nonamecrackers2.endertrigon.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;
import nonamecrackers2.endertrigon.common.init.EnderTrigonBlockEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonSoundEvents;

public class BabyDragonEggBlockEntity extends BlockEntity
{
	private int timeTillSpawn;
	
	public BabyDragonEggBlockEntity(BlockPos pos, BlockState state)
	{
		super(EnderTrigonBlockEntityTypes.BABY_DRAGON_EGG.get(), pos, state);
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, BabyDragonEggBlockEntity entity)
	{
		if (!level.isClientSide && level instanceof ServerLevel serverLevel)
		{
			if (entity.timeTillSpawn > 0)
			{
				entity.timeTillSpawn--;
				if (entity.timeTillSpawn == 0)
				{
					BabyEnderDragon dragon = EnderTrigonEntityTypes.BABY_ENDER_DRAGON.get().spawn(serverLevel, null, null, null, pos, MobSpawnType.TRIGGERED, true, false);
					dragon.playSound(SoundEvents.ENDER_DRAGON_GROWL, 3.0F, dragon.getVoicePitch());
					level.playSound(null, pos, EnderTrigonSoundEvents.BABY_DRAGON_EGG_BREAKS.get(), SoundSource.BLOCKS, 5.0F, 1.0F);
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
					level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
				}
			}
		}
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.putInt("TimeTillSpawn", this.timeTillSpawn);
	}
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		this.timeTillSpawn = tag.getInt("TimeTillSpawn");
	}
	
	public void setTimeTillSpawn(int time)
	{
		this.timeTillSpawn = time;
	}
}
