package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static schedulemod.BasicMod.makeID;

public class PunctualPower extends BasePower implements CloneablePowerInterface, EventPowerInterface {
    public static final String POWER_ID = makeID("Punctual");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static final boolean CAN_GO_NEGATIVE = true;

    public PunctualPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.canGoNegative = CAN_GO_NEGATIVE;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
            this.type = AbstractPower.PowerType.BUFF;
          } else {
            int tmp = -this.amount;
            this.description = DESCRIPTIONS[1] + DESCRIPTIONS[2] + tmp + DESCRIPTIONS[3];
            this.type = AbstractPower.PowerType.DEBUFF;
          } 
    }

    @Override
    public AbstractPower makeCopy() {
        return new PunctualPower(owner, source, amount);
    }

    public void modifyEvent(EventCard eventCard, AbstractCard triggeringCard) {
        eventCard.damage += this.amount;
        eventCard.block += this.amount;
        if (eventCard.tags.contains(Entropy.Enums.FATIGUE_EVENT)) {
            eventCard.magicNumber += this.amount;
        }
    }
}
