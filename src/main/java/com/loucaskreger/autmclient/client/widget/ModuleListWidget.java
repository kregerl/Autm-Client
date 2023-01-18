package com.loucaskreger.autmclient.client.widget;

import com.loucaskreger.autmclient.AutmClient;
import com.loucaskreger.autmclient.ModuleManager;
import com.loucaskreger.autmclient.module.AbstractModule;
import com.loucaskreger.autmclient.module.OverlayModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;


public class ModuleListWidget extends EntryListWidget<ModuleListWidget.ModuleListEntry> {

    public ModuleListWidget(MinecraftClient client, Screen parent) {
        super(client, parent.width, parent.height, 20, parent.height, 20);
        this.setRenderBackground(false);
        this.setRenderHeader(false, -1);
        this.setRenderHorizontalShadows(false);
        for (var module : ModuleManager.getInstance().getModuleMap().values()) {
            if (OverlayModule.ID.equals(module.getName()))
                continue;
            this.addEntry(new ModuleListEntry(module));
        }
        this.addEntry(new ModuleListEntry(ModuleManager.getInstance().getModule(OverlayModule.ID).get()));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected void renderHeader(MatrixStack matrices, int x, int y, Tessellator tessellator) {
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    static class ModuleListEntry extends ElementListWidget.Entry<ModuleListWidget.ModuleListEntry> {
        private static final int BUTTON_GAP = 2;
        private final AbstractModule module;
        private final List<ClickableWidget> clickables;

        public ModuleListEntry(AbstractModule module) {
            this.module = module;
            this.clickables = new ArrayList<>();
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            int lastWidth = 0;
            // Buttons are modified here so they need to be cleared and saved on every render
            clickables.clear();
            for (var drawable : module.getWidgets()) {
                drawable.setX(x + lastWidth + BUTTON_GAP);
                drawable.setY(y);
                drawable.render(matrices, mouseX, mouseY, tickDelta);
                lastWidth = drawable.getWidth();
                clickables.add(drawable);
            }
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            AutmClient.LOGGER.info("selectableChildren :: Got {} buttons", this.clickables.size());
            return this.clickables;
        }

        @Override
        public List<? extends Element> children() {
            AutmClient.LOGGER.info("children :: Got {} buttons", this.clickables.size());
            return this.clickables;
        }
    }
}
