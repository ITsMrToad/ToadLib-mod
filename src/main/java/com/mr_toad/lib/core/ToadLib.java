package com.mr_toad.lib.core;

import com.mr_toad.lib.data.tag.ToadlyBiomeTagProvider;
import com.mr_toad.lib.data.tag.ToadlyBlockTagProvider;
import com.mr_toad.lib.data.tag.ToadlyItemTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@Mod(ToadLib.MODID)
public class ToadLib {

    public static final String MODID = "toadlib";
    public static final Logger LOGGER = LoggerFactory.getLogger("ToadLib");

    public ToadLib() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(this::dataSetup);
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        boolean includeServer = event.includeServer();
        ToadlyBlockTagProvider blockTagProvider = new ToadlyBlockTagProvider(packOutput, lookupProvider, fileHelper);

        generator.addProvider(includeServer, blockTagProvider);
        generator.addProvider(includeServer, new ToadlyItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), fileHelper));
        generator.addProvider(includeServer, new ToadlyBiomeTagProvider(packOutput, lookupProvider, fileHelper));
    }
}
