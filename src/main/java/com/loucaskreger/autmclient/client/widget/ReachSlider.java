package com.loucaskreger.autmclient.client.widget;

import com.loucaskreger.autmclient.module.ReachModule;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class ReachSlider extends SliderWidget {
    private final ReachModule module;
    private final double maxValue;

    public ReachSlider(ReachModule module, int x, int y, double maxValue, double defaultValue) {
        super(x, y, 140, 20, Text.of(String.format("Reach: %.1f", maxValue * defaultValue)), defaultValue);
        this.maxValue = maxValue;
        this.module = module;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.of(String.format("Reach: %.1f", this.getReachDistance())));
    }

    @Override
    protected void applyValue() {
        this.module.setReachDistance(this.getReachDistance());
    }

    public double getReachDistance() {
        return this.maxValue * this.value;
    }
}
