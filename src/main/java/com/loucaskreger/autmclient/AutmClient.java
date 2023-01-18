package com.loucaskreger.autmclient;

import com.loucaskreger.autmclient.module.AutoSign;
import com.loucaskreger.autmclient.module.Locusts;
import com.loucaskreger.autmclient.module.OverlayModule;
import com.loucaskreger.autmclient.module.ReachModule;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutmClient implements ModInitializer {
    public static final String MOD_ID = "autmclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModuleManager.getInstance().registerEvents();
        ModuleManager.getInstance().addModule(new OverlayModule());
        ModuleManager.getInstance().addModule(new Locusts());
        ModuleManager.getInstance().addModule(new AutoSign());
        ModuleManager.getInstance().addModule(new ReachModule());
    }
}
