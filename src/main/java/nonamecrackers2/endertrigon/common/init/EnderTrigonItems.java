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
