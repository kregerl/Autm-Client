package com.loucaskreger.autmclient.client.widget;

import com.loucaskreger.autmclient.AutmClient;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EditButton extends ButtonWidget {

    private static final Identifier EDIT_BUTTON_TEXTURE = new Identifier(AutmClient.MOD_ID, "textures/gui/edit_button.png");
    private static final int BUTTON_SIZE = 20;

    public EditButton(PressAction onPress) {
        this(0, 0, onPress);
    }

    public EditButton(int x, int y, PressAction onPress) {
        super(x, y, BUTTON_SIZE, BUTTON_SIZE, Text.empty(), onPress, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, EDIT_BUTTON_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.getX(), this.getY(), 0, this.isHovered() ? BUTTON_SIZE : 0, BUTTON_SIZE, BUTTON_SIZE);
        matrices.pop();
    }

}
