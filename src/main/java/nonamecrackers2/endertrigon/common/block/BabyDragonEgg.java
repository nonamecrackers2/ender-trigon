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

package nonamecrackers2.endertrigon.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nonamecrackers2.endertrigon.common.block.entity.BabyDragonEggBlockEntity;

public class BabyDragonEgg extends DragonEggBlock implements EntityBlock
{
	protected static final VoxelShape SHAPE;
	
	static 
	{
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.0625, 0.25, 0.75, 0.1875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.1875, 0.8125, 0.4375, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.25, 0.75, 0.625, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.625, 0.3125, 0.6875, 0.75, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);
		SHAPE = shape;
	}
	
	public BabyDragonEgg(BlockBehaviour.Properties properties)
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
	{
		return SHAPE;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new BabyDragonEggBlockEntity(pos, state);
	}
	
	@Override
	public void onLand(Level level, BlockPos pos, BlockState state, BlockState state1, FallingBlockEntity falling)
	{
		BlockEntity entity = level.getBlockEntity(pos);
		if (entity instanceof BabyDragonEggBlockEntity eggEntity)
		{
			RandomSource random = RandomSource.create();
			eggEntity.setTimeTillSpawn(30 + random.nextInt(20));
		}
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
	{
		return castTicker(BabyDragonEggBlockEntity::tick);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T extends BlockEntity> BlockEntityTicker<T> castTicker(BlockEntityTicker<BabyDragonEggBlockEntity> ticker)
	{
		return (BlockEntityTicker<T>)ticker;
	}
}
