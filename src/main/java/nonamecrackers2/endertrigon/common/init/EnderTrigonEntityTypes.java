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

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;
import nonamecrackers2.endertrigon.common.entity.DragonFlame;

public class EnderTrigonEntityTypes
{
	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EnderTrigonMod.MODID);
	
	public static final RegistryObject<EntityType<DragonFlame>> DRAGON_FLAME = register("dragon_flame", EntityType.Builder.<DragonFlame>of(DragonFlame::new, MobCategory.MISC).sized(1.2F, 1.2F).clientTrackingRange(4).updateInterval(10).fireImmune());
	public static final RegistryObject<EntityType<BabyEnderDragon>> BABY_ENDER_DRAGON = register("baby_ender_dragon", EntityType.Builder.<BabyEnderDragon>of(BabyEnderDragon::new, MobCategory.MONSTER).sized(1.8F, 0.4F).clientTrackingRange(8).fireImmune());
	
	private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, EntityType.Builder<T> builder)
	{
		return ENTITY_TYPES.register(id, () -> builder.build(EnderTrigonMod.id(id).toString()));
	}
	
	public static void register(IEventBus modBus)
	{
		ENTITY_TYPES.register(modBus);
	}
	
	public static void registerAttributes(EntityAttributeCreationEvent event)
	{
		event.put(BABY_ENDER_DRAGON.get(), BabyEnderDragon.createAttributes().build());
	}
}
