package app.watchapp.event;

/**
 * Created by douglas on 07/03/17.
 */

public class FABEvent {

    private Boolean display;

    public FABEvent(Boolean display) {
        this.display = display;
    }

    public Boolean display() {
        return display;
    }
}
