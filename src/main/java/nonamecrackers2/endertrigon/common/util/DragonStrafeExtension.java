package nonamecrackers2.endertrigon.common.util;

public interface DragonStrafeExtension
{
	int getTimesStrafing();
	
	void setTimesStrafing(int amount);
	
	default void countStrafe()
	{
		this.setTimesStrafing(this.getTimesStrafing() + 1);
	}
}
