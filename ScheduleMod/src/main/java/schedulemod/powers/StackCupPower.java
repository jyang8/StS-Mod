package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Drink;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class StackCupPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("StackCup");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static final int SCHEDULE_SLOT = 2;

    public StackCupPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, -1);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onLoseHp(int damageAmount) {
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            addToBot((AbstractGameAction) new ScheduleEventCard((AbstractCard) new Drink(), SCHEDULE_SLOT));
        }
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new StackCupPower(owner, source);
    }
}
