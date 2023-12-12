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

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.block.BabyDragonEgg;

public class EnderTrigonBlocks
{	
	private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EnderTrigonMod.MODID);
	
	public static final DeferredBlock<BabyDragonEgg> BABY_DRAGON_EGG = BLOCKS.register("baby_dragon_egg", () -> new BabyDragonEgg(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 9.0F).lightLevel((state) -> 1).noOcclusion()));
	
	public static void register(IEventBus modBus)
	{
		BLOCKS.register(modBus);
	}
}
