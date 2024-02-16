package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static schedulemod.BasicMod.makeID;

public class UnderstandPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Understand");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public UnderstandPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cost == 0) {
            flash();
            addToBot(new LoseHPAction(this.owner, null, this.amount));
        }
    }

    @Override
    public AbstractPower makeCopy() { return new UnderstandPower(owner, source, amount); }
}
