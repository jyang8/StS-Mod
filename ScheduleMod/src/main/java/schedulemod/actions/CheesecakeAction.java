package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CheesecakeAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int amount;

    public CheesecakeAction(AbstractPlayer p, int amount) {
        this.p = p;
        this.amount = amount;
    }

    public void update() {
        if (AbstractDungeon.actionManager.turnHasEnded) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
        }
        this.isDone = true;
    }
}
