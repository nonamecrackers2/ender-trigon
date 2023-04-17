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

package nonamecrackers2.endertrigon.client.renderer.entity.model;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;

public class BabyEnderDragonModel extends EntityModel<BabyEnderDragon>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EnderTrigonMod.id("baby_ender_dragon"), "main");
	private final ModelPart head;
	private final ModelPart neck;
	private final ModelPart[] neckPieces;
	private final ModelPart jaw;
	private final ModelPart body;
	private final ModelPart leftWing;
	private final ModelPart leftWingEnd;
	private final ModelPart rightWing;
	private final ModelPart rightWingEnd;
	private final ModelPart tail;
	private final ModelPart[] tailPieces;
	private float partialTicks;
	private @Nullable BabyEnderDragon dragon;

	public BabyEnderDragonModel(ModelPart root) 
	{
		this.body = root.getChild("body");
		this.neck = this.body.getChild("neck");
		this.neckPieces = getPieces(this.neck, "neckPiece", 1);
		this.head = this.neckPieces[this.neckPieces.length - 1].getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.leftWing = this.body.getChild("leftWing");
		this.leftWingEnd  = this.leftWing.getChild("leftEnd");
		this.rightWing = this.body.getChild("rightWing");
		this.rightWingEnd  = this.rightWing.getChild("rightEnd");
		this.tail = this.body.getChild("tail");
		this.tailPieces = getPieces(this.tail, "tailPiece", 1);
	}
	
	private static ModelPart[] getPieces(ModelPart root, String child, int start)
	{
		int i = start;
		List<ModelPart> pieces = Lists.newArrayList();
		ModelPart previous = root;
		while(previous.hasChild(child + i))
		{
			ModelPart part = previous.getChild(child + i);
			pieces.add(part);
			previous = part;
			i++;
		}
		return pieces.toArray(ModelPart[]::new);
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -4.0F, 14.0F, 6.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(48, 3).addBox(-0.5F, -5.0F, 15.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(47, 33).addBox(-0.5F, -5.0F, 18.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 0).addBox(-0.5F, -5.0F, 21.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, -16.0F));

		body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(6, 26).addBox(-7.5F, -0.5F, -0.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -1.5F, 15.5F, -0.3491F, 0.0F, 0.0F));

		body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 46).addBox(-0.5F, -0.4793F, 0.1359F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(46, 46).addBox(-7.5F, -0.4793F, 0.1359F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -0.3689F, 18.6683F, -1.0036F, 0.0F, 0.0F));

		body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, -0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(-6.5F, -1.5F, -0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.5F, 22.5F, -0.3491F, 0.0F, 0.0F));

		body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -0.9793F, -0.3641F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(6, 21).addBox(-6.0F, -0.9793F, -0.3641F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -0.3689F, 25.6683F, -0.6545F, 0.0F, 0.0F));

		body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 46).addBox(-1.0F, -0.5599F, -0.1006F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 46).addBox(-7.0F, -0.5599F, -0.1006F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.573F, 28.888F, -0.3054F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 14.0F));

		PartDefinition neckPiece1 = neck.addOrReplaceChild("neckPiece1", CubeListBuilder.create().texOffs(44, 36).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition neckPiece2 = neckPiece1.addOrReplaceChild("neckPiece2", CubeListBuilder.create().texOffs(30, 45).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition neckPiece3 = neckPiece2.addOrReplaceChild("neckPiece3", CubeListBuilder.create().texOffs(38, 45).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 20).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition neckPiece4 = neckPiece3.addOrReplaceChild("neckPiece4", CubeListBuilder.create().texOffs(0, 46).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition head = neckPiece4.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, -8.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 32).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(45, 42).addBox(-2.0F, -3.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(45, 29).addBox(1.0F, -3.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -0.5F, -3.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -5.0F));

		PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create(), PartPose.offset(3.0F, -4.0F, 15.0F));

		leftWing.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 8.0F, 0.0F, -1.5708F, 0.0F));

		leftWing.addOrReplaceChild("leftEnd", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(0.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(16.0F, 0.0F, 8.0F));

		PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create(), PartPose.offset(-3.0F, -4.0F, 15.0F));

		rightWing.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 8.0F, 0.0F, 1.5708F, 0.0F));

		rightWing.addOrReplaceChild("rightEnd", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, 0.0F, 8.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 24.0F));

		PartDefinition tailPiece1 = tail.addOrReplaceChild("tailPiece1", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tailPiece2 = tailPiece1.addOrReplaceChild("tailPiece2", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tailPiece3 = tailPiece2.addOrReplaceChild("tailPiece3", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tailPiece4 = tailPiece3.addOrReplaceChild("tailPiece4", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tailPiece5 = tailPiece4.addOrReplaceChild("tailPiece5", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tailPiece6 = tailPiece5.addOrReplaceChild("tailPiece6", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tailPiece7 = tailPiece6.addOrReplaceChild("tailPiece7", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tailPiece8 = tailPiece7.addOrReplaceChild("tailPiece8", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tailPiece9 = tailPiece8.addOrReplaceChild("tailPiece9", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		tailPiece9.addOrReplaceChild("tailPiece10", CubeListBuilder.create().texOffs(11, 16).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
	@Override
	public void prepareMobModel(BabyEnderDragon entity, float p_102615_, float p_102616_, float partialTicks)
	{
		this.partialTicks = partialTicks;
		this.dragon = entity;
	}

	@Override
	public void setupAnim(BabyEnderDragon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		float flap = Mth.lerp(this.partialTicks, this.dragon.oFlapTime, this.dragon.flapTime);
		this.jaw.xRot = (float)(Math.sin(flap * ((float)Math.PI * 2.0F)) + 1.0D) * 0.2F;
		double[] baseLatency = entity.getLatency(6, this.partialTicks);
		float flapPi = flap * ((float)Math.PI * 2.0F);
		float roll = (float) Mth.wrapDegrees(entity.getLatency(5, this.partialTicks)[0] + ((Mth.wrapDegrees(entity.getLatency(5, this.partialTicks)[0] - entity.getLatency(10, this.partialTicks)[0])) / 2.0F));
		
		for (int i = 0; i < this.neckPieces.length; i++)
		{
			float prevY = 0.0F;
			float prevX = 0.0F;
			float prevZ = 0.0F;
			if (i > 0)
			{
				ModelPart prev = this.neckPieces[i - 1];
				prevY = prev.yRot;
				prevX = prev.xRot;
				prevZ = prev.zRot;
			}
			ModelPart piece = this.neckPieces[i];
			double[] latency = entity.getLatency(this.neckPieces.length - i, this.partialTicks);
			float xOffset = Mth.cos(i * 0.45F + flapPi) * 0.15F;
			piece.yRot = (float) Mth.wrapDegrees(latency[0] - baseLatency[0]) * ((float)Math.PI / 180.0F);
			piece.xRot = (xOffset + (float)baseLatency[1] - (float)latency[1]) * 0.25F;
			piece.zRot = (float) -Mth.wrapDegrees(latency[0] - (double)roll) * ((float)Math.PI / 180.0F);
			piece.yRot -= prevY;
			piece.xRot -= prevX;
			piece.zRot -= prevZ;
		}
		
		double[] headLatency = entity.getLatency(0, this.partialTicks);
		this.head.yRot = (float) Mth.wrapDegrees(headLatency[0] - baseLatency[0]) * ((float)Math.PI / 180.0F);
		this.head.xRot = 0.0F;
		this.head.zRot = (float) -Mth.wrapDegrees(headLatency[0] - (double)roll) * ((float)Math.PI / 180.0F);
		
		this.leftWing.xRot = 0.125F - Mth.cos(flapPi) * 0.2F;
		this.leftWing.yRot = -0.25F;
		this.leftWing.zRot = -(Mth.sin(flapPi) + 0.125F) * 0.8F;
		this.leftWingEnd.zRot = (Mth.sin(flapPi + 2.0F) + 0.5F) * 0.75F;
		this.rightWing.xRot = this.leftWing.xRot;
		this.rightWing.yRot = -this.leftWing.yRot;
		this.rightWing.zRot = -this.leftWing.zRot;
		this.rightWingEnd.zRot = -this.leftWingEnd.zRot;
		
		for (int i = 0; i < this.tailPieces.length; i++)
		{
			float prevY = 0.0F;
			float prevX = 0.0F;
			float prevZ = 0.0F;
			if (i > 0)
			{
				ModelPart prev = this.tailPieces[i - 1];
				prevY = prev.yRot;
				prevX = prev.xRot;
				prevZ = prev.zRot;
			}
			ModelPart piece = this.tailPieces[i];
			double[] latency = entity.getLatency(12 + i, this.partialTicks);
			float xOffset = Mth.cos(i * 0.45F + flapPi) * 0.05F;
			piece.yRot = (float) (Mth.wrapDegrees(latency[0] - baseLatency[0]) * 0.25F) * ((float)Math.PI / 180.0F);
			piece.xRot = -(xOffset + (float)(baseLatency[1] - (float)latency[1]) * ((float)Math.PI / 180F) * 2.5F);
			piece.zRot = (float) Mth.wrapDegrees(latency[0] - (double)roll) * ((float)Math.PI / 180.0F) * 0.2F;
			piece.yRot -= prevY;
			piece.xRot -= prevX;
			piece.zRot -= prevZ;
		}
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		this.body.render(stack, consumer, packedLight, packedOverlay);
	}
}
