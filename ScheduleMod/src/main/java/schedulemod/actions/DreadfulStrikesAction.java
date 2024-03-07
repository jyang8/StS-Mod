package schedulemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.BasicMod;
import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.DreadfulStrikes;
import schedulemod.orbs.ScheduleOrb;

public class DreadfulStrikesAction extends AbstractGameAction {

    private static int INCREASE = 3;
    private static int UPGRADE_INCREASE = 4;

    public DreadfulStrikesAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractOrb> orbs = p.orbs;
        for (AbstractOrb orb : orbs) {
            if (!(orb instanceof ScheduleOrb)) {
                continue;
            }
            EventCard eventCard = ((ScheduleOrb) orb).eventCard;
            if (eventCard instanceof DreadfulStrikes) {
                BasicMod.logger.info("ATTEMPTING TO INCREASE DREADFUL STRIKES");
                eventCard.baseMagicNumber += eventCard.upgraded ? UPGRADE_INCREASE : INCREASE;
                eventCard.initializeDescription();
                eventCard.calculateCardDamage(null);
                BasicMod.logger.info("MAGIC NUMBER IS: " + eventCard.magicNumber);
            }
        }
        this.isDone = true;
    }
}
