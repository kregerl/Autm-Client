package com.loucaskreger.autmclient.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketCallbacks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientConnection.class)
public interface IClientConnectionInvoker {

    @Invoker
    void callSendImmediately(Packet<?> packet, @Nullable PacketCallbacks callbacks);
}
