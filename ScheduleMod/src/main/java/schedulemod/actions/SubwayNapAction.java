package schedulemod.actions;

import java.lang.reflect.Field;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import schedulemod.powers.FatiguePower;
import schedulemod.powers.SleepPower;

public class SubwayNapAction extends AbstractGameAction {
    private AbstractMonster m;
    private int amount;

    public SubwayNapAction(AbstractMonster m, int amount) {
        this.m = m;
        this.amount = amount;
    }

    @Override
    public void update() {

        if (m == null) {
            this.isDone = true;
            return;
        }
        
        if (m.hasPower(SleepPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, this.amount), this.amount));
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
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, this.amount), this.amount));
        }
        this.isDone = true;
    }
}
