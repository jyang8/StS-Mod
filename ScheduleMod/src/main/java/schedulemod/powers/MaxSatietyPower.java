package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import schedulemod.character.Entropy;

import static schedulemod.BasicMod.makeID;

public class MaxSatietyPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("MaxSatiety");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public MaxSatietyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
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
        if (AbstractDungeon.player instanceof Entropy) {
            ((Entropy) AbstractDungeon.player).setMaxSatiety(this.amount, true);
        }
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
        if (AbstractDungeon.player instanceof Entropy) {
            ((Entropy) AbstractDungeon.player).setMaxSatiety(this.amount, true);
        }
    }

    public void updateDescription() {
        AbstractPlayer p = AbstractDungeon.player;
        int max = p instanceof Entropy ? ((Entropy)p).maxSatiety : 1;
        if (max > 0) {
            this.description = DESCRIPTIONS[0] + max + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new MaxSatietyPower(owner, amount);
    }
}
