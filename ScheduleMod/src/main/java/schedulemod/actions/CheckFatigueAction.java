package schedulemod.actions;

import java.lang.reflect.Field;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

import schedulemod.powers.FatiguePower;
import schedulemod.powers.SleepPower;
import schedulemod.powers.SnoozeAlarmPower;

public class CheckFatigueAction extends AbstractGameAction {
    private AbstractMonster m;

    public CheckFatigueAction(AbstractMonster m, AbstractCreature source) {
        this.m = m;
        this.source = source;
    }

    public void update() {
        if (m.hasPower(SleepPower.POWER_ID) || !m.hasPower(FatiguePower.POWER_ID)) {
            this.isDone = true;
            return;

        }

        try {
            Field f = AbstractMonster.class.getDeclaredField("move");
            f.setAccessible(true);
            if (((EnemyMoveInfo) f.get(m)).baseDamage == -1) {
                this.isDone = true;
                return;
            }
        } catch (NoSuchFieldException | IllegalAccessException var3) {
            var3.printStackTrace();
        }

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
                && m.getPower(FatiguePower.POWER_ID).amount >= dmg) {
            addToTop(new SleepAction((AbstractMonster) m, this.source));

            if (!this.source.hasPower(SnoozeAlarmPower.POWER_ID)) {
                addToTop(new RemoveSpecificPowerAction(m, this.source, FatiguePower.POWER_ID));
            } else {
                addToTop(new ApplyPowerAction(m, this.source,
                        new FatiguePower(m, this.source, -dmg), -dmg));
            }
        }
        this.isDone = true;
    }
}
