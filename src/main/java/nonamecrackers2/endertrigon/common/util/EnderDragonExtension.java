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

package nonamecrackers2.endertrigon.common.util;

import nonamecrackers2.endertrigon.common.entity.boss.enderdragon.EnderDragonHead;

public interface EnderDragonExtension
{
	public static final int SECOND_HEAD = 0;
	public static final int THIRD_HEAD = 1;
	
	EnderDragonHead[] getOtherHeads();
}
