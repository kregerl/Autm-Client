package com.loucaskreger.autmclient.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

//    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
//    private void onGetReachDistance(CallbackInfoReturnable<Float> cir) {
//        cir.setReturnValue(20.0f);
//    }
//
//    @Inject(method = "hasExtendedReach", at = @At("HEAD"), cancellable = true)
//    private void onhasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(true);
//    }
}
