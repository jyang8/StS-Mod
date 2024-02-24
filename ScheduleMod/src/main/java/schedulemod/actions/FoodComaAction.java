package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.powers.FatiguePower;
import schedulemod.powers.SatietyPower;

public class FoodComaAction extends AbstractGameAction {
    private static final int ENERGY_GAIN = 1;
    private static final int CARD_DRAW = 1;
    private static final int SATIETY_THRESHOLD = 2;

    private AbstractPlayer p;
    private AbstractMonster m;
    private int fatigue;

    public FoodComaAction(AbstractPlayer p, AbstractMonster m, int fatigue) {
        this.p = p;
        this.m = m;
        this.fatigue = fatigue;
    }
    public void update() {
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, fatigue)));
        if (p.hasPower(SatietyPower.POWER_ID) && p.getPower(SatietyPower.POWER_ID).amount >= SATIETY_THRESHOLD) {
            addToBot(new GainEnergyAction(ENERGY_GAIN));
            addToBot(new DrawCardAction(CARD_DRAW));
        }
        this.isDone = true;
    }
}
