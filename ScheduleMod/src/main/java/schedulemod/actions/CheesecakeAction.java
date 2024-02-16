package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.character.Entropy;
import schedulemod.powers.MaxSatietyPower;
import schedulemod.powers.SatietyPower;

public class CheesecakeAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int amount;

    public CheesecakeAction(AbstractPlayer p, int amount) {
        this.p = p;
        this.amount = amount;
    }
    public void update() {
        int bellySize = p.hasPower(MaxSatietyPower.POWER_ID) ? p.getPower(MaxSatietyPower.POWER_ID).amount : 0;
        int maxSatiety = (p instanceof Entropy) ? ((Entropy)p).maxSatiety : 1;
        int satiety = p.hasPower(SatietyPower.POWER_ID) ? p.getPower(SatietyPower.POWER_ID).amount : 0;
        if (satiety + 1 >= maxSatiety + bellySize) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
        }
        this.isDone = true;
    }
}
