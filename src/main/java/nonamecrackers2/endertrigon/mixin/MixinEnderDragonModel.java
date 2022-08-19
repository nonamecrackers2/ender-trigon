package nonamecrackers2.endertrigon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import nonamecrackers2.endertrigon.client.renderer.entity.DragonRendererAdditions;

@Mixin(EnderDragonRenderer.DragonModel.class)
public abstract class MixinEnderDragonModel
{
	private ModelPart[] heads;
	private ModelPart[] necks;
	private ModelPart[] jaws;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void constructorTail(ModelPart root, CallbackInfo ci)
	{
		this.heads = new ModelPart[] { root.getChild("head1"), root.getChild("head2") };
		this.jaws = new ModelPart[] { this.heads[0].getChild("jaw"), this.heads[1].getChild("jaw") };
		this.necks = new ModelPart[] { root.getChild("neck1"), root.getChild("neck2") };
	}

	@Inject(method = "renderToBuffer", at = @At("TAIL"))
	public void renderToBufferTail(PoseStack stack, VertexConsumer consumer, int packedLight, int overlayTexture, float r, float g, float b, float a, CallbackInfo ci)
	{
		DragonRendererAdditions.renderExtra(stack, consumer, packedLight, overlayTexture, r, g, b, a, this.getEntity(), this.getPartialTicks(), this.heads, this.jaws, this.necks);
	}

	@Accessor
	public abstract EnderDragon getEntity();

	@Accessor("a")
	public abstract float getPartialTicks();
}
