package com.loucaskreger.autmclient;

import com.loucaskreger.autmclient.client.ModuleScreen;
import com.loucaskreger.autmclient.event.ClickEvents;
import com.loucaskreger.autmclient.module.AbstractModule;
import com.loucaskreger.autmclient.module.ITickableModule;
import com.loucaskreger.autmclient.module.OverlayModule;
import com.loucaskreger.autmclient.module.listener.ILeftClickEventListener;
import com.loucaskreger.autmclient.module.listener.IRightClickEventListener;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class ModuleManager {

    private static final ModuleManager INSTANCE = new ModuleManager();

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private static final KeyBinding OPEN_MODULE_SCREEN = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    "key.autmclient.modules",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_K,
                    "category.autmclient.modules"
            ));

    private final Map<String, AbstractModule> modules;

    public ModuleManager() {
        modules = new HashMap<>();
    }

    public void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(this::endClientTick);
        ClientTickEvents.START_CLIENT_TICK.register(this::startClientTick);
        ClickEvents.LEFT_CLICK_EVENT.register(this::onLeftClick);
        ClickEvents.RIGHT_CLICK_EVENT.register(this::onRightClick);
        HudRenderCallback.EVENT.register(this::renderHud);
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            AutmClient.LOGGER.info("AttackEntityCallback");
            return ActionResult.PASS;
        });
    }



    public <T extends AbstractModule> void addModule(T module) {
        this.modules.put(module.getName(), module);
    }

    public Optional<AbstractModule> getModule(String name) {
        AbstractModule module;
        if ((module = this.modules.get(name)) != null) {
            return Optional.of(module);
        }
        return Optional.empty();
    }

    public Map<String, AbstractModule> getModuleMap() {
        return modules;
    }

    public List<ClickableWidget> getModuleWidgets() {
        List<ClickableWidget> widgets = new ArrayList<>();
        for (Map.Entry<String, AbstractModule> entry : this.modules.entrySet()) {
            widgets.addAll(entry.getValue().getWidgets());
        }
        return widgets;
    }

    // Called at the end of a client tick event
    private void endClientTick(MinecraftClient client) {
        if (client.currentScreen == null && OPEN_MODULE_SCREEN.isPressed()) {
            client.setScreen(new ModuleScreen());
        }
    }

    // Called at the start of a client tick event
    private void startClientTick(MinecraftClient client) {
        this.modules.values().stream().filter((module) -> module instanceof ITickableModule).forEach(module -> ((ITickableModule) module).tick(client));
    }

    // Called on every render tick of the player's hud
    private void renderHud(MatrixStack matrixStack, float v) {
        var mc = MinecraftClient.getInstance();
        var textRenderer = mc.textRenderer;
        // Set to 4 for padding between text.
        int yPos = 4;
        for (var entry : this.modules.values()) {
            // Ignore the hud overlay module.
            if (entry.isEnabled() && !OverlayModule.ID.equals(entry.getName())) {
                // Get the hud overlay position
                var position = ((OverlayModule) this.modules.get(OverlayModule.ID)).getOverlayPosition();
                int textWidth = textRenderer.getWidth(entry.getName());
                // Use the HubPosition to compute the string's x position.
                var xPos = position.getXPosition(mc.getWindow().getScaledWidth(), textWidth);
                textRenderer.drawWithShadow(matrixStack, entry.getName(), xPos, yPos, 0x1AFF00);
                yPos += 13;
            }
        }
    }

    private void onLeftClick(int action, int mods) {
        this.modules.values().stream().filter((module) -> module instanceof ILeftClickEventListener).forEach(module -> ((ILeftClickEventListener) module).onLeftClick(action, mods));
    }

    private void onRightClick(int action, int mods) {
        this.modules.values().stream().filter((module) -> module instanceof IRightClickEventListener).forEach(module -> ((IRightClickEventListener) module).onRightClick(action, mods));
    }

//    private ActionResult useBlock(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
//        this.modules.values().stream().filter((module) -> module instanceof IUseBlockEventListener).forEach(module -> ((IUseBlockEventListener) module).onUseBlock(playerEntity, world, hand, blockHitResult));
//    }
//
//    private void sendPosition(Vec3d pos) {
//        var mc = MinecraftClient.getInstance();
//        var connection = (IClientConnectionInvoker) mc.player.networkHandler.getConnection();
//        if (connection != null) {
//            connection.callSendImmediately(new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), mc.player.isOnGround()), null);
//        }
//    }
}


