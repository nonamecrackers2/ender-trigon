package nonamecrackers2.endertrigon.common.init;

import javax.annotation.Nullable;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import nonamecrackers2.endertrigon.EnderTrigonMod;

public class EnderTrigonDamageTypes
{
	public static final ResourceKey<DamageType> DRAGON_FLAME = create("dragon_flame");
			
	private static ResourceKey<DamageType> create(String id)
	{
		return ResourceKey.create(Registries.DAMAGE_TYPE, EnderTrigonMod.id(id));
	}
	
	public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key)
	{
		return new DamageSource(access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
	}
	
	public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key, @Nullable Entity entity)
	{
		return new DamageSource(access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), entity);
	}
	
	public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key, @Nullable Entity entity, @Nullable Entity entity2)
	{
		return new DamageSource(access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), entity, entity2);
	}
	
	public static DamageSource dragonFlame(Entity entity, Entity target)
	{
		return source(entity.level().registryAccess(), DRAGON_FLAME, entity, target);
	}
}
