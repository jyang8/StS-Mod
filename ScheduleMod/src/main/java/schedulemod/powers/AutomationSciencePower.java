package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static schedulemod.BasicMod.makeID;

public class AutomationSciencePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("AutomationScience");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private int damageReq;
    private boolean upgraded;

    public AutomationSciencePower(AbstractCreature owner, int amount, int damageReq, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.damageReq = damageReq;
        this.upgraded = upgraded;
        updateDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount >= this.damageReq) {
            if (this.upgraded) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, damageAmount/this.damageReq)));
            } else {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1)));
            }
        }
    }

    public void updateDescription() {
        if (!this.upgraded) {
            this.description = DESCRIPTIONS[0] + this.damageReq + DESCRIPTIONS[1] + DESCRIPTIONS[2] + LocalizedStrings.PERIOD;
        } else {
            this.description = DESCRIPTIONS[0] + this.damageReq + DESCRIPTIONS[1] + DESCRIPTIONS[2] + this.damageReq + DESCRIPTIONS[3];        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AutomationSciencePower(this.owner, this.amount, this.damageReq, this.upgraded);
    }
}
