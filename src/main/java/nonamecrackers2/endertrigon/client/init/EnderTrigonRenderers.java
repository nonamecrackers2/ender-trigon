package nonamecrackers2.endertrigon.client.init;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import nonamecrackers2.endertrigon.client.renderer.entity.BabyEnderDragonRenderer;
import nonamecrackers2.endertrigon.client.renderer.entity.DragonFlameRenderer;
import nonamecrackers2.endertrigon.client.renderer.entity.model.BabyEnderDragonModel;
import nonamecrackers2.endertrigon.common.init.EnderTrigonEntityTypes;

public class EnderTrigonRenderers
{
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerEntityRenderer(EnderTrigonEntityTypes.DRAGON_FLAME.get(), DragonFlameRenderer::new);
		event.registerEntityRenderer(EnderTrigonEntityTypes.BABY_ENDER_DRAGON.get(), BabyEnderDragonRenderer::new);
	}
	
	@SubscribeEvent
	public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(BabyEnderDragonModel.LAYER_LOCATION, BabyEnderDragonModel::createBodyLayer);
	}
}
