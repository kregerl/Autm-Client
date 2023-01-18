package com.loucaskreger.autmclient.client;

import com.loucaskreger.autmclient.client.widget.ModuleListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ModuleScreen extends Screen {

    private ModuleListWidget moduleList;

    public ModuleScreen() {
        super(Text.of("Module Screen"));
    }

    @Override
    protected void init() {
        this.moduleList = new ModuleListWidget(this.client, this);
        this.addSelectableChild(this.moduleList);
        super.init();
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        this.fillGradient(matrices, 0, 0, this.width, this.height, 0x1C1C1C7F, 0x1C1C1C7F);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.moduleList.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }


}
