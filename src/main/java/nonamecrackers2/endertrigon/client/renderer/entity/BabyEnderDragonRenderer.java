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

package nonamecrackers2.endertrigon.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.client.renderer.entity.model.BabyEnderDragonModel;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;

public class BabyEnderDragonRenderer extends MobRenderer<BabyEnderDragon, BabyEnderDragonModel>
{
	private static final ResourceLocation TEXTURE = EnderTrigonMod.id("textures/entity/baby_ender_dragon.png");
	private static final ResourceLocation EYES_TEXTURE = EnderTrigonMod.id("textures/entity/baby_ender_dragon_eyes.png");
	private static final RenderType EYES = RenderType.eyes(EYES_TEXTURE);
	
	public BabyEnderDragonRenderer(EntityRendererProvider.Context context)
	{
		super(context, new BabyEnderDragonModel(context.bakeLayer(BabyEnderDragonModel.LAYER_LOCATION)), 0.5F);
		this.addLayer(new EyesLayer<>(this)
		{
			@Override
			public RenderType renderType()
			{
				return EYES;
			}
		});
	}
	
	@Override
	protected void setupRotations(BabyEnderDragon dragon, PoseStack stack, float p_115319_, float p_115320_, float partialTicks)
	{
		float f = (float)dragon.getLatency(7, partialTicks)[0];
		float f1 = (float)(dragon.getLatency(5, partialTicks)[1] - dragon.getLatency(10, partialTicks)[1]);
		stack.mulPose(Axis.YP.rotationDegrees(-f + 180.0F));
		stack.mulPose(Axis.XP.rotationDegrees(f1 * 10.0F));
		stack.mulPose(Axis.ZP.rotationDegrees(Mth.wrapDegrees(-f1) * 10.0F));
	}
	
	@Override
	public ResourceLocation getTextureLocation(BabyEnderDragon entity)
	{
		return TEXTURE;
	}
}
