package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.cards.EventCard;
import schedulemod.interfaces.OnEventScheduledPower;
import schedulemod.orbs.ScheduleOrb;

public class ScheduleEventCard extends AbstractGameAction {
    private final AbstractCard card;
    private final int slot;
    private final CardGroup source;
    private boolean skipWait;

    public ScheduleEventCard(AbstractCard card, int scheduleSlot) {
        this(card, scheduleSlot, (CardGroup) null);
    }

    public ScheduleEventCard(AbstractCard card, int scheduleSlot, CardGroup source, boolean skipWait) {
        this(card, scheduleSlot, source);
        this.skipWait = skipWait;
    }

    public ScheduleEventCard(AbstractCard card, int scheduleSlot, CardGroup source) {
        this.card = card;
        this.slot = scheduleSlot - 1;
        this.source = source;
        this.skipWait = false;
        this.actionType = ActionType.CARD_MANIPULATION;
        if (source != null && source.type == CardGroup.CardGroupType.HAND)
            card.retain = true;
    }

    public void update() {
        for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow instanceof OnEventScheduledPower) {
                ((OnEventScheduledPower)pow).onEventScheduled((EventCard)card, slot);
            }
        }
        if (!AbstractDungeon.player.hasEmptyOrb())
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (!(o instanceof ScheduleOrb)) {
                    AbstractDungeon.player.orbs.remove(o);
                    AbstractDungeon.player.orbs.add(0, o);
                    AbstractDungeon.player.evokeOrb();
                    break;
                }
            }
        if (!this.skipWait && !Settings.FAST_MODE)
            addToTop(new WaitAction(0.1F));
        addToTop(new ChannelAction(new ScheduleOrb(this.card, this.slot, this.source)));

        this.isDone = true;
    }
}
