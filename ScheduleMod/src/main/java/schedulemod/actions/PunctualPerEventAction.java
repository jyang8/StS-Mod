package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.orbs.ScheduleOrb;
import schedulemod.powers.PunctualPower;

public class PunctualPerEventAction extends AbstractGameAction {
    private final float DURATION = Settings.ACTION_DUR_FAST;
    private int amount;

    public PunctualPerEventAction(int amount) {
        this.duration = DURATION;
        this.amount = amount;
    }

    public void update() {
        int tmp = 0;
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof ScheduleOrb)
                tmp++;
        }
        tmp *= amount;
        if (tmp != 0) {
            addToBot(new ApplyPowerAction(p, p, new PunctualPower(p, p, tmp), tmp));
        }
        this.isDone = true;
    }
}
