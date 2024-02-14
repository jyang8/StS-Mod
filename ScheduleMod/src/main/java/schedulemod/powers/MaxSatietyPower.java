package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.actions.CheckSatietyAction;
import schedulemod.character.Entropy;

import static schedulemod.BasicMod.makeID;

public class MaxSatietyPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("MaxSatiety");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static final boolean CAN_GO_NEGATIVE = true;

    public MaxSatietyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = CAN_GO_NEGATIVE;
    }

    private void checkSatiety() {
        addToBot(new CheckSatietyAction());
    }

    public void onInitialApplication() {
        checkSatiety();
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
        checkSatiety();
    }

    @Override
    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
        checkSatiety();
    }
    public void updateDescription() {
        AbstractPlayer p = AbstractDungeon.player;
        int max = p instanceof Entropy ? ((Entropy) p).maxSatiety : 1;
        if (amount > 0) {
            this.type = PowerType.BUFF;
            this.description = DESCRIPTIONS[0] + (max + amount) + DESCRIPTIONS[2];
        } else {
            this.type = PowerType.DEBUFF;
            this.description = DESCRIPTIONS[1] + (max + amount) + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new MaxSatietyPower(owner, amount);
    }
}
