package com.mr_toad.lib.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ToadLib.MODID)
public class ToadLib {

    public static final String MODID = "toadlib";
    public static final Logger LOGGER = LoggerFactory.getLogger("ToadLib");

    public ToadLib() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }

}
