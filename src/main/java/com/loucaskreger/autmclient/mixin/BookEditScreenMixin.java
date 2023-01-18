package com.loucaskreger.autmclient.mixin;

import com.loucaskreger.autmclient.AutmClient;
import com.loucaskreger.autmclient.client.widget.EditButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin extends Screen {

    @Final
    @Mutable
    @Shadow
    private List<String> pages;

    private static final int MAX_PAGES = 100;
    private static final int MAX_CHARS = 266;

    protected BookEditScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        var widget = ButtonWidget.builder(Text.of("Create Ban Book"), (button) -> {
            // Repeat special char MAX_CHARS times.
            var payload = "\uDBFF\uDFFF".repeat(MAX_CHARS);
            List<String> pages = new ArrayList<>();
            for (int i = 0; i < MAX_PAGES; i++) {
                pages.add(payload);
            }
            this.pages = pages;
        }).build();

        this.addDrawableChild(new EditButton(0 , 50, (x) -> AutmClient.LOGGER.info("Pressed!")));
        this.addDrawableChild(widget);
    }
}
