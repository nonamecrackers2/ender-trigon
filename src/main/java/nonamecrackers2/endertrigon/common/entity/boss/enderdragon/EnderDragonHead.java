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

package nonamecrackers2.endertrigon.common.entity.boss.enderdragon;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import nonamecrackers2.endertrigon.mixin.IMixinEnderDragon;

public class EnderDragonHead
{
	public final EnderDragon dragon;
	public final EnderDragonPart head;
	public final EnderDragonPart neck;
	private float renderYRotOffset;
	private float renderXRotOffset;
	private float yRotOffset;
	private float xOffset;
	private int latencyOffset;
	
	public EnderDragonHead(EnderDragon dragon, EnderDragonPart head, EnderDragonPart neck, float renderYRotOffset, float renderXRotOffset, float yRotOffset, float xOffset, int latencyOffset)
	{
		this.dragon = dragon;
		this.head = head;
		this.neck = neck;
		this.renderYRotOffset = renderYRotOffset;
		this.renderXRotOffset = renderXRotOffset;
		this.yRotOffset = yRotOffset;
		this.xOffset = xOffset;
		this.latencyOffset = latencyOffset;
	}
	
	public void tick()
	{
		if (!this.dragon.isDeadOrDying() && !this.dragon.isNoAi())
		{
			float latency = (float) (this.dragon.getLatencyPos(5 + this.latencyOffset, 1.0F)[1] - this.dragon.getLatencyPos(10 + this.latencyOffset, 1.0F)[1]) * 10.0F * ((float) Math.PI / 180F);
			float cosLatency = Mth.cos(latency);
			float sinLatency = Mth.sin(latency);
			float sin = Mth.sin((this.dragon.getYRot() + this.yRotOffset) * ((float) Math.PI / 180F) - this.dragon.yRotA * 0.01F);
			float cos = Mth.cos((this.dragon.getYRot() + this.yRotOffset) * ((float)Math.PI / 180F) - this.dragon.yRotA * 0.01F);
			float offset = this.getHeadYOffset();
			float distance = 6.5F;
			this.tickPart(this.head, (double)(sin * distance * cosLatency), (double)(offset + this.xOffset + sinLatency * 6.5F), (double)(-cos * distance * cosLatency));
			this.tickPart(this.neck, (double)(sin * (distance - 1.0F) * cosLatency), (double)(offset + this.xOffset + sinLatency * 5.5F), (double)(-cos * (distance - 1.0F) * cosLatency));
		}
	}
	
	public float getRenderYRotOffset()
	{
		return this.renderYRotOffset;
	}
	
	public float getRenderXRotOffset()
	{
		return this.renderXRotOffset;
	}
	
	public int getLatencyOffset()
	{
		return this.latencyOffset;
	}
	
	private float getHeadYOffset()
	{
		return ((IMixinEnderDragon)this.dragon).callGetHeadYOffset();
	}
	
	private void tickPart(EnderDragonPart part, double x, double y, double z)
	{
		((IMixinEnderDragon)this.dragon).callTickPart(part, x, y, z);
	}
}
