package com.loucaskreger.autmclient.module;

import com.loucaskreger.autmclient.AutmClient;
import com.loucaskreger.autmclient.client.widget.EditButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.List;

public class AutoSign extends AbstractModule {

    public static String ID = "Auto Sign";
    private String[] text;

    public AutoSign() {
        super();
        this.text = new String[4];
        // TODO: Allow sign text to be editable in screen
        setSignText();
    }

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public List<ClickableWidget> getWidgets() {
        return List.of(this.createButton("Toggle Auto Sign"), new EditButton((button) -> AutmClient.LOGGER.info("Pressed!")));
    }

    public void setSignText() {
        text[0] = "You've been";
        text[1] = "greifed by";
        text[2] = "S.H.A.W.L";
        text[3] = ".;,;.";
    }

    public void setSignText(String[] text) {
        this.text = text;
    }

    public String[] getSignText() {
        return this.text;
    }
}
