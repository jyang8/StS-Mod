package schedulemod.powers;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPlayerTurnStartPostDrawSubscriber;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;
import schedulemod.actions.SleepAction;

import static schedulemod.BasicMod.makeID;

import java.lang.reflect.Field;

public class FatiguePower extends BasePower implements CloneablePowerInterface, OnPlayerTurnStartPostDrawSubscriber {
    public static final String POWER_ID = makeID("Fatigue");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public FatiguePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        BaseMod.subscribe(this);
    }

    @Override
    public void onRemove() {
        BaseMod.unsubscribe(this);
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
        applySleep();
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
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower p, AbstractCreature target, AbstractCreature source) {
        if (!(p instanceof FatiguePower)) {
            applySleep();
        }
    }

    @Override
    public void atStartOfTurn() {
        applySleep();
    }

    @Override
    public void onInitialApplication() {
        applySleep();
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        applySleep();
    }

    private void applySleep() {
        if (!(this.owner instanceof AbstractMonster) || this.owner.hasPower(SleepPower.POWER_ID))
            return;
        AbstractMonster m = (AbstractMonster) this.owner;
        boolean isAttack = m.intent == Intent.ATTACK ||
                m.intent == Intent.ATTACK_BUFF ||
                m.intent == Intent.ATTACK_DEBUFF ||
                m.intent == Intent.ATTACK_DEFEND;
        if (!isAttack)
            return;
        int dmg = m.getIntentDmg();
        try {
            Field f = AbstractMonster.class.getDeclaredField("intentMultiAmt");
            f.setAccessible(true);
            if ((int) f.get(m) > 0)
                dmg *= (int) f.get(m);
        } catch (NoSuchFieldException | IllegalAccessException var3) {
            var3.printStackTrace();
        }

        if (m.getIntentDmg() >= 0
                && amount >= dmg
                && isAttack) {
            addToTop(new SleepAction((AbstractMonster) this.owner, this.source));
            if (!this.owner.hasPower(SnoozeAlarmPower.POWER_ID)) {
                addToTop(new RemoveSpecificPowerAction(this.owner, this.source, POWER_ID));
            } else {
                addToTop(new ApplyPowerAction(this.owner, this.source,
                        new FatiguePower(this.owner, this.source, -this.amount), -this.amount));
            }           
            addToTop(new SleepAction((AbstractMonster) this.owner, this.source));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FatiguePower(owner, source, amount);
    }
}
