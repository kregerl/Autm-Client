package com.loucaskreger.autmclient.module;

import net.minecraft.client.MinecraftClient;

public interface ITickableModule {
    void tick(MinecraftClient client);
}
