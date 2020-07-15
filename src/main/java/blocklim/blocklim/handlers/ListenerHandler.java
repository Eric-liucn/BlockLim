package blocklim.blocklim.handlers;

import blocklim.blocklim.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

public class ListenerHandler {

    public ListenerHandler(){
        Sponge.getEventManager().registerListeners(Main.getINSTANCE(), this);
    }

    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event){
        
    }
}
