package nonamecrackers2.endertrigon.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.block.BabyDragonEgg;

public class EnderTrigonBlocks
{	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EnderTrigonMod.MODID);
	
	public static final RegistryObject<BabyDragonEgg> BABY_DRAGON_EGG = BLOCKS.register("baby_dragon_egg", () -> new BabyDragonEgg(BlockBehaviour.Properties.of(Material.EGG, MaterialColor.COLOR_BLACK).strength(3.0F, 9.0F).lightLevel((state) -> 1).noOcclusion()));
	
	public static void register(IEventBus modBus)
	{
		BLOCKS.register(modBus);
	}
}
