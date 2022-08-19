package nonamecrackers2.endertrigon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

@Mixin(EnderDragon.class)
public interface IMixinEnderDragon
{
	@Invoker
	public abstract void callTickPart(EnderDragonPart part, double x, double y, double z);
	
	@Invoker
	public abstract float callGetHeadYOffset();
}
