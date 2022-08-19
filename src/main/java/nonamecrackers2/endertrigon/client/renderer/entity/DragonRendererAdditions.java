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
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.EnderDragonHead;
import nonamecrackers2.endertrigon.common.util.EnderDragonExtension;

public class DragonRendererAdditions
{
	@SuppressWarnings("deprecation")
	public static void renderExtra(PoseStack stack, VertexConsumer consumer, int packedLight, int overlayTexture, float r, float g, float b, float a, EnderDragon dragon, float partialTicks, ModelPart[] heads, ModelPart[] jaws, ModelPart[] necks)
	{
		stack.pushPose();
		float f = Mth.lerp(partialTicks, dragon.oFlapTime, dragon.flapTime);
		float f1 = (float) (Math.sin((double) (f * ((float) Math.PI * 2F) - 1.0F)) + 1.0D);
		f1 = (f1 * f1 + f1 * 2.0F) * 0.05F;
		stack.translate(0.0D, (double) (f1 - 2.0F), -3.0D);
		stack.mulPose(Vector3f.XP.rotationDegrees(f1 * 2.0F));
		float f8 = f * ((float) Math.PI * 2F);

		EnderDragonHead[] otherHeads = ((EnderDragonExtension)dragon).getOtherHeads();
		
		for (int j = 0; j < otherHeads.length; j++)
		{
			int latencyOffset = otherHeads[j].getLatencyOffset();
			
			double[] adouble = dragon.getLatencyPos(6 + latencyOffset, partialTicks);
			float f6 = Mth.rotWrap(dragon.getLatencyPos(5 + latencyOffset, partialTicks)[0] - dragon.getLatencyPos(10, partialTicks)[0]);
			float f7 = Mth.rotWrap(dragon.getLatencyPos(5 + latencyOffset, partialTicks)[0] + (double) (f6 / 2.0F));
			
			jaws[j].xRot = (float)(Math.sin((double)(f * ((float)Math.PI * 2F)) + 400.0D * (j + 1)) + 1.0D) * 0.2F;
			float x = 0.0F;
			float y = 20.0F;
			float z = -12.0F;

			for (int i = 0; i < 5; i++)
			{
				double[] adouble1 = dragon.getLatencyPos(5 + latencyOffset - i, partialTicks);
				float f9 = (float) Math.cos((double) ((float) i * 0.45F + f8)) * 0.15F;
				necks[j].yRot = Mth.rotWrap(adouble1[0] - adouble[0] + otherHeads[j].getRenderYRotOffset()) * ((float) Math.PI / 180F) * 1.5F;
				necks[j].xRot = f9 + (dragon.getHeadPartYOffset(i, adouble, adouble1) + otherHeads[j].getRenderXRotOffset()) * ((float) Math.PI / 180F) * 1.5F * 3.0F;
				necks[j].zRot = -Mth.rotWrap(adouble1[0] - (double) f7) * ((float) Math.PI / 180F) * 1.5F;
				necks[j].y = y;
				necks[j].z = z;
				necks[j].x = x;
				y += Mth.sin(necks[j].xRot) * 10.0F;
				z -= Mth.cos(necks[j].yRot) * Mth.cos(necks[j].xRot) * 10.0F;
				x -= Mth.sin(necks[j].yRot) * Mth.cos(necks[j].xRot) * 10.0F;
				necks[j].render(stack, consumer, packedLight, overlayTexture, 1.0F, 1.0F, 1.0F, a);
			}
			
			heads[j].y = y;
			heads[j].z = z;
			heads[j].x = x;
			double[] adouble2 = dragon.getLatencyPos(0 + latencyOffset , partialTicks);
			heads[j].yRot = Mth.rotWrap(adouble2[0] - adouble[0]) * ((float) Math.PI / 180F);
			heads[j].xRot = Mth.rotWrap((double) dragon.getHeadPartYOffset(6, adouble, adouble2)) * ((float) Math.PI / 180F) * 1.5F * 5.0F;
			heads[j].zRot = -Mth.rotWrap(adouble2[0] - (double) f7) * ((float) Math.PI / 180F);
			heads[j].render(stack, consumer, packedLight, overlayTexture, 1.0F, 1.0F, 1.0F, a);
		}

		stack.popPose();
	}
}
