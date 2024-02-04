package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.powers.SleepPower;

public class SleepAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_FAST;

    public SleepAction(AbstractMonster target, AbstractCreature source) {
        this(target, source, 0);
    }

    public SleepAction(AbstractMonster target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
        this.actionType = ActionType.DEBUFF;
        this.duration = DURATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new SleepPower((AbstractMonster)this.target, (AbstractCreature)this.source, this.amount), this.amount));
        }
        this.tickDuration();
    }
}
