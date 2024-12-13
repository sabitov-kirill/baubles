package lazy.baubles;

import lazy.baubles.api.BaublesAPI;
import lazy.baubles.client.gui.PlayerExpandedScreen;
import lazy.baubles.network.PacketHandler;
import lazy.baubles.setup.ModConfigs;
import lazy.baubles.setup.ModItems;
import lazy.baubles.setup.ModMenus;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.mojang.blaze3d.platform.InputConstants;

@Mod(BaublesAPI.MOD_ID)
public class Baubles {

    public static final Lazy<KeyMapping> KEY_BAUBLES = Lazy.of(() -> new KeyMapping(
            "keybind.baublesinventory",
            InputConstants.KEY_B,
            "key.categories.inventory"));

    public Baubles() {
        ModConfigs.registerAndLoadConfig();
        ModItems.init();
        ModMenus.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupCommon(FMLCommonSetupEvent event) {
        PacketHandler.registerMessages();
    }

    private void setupClient(FMLClientSetupEvent event) {
        MenuScreens.register(ModMenus.PLAYER_BAUBLES.get(), PlayerExpandedScreen::new);
    }

    @SubscribeEvent
    public static void registerKeyBinds(RegisterKeyMappingsEvent event) {
        event.register(KEY_BAUBLES.get());
    }
}
