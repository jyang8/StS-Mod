package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.cards.EventCard;
import schedulemod.interfaces.OnEventScheduledPower;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class CalendarTetrisPower extends BasePower implements CloneablePowerInterface, OnEventScheduledPower {
    public static final String POWER_ID = makeID("CalendarTetris");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private int cardsLeftThisTurn = 0;

    public CalendarTetrisPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        cardsLeftThisTurn = amount;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        cardsLeftThisTurn += amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        cardsLeftThisTurn = amount;
    }

    @Override
    public void onEventScheduled(EventCard card, int slot, AbstractOrb replaced) {
        if (cardsLeftThisTurn > 0) {
            flashWithoutSound();
            addToBot(new DrawCardAction(1));
            cardsLeftThisTurn--;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CalendarTetrisPower(owner, amount);
    }
}
