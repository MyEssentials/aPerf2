package aperf.modules.tile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler {
    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent ev) {
        switch(ev.phase) {
            case START:
                startServerTick(ev);
                break;
            case END:
                endServerTick(ev);
                break;
        }
    }

    @SubscribeEvent
    public void worldTick(TickEvent.WorldTickEvent ev) {
        switch(ev.phase) {
            case START:
                startWorldTick(ev);
                break;
            case END:
                endWorldTick(ev);
                break;
        }
    }

    private void startServerTick(TickEvent.ServerTickEvent ev) {
    }

    private void endServerTick(TickEvent.ServerTickEvent ev) {
    }

    private void startWorldTick(TickEvent.WorldTickEvent ev) {
    }

    private void endWorldTick(TickEvent.WorldTickEvent ev) {
    }
}
