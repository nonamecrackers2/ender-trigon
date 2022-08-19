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

package nonamecrackers2.endertrigon.common.init;

import java.util.function.Supplier;

import com.mojang.datafixers.types.Type;

import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.block.entity.BabyDragonEggBlockEntity;

public class EnderTrigonBlockEntityTypes
{
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EnderTrigonMod.MODID);
	
	public static final RegistryObject<BlockEntityType<BabyDragonEggBlockEntity>> BABY_DRAGON_EGG = register("baby_dragon_egg", () -> BlockEntityType.Builder.of(BabyDragonEggBlockEntity::new, EnderTrigonBlocks.BABY_DRAGON_EGG.get()));
	
	public static void register(IEventBus modBus)
	{
		BLOCK_ENTITY_TYPES.register(modBus);
	}
	
	private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, Supplier<BlockEntityType.Builder<T>> builder)
	{
		return BLOCK_ENTITY_TYPES.register(id, () -> 
		{
			Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, id);
			return builder.get().build(type);
		});
	}
}
