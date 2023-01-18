package com.loucaskreger.autmclient.module;

import com.loucaskreger.autmclient.AutmClient;
import com.loucaskreger.autmclient.PacketHelper;
import com.loucaskreger.autmclient.client.widget.ReachSlider;
import com.loucaskreger.autmclient.module.listener.ILeftClickEventListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ReachModule extends AbstractModule implements ILeftClickEventListener {

    private static final double MAX_REACH = 50.0D;
    private double reachDistance = 4.0D;

    @Override
    public String getName() {
        return "Reach";
    }

    @Override
    public List<ClickableWidget> getWidgets() {
        return List.of(this.createButton("Reach"), new ReachSlider(this, 0, 0, MAX_REACH, reachDistance / MAX_REACH));
    }

    @Override
    public void onLeftClick(int action, int mods) {
        var client = MinecraftClient.getInstance();
        var player = client.player;
        // Make sure module is enabled and player exists.
        if (!this.isEnabled() || player == null) {
            return;
        }
        Entity cameraEntity = client.getCameraEntity();
        // Camera can be null
        if (cameraEntity == null) {
            return;
        }

        // Skip if were inside an inventory.
        if (client.currentScreen != null) {
            return;
        }

        // world cannot be null and the action must be "press"
        if (client.world != null && action == 1) {
            Vec3d vec3d = cameraEntity.getCameraPosVec(1.0f);
            Vec3d vec3d2 = cameraEntity.getRotationVec(1.0F);
            Vec3d vec3d3 = vec3d.add(vec3d2.x * reachDistance, vec3d2.y * reachDistance, vec3d2.z * reachDistance);

            Box box = cameraEntity.getBoundingBox().stretch(vec3d2.multiply(reachDistance)).expand(1.0D, 1.0D, 1.0D);

            EntityHitResult entityHitResult = ProjectileUtil.raycast(cameraEntity, vec3d, vec3d3, box, (entityX) -> !entityX.isSpectator(), reachDistance * reachDistance);
            if (entityHitResult != null) {
                var entity = entityHitResult.getEntity();
                if (entity instanceof LivingEntity || entity instanceof ItemFrameEntity) {
                    PacketHelper.teleportFromTo(player.getPos(), entityHitResult.getEntity().getPos());
                    AutmClient.LOGGER.info("Targeting entity: {}", entityHitResult.getEntity());

                    PacketHelper.sendImmediately(PlayerInteractEntityC2SPacket.attack(entityHitResult.getEntity(), false));

                    PacketHelper.teleportFromTo(entityHitResult.getEntity().getPos(), player.getPos());
                }
            }
        }
    }

    public void setReachDistance(double reachDistance) {
        this.reachDistance = reachDistance;
    }
}
