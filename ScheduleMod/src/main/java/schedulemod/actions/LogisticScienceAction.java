package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.powers.LoseMaxSatietyPower;
import schedulemod.powers.MaxSatietyPower;

public class LogisticScienceAction extends AbstractGameAction {
    private final float DURATION = Settings.ACTION_DUR_FAST;
    private int amount;

    public LogisticScienceAction(int amount) {
        this.duration = DURATION;
        this.amount = amount;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount, true));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, amount), amount, true));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, amount), amount, true));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, amount), amount, true));
        addToBot(new ApplyPowerAction(p, p, new MaxSatietyPower(p, amount), amount, true));
        addToBot(new ApplyPowerAction(p, p, new LoseMaxSatietyPower(p, amount), amount, true));
        this.isDone = true;
    }
}
