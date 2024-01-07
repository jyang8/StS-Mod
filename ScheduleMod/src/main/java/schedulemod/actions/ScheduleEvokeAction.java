package schedulemod.actions;

import static schedulemod.BasicMod.logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.orbs.ScheduleOrb;

public class ScheduleEvokeAction extends AbstractGameAction {
    private ScheduleOrb orb;
    private AbstractCard triggeringCard;
    private AbstractCreature triggeringCardTarget;

    public ScheduleEvokeAction(ScheduleOrb orb, AbstractCard triggeringCard, AbstractCreature triggeringCardTarget) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orb = orb;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.triggeringCard = triggeringCard;
        this.triggeringCardTarget = triggeringCardTarget;
    }

    public void update() {
        if (!triggeringCard.purgeOnUse) {
            logger.info("Evoking card:" + triggeringCard + "," + triggeringCardTarget);
            AbstractMonster m = null;
            if (orb.eventCard.target == CardTarget.ENEMY) {
                // Try using the trigger target
                if (triggeringCardTarget != null
                        && triggeringCardTarget instanceof AbstractMonster
                        && !triggeringCardTarget.isDeadOrEscaped()) {
                    m = (AbstractMonster) triggeringCardTarget;
                } else {
                    // If that doesn't work then use a random alive monster
                    m = (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true,
                            AbstractDungeon.cardRandomRng);
                }
            }
            AbstractCard tmp = orb.eventCard.makeStatEquivalentCopy();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            if (m != null)
                tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(
                    new CardQueueItem(tmp, m, orb.eventCard.energyOnUse, true, true),
                    true);
        }
        this.isDone = true;
    }
}
