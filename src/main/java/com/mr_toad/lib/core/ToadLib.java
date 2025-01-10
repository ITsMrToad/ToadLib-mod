package com.mr_toad.lib.core;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.InputConstants;
import com.mr_toad.lib.api.client.init.ConfigEntryWidgetsRegistry;
import com.mr_toad.lib.api.client.init.EaseWidgetSettingsRegistry;
import com.mr_toad.lib.api.client.resource.ConfigReloadListener;
import com.mr_toad.lib.api.client.screen.interpolation.InterpolationSelectionScreen;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.lib.api.entity.immune.EffectImmuneStorage;
import com.mr_toad.lib.core.config.ToadLibConfig;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@Mod(ToadLib.MODID)
public class ToadLib {

    public static final String MODID = "toadlib";
    public static final Object2ObjectMap<String, Supplier<SavedData>> SAVED_DATA_SUPPLIERS = new Object2ObjectOpenHashMap<>();

    public static final Marker CONFIG = MarkerFactory.getMarker("ToadConfig");
    public static final Logger LOGGER = LoggerFactory.getLogger("ToadLib");

    public static final ToadLibConfig CFG = new ToadLibConfig();

    public ToadLib() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(this::clientSetup));
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ToadConfigs.create(MODID, CFG);
            EaseWidgetSettingsRegistry.init();
            ConfigEntryWidgetsRegistry.init();
            ToadConfigs.load();
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static <D extends SavedData> Optional<Supplier<D>> findSavedData(String file) {
        try {
            Supplier<D> supplier = (Supplier<D>) SAVED_DATA_SUPPLIERS.get(file);
            if (supplier == null) {
                ToadLib.LOGGER.error("Saved data with file name '{}', doesn't exists or server level not initialized!", file);
                return Optional.empty();
            } else {
                return Optional.of(Suppliers.memoize(supplier));
            }
        } catch (Exception e) {
            ToadLib.LOGGER.error("Error during get '{}' saved data!", file, e);
            return Optional.empty();
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.DEDICATED_SERVER)
    public static class Server {
        @SubscribeEvent
        public static void onLevelLoad(LevelEvent.Load event) {
            EffectImmuneStorage.init();
        }

        @SubscribeEvent
        public static void onEffectApply(MobEffectEvent.Applicable event) {
            if (EffectImmuneStorage.isInImmuneMap(event.getEntity(), event.getEffectInstance())) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientBus {
        @SubscribeEvent
        public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(new ConfigReloadListener());
        }
    }

    @ParametersAreNonnullByDefault
    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void addConfigButtons(ScreenEvent.Init event) {
            if (event.getScreen() instanceof TitleScreen || event.getScreen() instanceof PauseScreen) {
                event.addListener(ToadClientUtils.createConfigButton(event.getScreen().width / 2 + 104, event.getScreen().height / 4 + 96, event.getScreen()));
            }
        }

        @SubscribeEvent
        public static void onKey(InputEvent.Key event) {
            if (Minecraft.getInstance().screen == null && event.getKey() == InputConstants.KEY_I) {
                if (CFG.interpOverview.value) {
                    Minecraft.getInstance().setScreen(new InterpolationSelectionScreen<>(InterpolationSelectionScreen.ALL_REGISTERED_INTERPOLATIONS, null) {
                        @Override
                        public void onSelect(Interpolation interpolation) {
                        }
                    });
                }
            }
        }
    }
}
