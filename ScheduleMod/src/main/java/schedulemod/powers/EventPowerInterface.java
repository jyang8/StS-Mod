package schedulemod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;

import schedulemod.cards.EventCard;

public interface EventPowerInterface {
    public void modifyEvent(EventCard eventCard, AbstractCard triggeringCard);
}
