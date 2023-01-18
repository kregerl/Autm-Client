package com.loucaskreger.autmclient.module;

import com.loucaskreger.autmclient.PacketHelper;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.List;

public class Locusts extends AbstractModule implements ITickableModule {

    @Override
    public String getName() {
        return "Locusts";
    }

    @Override
    public List<ClickableWidget> getWidgets() {
        return List.of(this.createButton("Toggle Locus"));
    }

    @Override
    public void tick(MinecraftClient client) {
        if (client.player != null && this.enabled) {
            var playerPos = client.player.getPos();
            var start = playerPos.add(10, 5, 10);
            var end = playerPos.add(-10, -5, -10);
            var iter = BlockPos.iterate(new BlockPos(start), new BlockPos(end)).iterator();

            var interactionManager = client.interactionManager;

            while (iter.hasNext()) {
                var pos = iter.next();
                var block = client.world.getBlockState(pos).getBlock();

                if (block instanceof CropBlock || block instanceof NetherWartBlock || block instanceof SugarCaneBlock || block instanceof SweetBerryBushBlock || block instanceof StemBlock) {
                    PacketHelper.teleportFromTo(playerPos, pos.toCenterPos());

                    interactionManager.attackBlock(pos, Direction.DOWN);

                    PacketHelper.teleportFromTo(pos.toCenterPos(), playerPos);

                }
            }
        }
    }
}
