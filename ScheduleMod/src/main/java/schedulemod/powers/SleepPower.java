package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;

import static schedulemod.BasicMod.makeID;

public class SleepPower extends BasePower implements CloneablePowerInterface, OnReceivePowerPower, BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID("Sleep");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private byte moveByte;
    private AbstractMonster.Intent moveIntent;
    private EnemyMoveInfo move;

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
//        if (this.owner.getPower(makeID("Fatigue")).amount == ((AbstractMonster)this.owner).getIntentDmg()) {
//            addToBot(new ApplyPowerAction(this.owner, this.source, new FatiguePower(this.owner, this.source, -this.amount), -this.amount));
//            addToBot(new RemoveSpecificPowerAction(this.owner, this.source, makeID("Fatigue")));
//        } else if (this.owner.getPower(makeID("Fatigue")).amount > ((AbstractMonster)this.owner).getIntentDmg()) {
//            addToBot(new ApplyPowerAction(this.owner, this.source, new FatiguePower(this.owner, this.source, -this.amount), -this.amount));
//        } else {
//            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
//        }
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower instanceof FatiguePower) {
            return this.source.hasPower(makeID("SnoozeAlarm"));
        }
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power instanceof FatiguePower && !this.source.hasPower(makeID("SnoozeAlarm"))) {
            return 0;
        }
        return stackAmount;
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (SleepPower.this.owner instanceof AbstractMonster) {
                    SleepPower.this.moveByte = ((AbstractMonster)SleepPower.this.owner).nextMove;
                    SleepPower.this.moveIntent = ((AbstractMonster)SleepPower.this.owner).intent;
                    try {
                        Field f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        SleepPower.this.move = (EnemyMoveInfo)f.get(SleepPower.this.owner);
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
            if (this.move != null) {
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);
            } else {
                m.setMove(this.moveByte, this.moveIntent);
            }
            m.createIntent();
            m.applyPowers();
        }
    }

    public AbstractPower makeCopy() { return new SleepPower((AbstractMonster)owner, source, amount); }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        if (((AbstractPlayer)source).hasRelic(makeID("Blahaj")))
            addToBot(new GainEnergyAction(1));
        return true;
    }
}
