package com.loucaskreger.autmclient;

import com.loucaskreger.autmclient.mixin.IClientConnectionInvoker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class PacketHelper {
    private static final double BLINK_DISTANCE = 8.5D;

    public static void sendImmediately(Packet<?> packet) {
        var connection = (IClientConnectionInvoker) MinecraftClient.getInstance().player.networkHandler.getConnection();
        if (connection != null) {
            connection.callSendImmediately(packet, null);
        }
    }

    public static void sendPosition(Vec3d pos) {
        PacketHelper.sendImmediately(new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), MinecraftClient.getInstance().player.isOnGround()));
    }

    public static void teleportFromTo(Vec3d from, Vec3d to) {
        var targetDistance = Math.ceil(from.distanceTo(to) / BLINK_DISTANCE);
        for (int i = 1; i <= targetDistance; i++) {
            Vec3d tmpPos = from.lerp(to, i / targetDistance);
            sendPosition(tmpPos);
            if (i % 4 == 0) {
                try {
                    Thread.sleep((long) ((1.0 / 20.0) * 1000));
                } catch (InterruptedException e) {
                    AutmClient.LOGGER.info("Sleeping failed: {}", e);
                }
            }
        }
    }
}
