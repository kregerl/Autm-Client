package com.loucaskreger.autmclient.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ClickEvents {

    public static final Event<LeftClick> LEFT_CLICK_EVENT = EventFactory.createArrayBacked(LeftClick.class, callbacks -> (action, mods) -> {
        for (LeftClick callback : callbacks) {
            callback.onLeftClick(action, mods);
        }
    });

    public static final Event<RightClick> RIGHT_CLICK_EVENT = EventFactory.createArrayBacked(RightClick.class, callbacks -> (action, mods) -> {
        for (RightClick callback : callbacks) {
            callback.onRightClick(action, mods);
        }
    });


    @FunctionalInterface
    interface LeftClick {
        void onLeftClick(int action, int mods);
    }

    @FunctionalInterface
    interface RightClick {
        void onRightClick(int action, int mods);
    }

}
