package party.lemons.balloonsheep;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "balloonsheep", name = "Balloon Sheep", version = "1.0.0")
@Mod.EventBusSubscriber(modid = "balloonsheep")
public class BalloonSheep
{
	@Mod.EventHandler
	public void onInit(FMLPreInitializationEvent event)
	{
		if(event.getSide() == Side.CLIENT)
		{
			RenderingRegistry.registerEntityRenderingHandler(EntityOvergrownSheep.class, RenderOvergrownSheep::new);
		}
	}

	@Mod.EventBusSubscriber(modid = "balloonsheep")
	@GameRegistry.ObjectHolder("balloonsheep")
	public static class ModItems
	{
		public static final Item GROWTH_POWDER = Items.AIR;

		@SubscribeEvent
		public static void onRegisterItem(RegistryEvent.Register<Item> event)
		{
			event.getRegistry().register(new ItemGrowthPowder().setUnlocalizedName("balloonsheep.growth_powder").setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("balloonsheep", "growth_powder"));
		}
	}


	@Mod.EventBusSubscriber(modid = "balloonsheep")
	public static class ModEntities
	{
		@SubscribeEvent
		public static void onRegisterEntity(RegistryEvent.Register<EntityEntry> event)
		{
			event.getRegistry().register(EntityEntryBuilder.create().name("Overgrown Sheep").entity(EntityOvergrownSheep.class).id(new ResourceLocation("balloonsheep", "overgrown_sheep"), 0).tracker(80, 3, true).build());
		}
	}

	@Mod.EventBusSubscriber(modid = "balloonsheep", value = Side.CLIENT)
	public static class Models
	{
		@SubscribeEvent
		public static void onRegisterModel (ModelRegistryEvent event)
		{
			ModelLoader.setCustomModelResourceLocation(ModItems.GROWTH_POWDER, 0, new ModelResourceLocation(ModItems.GROWTH_POWDER.getRegistryName(), "inventory"));
		}
	}

}
