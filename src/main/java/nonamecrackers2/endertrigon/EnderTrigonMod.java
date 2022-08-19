package nonamecrackers2.endertrigon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nonamecrackers2.endertrigon.client.init.EnderTrigonRenderers;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.phase.DragonCarryPlayerPhase;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.phase.DragonChargeUpPhase;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.phase.DragonCrashPlayerPhase;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.phase.DragonDiveBombPlayerPhase;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.phase.DragonSnatchPlayerPhase;
import nonamecrackers2.endertrigon.common.init.EnderTrigonBlockEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonBlocks;
import nonamecrackers2.endertrigon.common.init.EnderTrigonEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonItems;
import nonamecrackers2.endertrigon.common.init.EnderTrigonSoundEvents;
import nonamecrackers2.endertrigon.mixin.IMixinEnderDragonPhase;

@Mod(EnderTrigonMod.MODID)
public class EnderTrigonMod
{
	public static final String MODID = "endertrigon";
	
	public static final EnderDragonPhase<DragonChargeUpPhase> CHARGE_UP = create(DragonChargeUpPhase.class, "ChargeUp");
	public static final EnderDragonPhase<DragonSnatchPlayerPhase> SNATCH_PLAYER = create(DragonSnatchPlayerPhase.class, "SnatchPlayer");
	public static final EnderDragonPhase<DragonCarryPlayerPhase> CARRY_PLAYER = create(DragonCarryPlayerPhase.class, "CarryPlayer");
	public static final EnderDragonPhase<DragonCrashPlayerPhase> CRASH_PLAYER = create(DragonCrashPlayerPhase.class, "CrashPlayer");
	public static final EnderDragonPhase<DragonDiveBombPlayerPhase> DIVE_BOMB_PLAYER = create(DragonDiveBombPlayerPhase.class, "DiveBombPlayer");
	
	public EnderTrigonMod()
	{
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		EnderTrigonEntityTypes.register(modBus);
		EnderTrigonBlocks.register(modBus);
		EnderTrigonBlockEntityTypes.register(modBus);
		EnderTrigonSoundEvents.register(modBus);
		EnderTrigonItems.register(modBus);
		modBus.addListener(EnderTrigonEntityTypes::registerAttributes);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			modBus.register(EnderTrigonRenderers.class);
		});
	}
	
	public static ResourceLocation id(String path)
	{
		return new ResourceLocation(MODID, path);
	}
	
	private static <T extends DragonPhaseInstance> EnderDragonPhase<T> create(Class<T> instanceClass, String name)
	{
		return IMixinEnderDragonPhase.callCreate(instanceClass, name);
	}
}
