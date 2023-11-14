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

import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import nonamecrackers2.endertrigon.EnderTrigonMod;

public class EnderTrigonSoundEvents
{
	private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EnderTrigonMod.MODID);
	
	public static final RegistryObject<SoundEvent> BABY_DRAGON_EGG_BREAKS = create("baby_dragon_egg_breaks");
	public static final RegistryObject<SoundEvent> ENDER_DRAGON_LAYS_EGG = create("dragon_lays_egg");
	
	public static void register(IEventBus modBus)
	{
		SOUND_EVENTS.register(modBus);
	}
	
	private static RegistryObject<SoundEvent> create(String id)
	{
		return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(EnderTrigonMod.id(id)));
	}
}
