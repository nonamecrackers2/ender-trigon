package nonamecrackers2.endertrigon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;

@Mixin(EnderDragonPhase.class)
public interface IMixinEnderDragonPhase
{
	@Invoker
	public static <T extends DragonPhaseInstance> EnderDragonPhase<T> callCreate(Class<T> instanceClass, String name)
	{
		throw new AssertionError();
	}
}
