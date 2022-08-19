package nonamecrackers2.endertrigon.common.init;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
		return SOUND_EVENTS.register(id, () -> new SoundEvent(EnderTrigonMod.id(id)));
	}
}
