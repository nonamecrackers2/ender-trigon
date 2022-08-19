package nonamecrackers2.endertrigon.common.util;

import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.EnderDragonHead;

public interface EnderDragonExtension
{
	public static final int SECOND_HEAD = 0;
	public static final int THIRD_HEAD = 1;
	
	EnderDragonHead[] getOtherHeads();
}
