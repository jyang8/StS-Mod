package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static schedulemod.BasicMod.makeID;

public class UnderstandPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Understand");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private int strengthLoss;

    public UnderstandPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cost == 0) {
            if (!this.owner.hasPower("Artifact"))
                strengthLoss += this.amount;
            addToBot(new ApplyPowerAction(this.owner, this.source, new StrengthPower(this.owner, -this.amount), -this.amount));
            addToBot(new ApplyPowerAction(this.owner, this.source, new GainStrengthPower(this.owner, strengthLoss), strengthLoss));
        }
    }

    @Override
    public AbstractPower makeCopy() { return new UnderstandPower(owner, source, amount); }
}
