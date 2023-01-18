package com.loucaskreger.autmclient.mixin;

import com.loucaskreger.autmclient.AutmClient;
import com.loucaskreger.autmclient.ModuleManager;
import com.loucaskreger.autmclient.module.AutoSign;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignEditScreen.class)
public class SignEditScreenMixin extends Screen {

    @Final
    @Mutable
    @Shadow
    private String[] text;

    protected SignEditScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        var module = ModuleManager.getInstance().getModule(AutoSign.ID);
        module.ifPresent((abstractModule) -> {
            if (abstractModule.isEnabled()) {
                this.text = ((AutoSign)abstractModule).getSignText();
                this.close();
            }
        });
    }
}
