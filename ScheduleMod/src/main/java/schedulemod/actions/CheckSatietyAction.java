package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import schedulemod.character.Entropy;
import schedulemod.powers.HokkaidoUniBuffetPower;
import schedulemod.powers.MaxSatietyPower;
import schedulemod.powers.SatietyPower;
import schedulemod.relics.HungryCamera;

public class CheckSatietyAction extends AbstractGameAction {
    private boolean fromUni = false;

    public CheckSatietyAction(boolean fromUni) {
        this.fromUni = true;
    }

    public CheckSatietyAction() {
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        int bellySize = p.hasPower(MaxSatietyPower.POWER_ID) ? p.getPower(MaxSatietyPower.POWER_ID).amount : 0;
        int satiety = p.hasPower(SatietyPower.POWER_ID) ? p.getPower(SatietyPower.POWER_ID).amount : 0;
        boolean skipCheck = p.hasPower(HokkaidoUniBuffetPower.POWER_ID) && !fromUni;
        if (!skipCheck && satiety > 0) {
            if (satiety >= (p instanceof Entropy ? ((Entropy) p).maxSatiety : 1) + bellySize) {
                p.getPower(SatietyPower.POWER_ID).flashWithoutSound();
                addToTop(new RemoveSpecificPowerAction(p, p, SatietyPower.POWER_ID));
                if (p.hasRelic(HungryCamera.ID)) {
                    ((HungryCamera) p.getRelic(HungryCamera.ID)).triggered = true;
                }
                if (!AbstractDungeon.actionManager.turnHasEnded) {
                    addToBot(new PressEndTurnButtonAction());
                }
            }
        }
        this.isDone = true;
    }
}
