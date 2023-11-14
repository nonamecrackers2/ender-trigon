/*
 * Copyright 2022 nonamecrackers2

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package nonamecrackers2.endertrigon.client.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
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
