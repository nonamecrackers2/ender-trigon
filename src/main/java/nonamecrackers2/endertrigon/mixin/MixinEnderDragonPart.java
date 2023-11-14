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

package nonamecrackers2.endertrigon.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.neoforged.neoforge.entity.PartEntity;
import nonamecrackers2.endertrigon.EnderTrigonMod;

@Mixin(EnderDragonPart.class)
public abstract class MixinEnderDragonPart extends PartEntity<EnderDragon>
{
	private MixinEnderDragonPart(EnderDragon parent)
	{
		super(parent);
	}
	
	@Override
	public boolean ignoreExplosion()
	{
		return this.getParent().getPhaseManager().getCurrentPhase().getPhase() == EnderTrigonMod.CRASH_PLAYER;
	}
}
