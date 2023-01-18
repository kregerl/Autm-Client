package com.loucaskreger.autmclient.mixin;

import com.loucaskreger.autmclient.event.ClickEvents;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseInputMixin {

    @Inject(method = "onMouseButton", at = @At("TAIL"))
    private void hookOnMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (button == 0) {
            ClickEvents.LEFT_CLICK_EVENT.invoker().onLeftClick(action, mods);
        } else if (button == 1) {
            ClickEvents.RIGHT_CLICK_EVENT.invoker().onRightClick(action, mods);
        }
    }
}
