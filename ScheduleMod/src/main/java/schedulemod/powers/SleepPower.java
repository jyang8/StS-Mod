package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;

import static schedulemod.BasicMod.makeID;

public class SleepPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Sleep");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private byte moveByte;

    public SleepPower(AbstractMonster owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.priority = 0;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (SleepPower.this.owner instanceof AbstractMonster) {
                    SleepPower.this.moveByte = ((AbstractMonster)SleepPower.this.owner).nextMove;
                    try {
                        Field f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        EnemyMoveInfo sleepMove = new EnemyMoveInfo(SleepPower.this.moveByte, AbstractMonster.Intent.SLEEP, -1, 0, false);
                        f.set(SleepPower.this.owner, sleepMove);
                        ((AbstractMonster)SleepPower.this.owner).createIntent();
                    } catch (NoSuchFieldException | IllegalAccessException var3) {
                        var3.printStackTrace();
                    }
                }
                this.isDone = true;
            }
        });
    }


    public void onRemove() {
        if (this.owner instanceof AbstractMonster) {
            AbstractMonster m = (AbstractMonster)this.owner;
            m.rollMove();
            m.createIntent();
        }
    }

    public AbstractPower makeCopy() { return new SleepPower((AbstractMonster)owner, source, amount); }
}
