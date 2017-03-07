package app.watchapp.event;

/**
 * Created by douglas on 07/03/17.
 */

public class FABEvent {

    private Boolean toggle;

    public FABEvent(Boolean toggle) {
        this.toggle = toggle;
    }

    public Boolean getToggle() {
        return toggle;
    }
}
