package com.loucaskreger.autmclient.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface IHandledScreenAccessor {

    /**
     * @return The x value of the screen background
     */
    @Accessor(value = "x")
    public int getContainerX();


    /**
     * @return The y value of the screen background
     */
    @Accessor(value = "y")
    public int getContainerY();


    @Accessor(value = "backgroundWidth")
    public int getBackgroundWidth();

}

