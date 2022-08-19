package nonamecrackers2.endertrigon.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;

@Mixin(DragonFireball.class)
public abstract class MixinDragonFireball extends AbstractHurtingProjectile
{
	private MixinDragonFireball(EntityType<? extends AbstractHurtingProjectile> type, Level level)
	{
		super(type, level);
	}
	
	@Override
	protected boolean ownedBy(Entity entity)
	{
		UUID owner = null;
		if (this.getOwner() != null)
			owner = this.getOwner().getUUID();
		return super.ownedBy(entity) || (entity instanceof PartEntity<?> part && part.getParent().getUUID().equals(owner));
	}
}
