package com.loucaskreger.autmclient.module;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

public class OverlayModule extends AbstractModule {

    public static final String ID = "Overlay Module";
    private HudPosition position = HudPosition.TOP_LEFT;

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public List<ClickableWidget> getWidgets() {
        var toggleButton = ButtonWidget.builder(Text.of("Toggle Overlay Side"), (button) -> {
            if (this.position == HudPosition.TOP_LEFT) {
                this.position = HudPosition.TOP_RIGHT;
            } else if (this.position == HudPosition.TOP_RIGHT) {
                this.position = HudPosition.TOP_LEFT;
            }
        }).dimensions(0, 0, 100, 20).build();
        return List.of(toggleButton);
    }

    public HudPosition getOverlayPosition() {
        return this.position;
    }
}
