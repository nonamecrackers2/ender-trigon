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
