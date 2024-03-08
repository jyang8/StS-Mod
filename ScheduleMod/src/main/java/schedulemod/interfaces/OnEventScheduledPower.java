package schedulemod.interfaces;

import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.cards.EventCard;

public interface OnEventScheduledPower {
    public void onEventScheduled(EventCard card, int slot, AbstractOrb replaced);
}
