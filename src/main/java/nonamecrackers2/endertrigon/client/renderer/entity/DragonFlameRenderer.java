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

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.entity.DragonFlame;

public class DragonFlameRenderer extends EntityRenderer<DragonFlame>
{
	private static final ResourceLocation TEXTURE_LOCATION = EnderTrigonMod.id("textures/entity/dragon_flame.png");
	private static final RenderType CUTOUT = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
	private static final RenderType EMISSIVE = RenderType.eyes(TEXTURE_LOCATION);
	
	public DragonFlameRenderer(EntityRendererProvider.Context context)
	{
		super(context);
	}

	@Override
	protected int getBlockLightLevel(DragonFlame entity, BlockPos pos)
	{
		return 15;
	}
	
	@Override
	public void render(DragonFlame entity, float p_114486_, float p_114487_, PoseStack stack, MultiBufferSource buffer, int p_114490_)
	{
		stack.pushPose();
		stack.scale(2.0F, 2.0F, 2.0F);
		stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		stack.mulPose(Axis.YP.rotationDegrees(180.0F));
		PoseStack.Pose pose = stack.last();
		Matrix4f matrix4f = pose.pose();
		Matrix3f matrix3f = pose.normal();
		VertexConsumer consumer = buffer.getBuffer(CUTOUT);
		render(consumer, matrix4f, matrix3f, p_114490_);
		consumer = buffer.getBuffer(EMISSIVE);
		render(consumer, matrix4f, matrix3f, p_114490_);
		stack.popPose();
		super.render(entity, p_114486_, p_114487_, stack, buffer, p_114490_);
	}
	
	@Override
	public ResourceLocation getTextureLocation(DragonFlame entity)
	{
		return TEXTURE_LOCATION;
	}
	
	private static void render(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int i)
	{
		consumer.vertex(matrix4f, -0.5F, -0.25F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		consumer.vertex(matrix4f, 0.5F, -0.25F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).uv(1.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		consumer.vertex(matrix4f, 0.5F, 0.75F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).uv(1.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
		consumer.vertex(matrix4f, -0.5F, 0.75F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
	}
}
