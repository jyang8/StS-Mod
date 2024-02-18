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
    private int satiety;

    public CheesecakeAction(AbstractPlayer p, int satiety, int amount) {
        this.p = p;
        this.amount = amount;
        this.satiety = satiety;
    }
    public void update() {
        int bellySize = p.hasPower(MaxSatietyPower.POWER_ID) ? p.getPower(MaxSatietyPower.POWER_ID).amount : 0;
        int maxSatiety = (p instanceof Entropy) ? ((Entropy)p).maxSatiety : 1;
        int currSatiety = p.hasPower(SatietyPower.POWER_ID) ? p.getPower(SatietyPower.POWER_ID).amount : 0;
        if (currSatiety + this.satiety >= maxSatiety + bellySize) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
        }
        this.isDone = true;
    }
}
