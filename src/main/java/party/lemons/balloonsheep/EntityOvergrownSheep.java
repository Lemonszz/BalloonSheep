package party.lemons.balloonsheep;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityOvergrownSheep extends EntitySheep
{
	public static final DataParameter<Float> GROWTH = EntityDataManager.createKey(EntityOvergrownSheep.class, DataSerializers.FLOAT);

	public static final float MIN_GROWTH = 0.8F;
	public static final float MAX_GROWTH = 1.8F;
	private static final float GROWTH_STEP = 0.1F;

	public EntityOvergrownSheep(World worldIn)
	{
		super(worldIn);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(GROWTH, 1F);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		float growth = getGrowth();
		if(growth > 1)
		{
			float amt = (growth - 1) * 10;

			float flyAmount = (amt) / 25;
			motionY = Math.max(motionY, flyAmount);
			velocityChanged = true;


			if(getLeashed())
			{
				Entity e = getLeashHolder();
				if(e != null)
				{
					float leashDistance = getDistance(e);
					if(leashDistance >= 5.0)
					{
						e.motionY = flyAmount;
					}
				}
			}
		}
	}

	/*
				Gets current growth
		 */
	public float getGrowth()
	{
		return dataManager.get(GROWTH);
	}

	/*
			Sets wool growth with min of MIN_GROWTH and max of MAX_GROWTH
	 */
	public float setGrowth(float growth)
	{
		float val = Math.max(MIN_GROWTH, Math.min(MAX_GROWTH, growth));
		dataManager.set(GROWTH, val);

		return val;
	}

	/*
			returns true if wool can't shrink anymore
	 */
	public boolean getSheared()
	{
		return getGrowth() <= MIN_GROWTH;
	}

	/*
			shrink wool if shearing, grow otherwise.
	 */
	public void setSheared(boolean sheared)
	{
		if(sheared)
			shrink();
		else
			grow();
	}

	/*
			Shrinks wool growth, maximum 1.8
	 */
	public void grow()
	{
		if(!world.isRemote)
		{
			setGrowth(getGrowth() + GROWTH_STEP);
		}
	}

	/*
			Shrinks wool growth, minimum 0.8
	 */
	public void shrink()
	{
		if(!world.isRemote)
		{
			setGrowth(getGrowth() - GROWTH_STEP);
		}
	}
}