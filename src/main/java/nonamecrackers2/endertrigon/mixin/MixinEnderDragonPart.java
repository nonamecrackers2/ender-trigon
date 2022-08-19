package nonamecrackers2.endertrigon.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.entity.PartEntity;
import nonamecrackers2.endertrigon.EnderTrigonMod;

@Mixin(EnderDragonPart.class)
public abstract class MixinEnderDragonPart extends PartEntity<EnderDragon>
{
	private MixinEnderDragonPart(EnderDragon parent)
	{
		super(parent);
	}
	
	@Override
	public boolean ignoreExplosion()
	{
		return this.getParent().getPhaseManager().getCurrentPhase().getPhase() == EnderTrigonMod.CRASH_PLAYER;
	}
}
