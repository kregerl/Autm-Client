package com.loucaskreger.autmclient.client.widget;

import com.loucaskreger.autmclient.AutmClient;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Quaternionf;

public class ArrowButtonWidget extends ButtonWidget {

    private static final Identifier TEXTURE = new Identifier(AutmClient.MOD_ID, "textures/gui/arrow.png");
    public static final int BUTTON_WIDTH = 13;
    public static final int BUTTON_HEIGHT = 13;
    public static final int ARROW_WIDTH = 9;
    public static final int ARROW_HEIGHT = 8;
    private static final Vec3d POSITIVE_Z = new Vec3d(0.0D, 0.0D, 1.0D);

    private Orientation orientation;

    public ArrowButtonWidget(int x, int y, PressAction onPress, Orientation orientation, ButtonWidget.NarrationSupplier tooltip) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, Text.empty(), onPress, tooltip);
        this.orientation = orientation;
    }

    public ArrowButtonWidget(int x, int y, PressAction onPress) {
        this(x, y, onPress, Orientation.UP, (unused) -> Text.empty());
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.getX(), this.getY(), 0, this.isHovered() ? BUTTON_HEIGHT : 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        matrices.push();
        var translation = this.orientation.getTranslation();
        matrices.translate(translation.getX() + this.getX() + 2, translation.getY() + this.getY() + 2, translation.getZ());
        matrices.multiply(this.orientation.getRotation());
        this.drawTexture(matrices, 0, 0, 0, BUTTON_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);
        matrices.pop();

    }

    public enum Orientation {
        UP(getDegreesQuaternion(POSITIVE_Z, 0.0f), new Vec3i(0, 0, 0)),
        DOWN(getDegreesQuaternion(POSITIVE_Z, 180.0f), new Vec3i(ARROW_WIDTH, ARROW_HEIGHT, 0)),
        LEFT(getDegreesQuaternion(POSITIVE_Z, -90.0f), new Vec3i(0, ARROW_WIDTH, 0)),
        RIGHT(getDegreesQuaternion(POSITIVE_Z, 90.0f), new Vec3i(ARROW_WIDTH, 0, 0));

        private Quaternionf quaternion;
        private Vec3i translation;

        Orientation(Quaternionf quaternion, Vec3i translation) {
            this.quaternion = quaternion;
            this.translation = translation;
        }

        public Quaternionf getRotation() {
            return this.quaternion;
        }

        public Vec3i getTranslation() {
            return this.translation;
        }

        static public Quaternionf getDegreesQuaternion(Vec3d vec, float angle) {
            return new Quaternionf(vec.getX(), vec.getY(), vec.getZ(), angle);
        }
    }
}
