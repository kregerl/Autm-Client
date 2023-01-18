package com.loucaskreger.autmclient.module;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

public abstract class AbstractModule {

    protected boolean enabled;

    public AbstractModule() {
        this.enabled = false;
    }

    protected ButtonWidget createButton(String text) {
        return ButtonWidget.builder(Text.of(text),
                (button) -> this.enabled = !this.enabled).dimensions(0, 0, 100, 20).build();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public abstract String getName();

    public abstract List<ClickableWidget> getWidgets();
}
