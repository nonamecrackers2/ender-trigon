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

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nonamecrackers2.endertrigon.EnderTrigonMod;

public class EnderTrigonItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderTrigonMod.MODID);
	
	public static final RegistryObject<Item> BABY_DRAGON_EGG = ITEMS.register("baby_dragon_egg", () -> new BlockItem(EnderTrigonBlocks.BABY_DRAGON_EGG.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
	
	public static void register(IEventBus modBus)
	{
		ITEMS.register(modBus);
	}
}
