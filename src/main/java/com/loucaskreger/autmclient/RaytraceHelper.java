package com.loucaskreger.autmclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Math;
import org.joml.Vector3f;

public class RaytraceHelper {

    public static boolean normalize(Vector3f vec) {
        float f = vec.x * vec.x + vec.y * vec.y + vec.z * vec.z;
        if ((double)f < 1.0E-5D) {
            return false;
        } else {
            float f1 = Math.invsqrt(f);
            vec.x *= f1;
            vec.y *= f1;
            vec.z *= f1;
            return true;
        }
    }

    public static Vec3d map(float anglePerPixel, Vec3d center, Vector3f horizontalRotationAxis,
                             Vector3f verticalRotationAxis, int x, int y, int width, int height) {
        float horizontalRotation = (x - width/2f) * anglePerPixel;
        float verticalRotation = (y - height/2f) * anglePerPixel;

        final Vector3f temp2 = new Vector3f(center.toVector3f());
        temp2.rotateAxis(verticalRotation, verticalRotationAxis.x(), verticalRotationAxis.y(), verticalRotationAxis.z());
        temp2.rotateAxis(horizontalRotation, horizontalRotationAxis.x(), horizontalRotationAxis.y(), horizontalRotationAxis.z());
        return new Vec3d(temp2);
    }

    public static HitResult rayTraceInDirection(MinecraftClient client, float tickDelta, Vec3d direction) {
        Entity entity = client.getCameraEntity();
        if (entity == null || client.world == null) {
            return null;
        }

        double reachDistance = 5.0F;
        HitResult target = rayTrace(entity, reachDistance, tickDelta, false, direction);
        boolean tooFar = false;
        double extendedReach = 6.0D;
        reachDistance = extendedReach;

        Vec3d cameraPos = entity.getCameraPosVec(tickDelta);

        extendedReach = extendedReach * extendedReach;
        if (target != null) {
            extendedReach = target.getPos().squaredDistanceTo(cameraPos);
        }

        Vec3d vec3d3 = cameraPos.add(direction.multiply(reachDistance));
        Box box = entity
                .getBoundingBox()
                .stretch(entity.getRotationVec(1.0F).multiply(reachDistance))
                .expand(1.0D, 1.0D, 1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                entity,
                cameraPos,
                vec3d3,
                box,
                (entityx) -> !entityx.isSpectator() && entityx.canHit(),
                extendedReach
        );

        if (entityHitResult == null) {
            AutmClient.LOGGER.info("Entity result is null");
            return target;
        }

        Entity entity2 = entityHitResult.getEntity();
        Vec3d hitPos = entityHitResult.getPos();
        if (cameraPos.squaredDistanceTo(hitPos) < extendedReach || target == null) {
            target = entityHitResult;
            if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
                client.targetedEntity = entity2;
            }
        }

        return target;
    }

    public static HitResult rayTrace(
            Entity entity,
            double maxDistance,
            float tickDelta,
            boolean includeFluids,
            Vec3d direction
    ) {
        Vec3d end = entity.getCameraPosVec(tickDelta).add(direction.multiply(maxDistance));
        return entity.world.raycast(new RaycastContext(
                entity.getCameraPosVec(tickDelta),
                end,
                RaycastContext.ShapeType.OUTLINE,
                includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE,
                entity
        ));
    }
}
