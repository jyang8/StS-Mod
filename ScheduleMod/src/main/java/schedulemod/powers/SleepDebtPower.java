package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import schedulemod.actions.FatigueLoseHpAction;

import static schedulemod.BasicMod.makeID;

public class SleepDebtPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SleepDebt");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public SleepDebtPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 4;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                String FATIGUE = makeID("Fatigue");
                if (m.hasPower(FATIGUE)) {
                    flashWithoutSound();
                    addToBot(new FatigueLoseHpAction(m, this.owner, m.getPower(FATIGUE).amount * this.amount, AbstractGameAction.AttackEffect.POISON));
                }
            }
        }
    }

    @Override
    public AbstractPower makeCopy() { return new SleepDebtPower(owner, amount); }
}
