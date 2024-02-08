package schedulemod.interfaces;

import schedulemod.cards.EventCard;

public interface OnEventScheduledPower {
    public void onEventScheduled(EventCard card, int slot);
}
