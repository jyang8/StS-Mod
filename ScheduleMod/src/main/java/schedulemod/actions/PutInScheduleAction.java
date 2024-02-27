package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StasisPower;

import schedulemod.BasicMod;
import schedulemod.cards.tempCards.Repossess;

import java.util.Iterator;

public class PutInScheduleAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    public static int numPlaced;
    private int slot;
    private AbstractCard card;

    public PutInScheduleAction(AbstractCreature target, AbstractCreature source, boolean isRandom,
            int slot) {
        this.target = target;
        this.p = (AbstractPlayer) target;
        this.setValues(target, source);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.isRandom = isRandom;
        this.slot = slot;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (this.p.hand.size() < 1) {
                this.isDone = true;
                return;
            }
            if (this.isRandom) {
                card = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                this.p.hand.removeCard(card);
                addToBot(new ScheduleEventCard(new Repossess(card.makeStatEquivalentCopy()), this.slot));
                card.setAngle(0.0F);
                card.targetDrawScale = 0.75F;
                card.target_x = Settings.WIDTH / 2.0F;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.lighten(false);
                card.unfadeOut();
                card.unhover();
                card.untip();
                card.stopGlowing();

            } else {
                if (this.p.hand.group.size() > this.amount) {
                    numPlaced = this.amount;
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                    this.tickDuration();
                    return;
                }

                card = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                this.p.hand.removeCard(card);
                addToBot(new ScheduleEventCard(new Repossess(card.makeStatEquivalentCopy()), this.slot));
                card.setAngle(0.0F);
                card.targetDrawScale = 0.75F;
                card.target_x = Settings.WIDTH / 2.0F;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.lighten(false);
                card.unfadeOut();
                card.unhover();
                card.untip();
                card.stopGlowing();

            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator var3 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var3.hasNext()) {
                card = (AbstractCard) var3.next();
                this.p.hand.removeCard(card);
                addToBot(new ScheduleEventCard(new Repossess(card.makeStatEquivalentCopy()), this.slot));
                card.setAngle(0.0F);
                card.targetDrawScale = 0.75F;
                card.target_x = Settings.WIDTH / 2.0F;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.lighten(false);
                card.unfadeOut();
                card.unhover();
                card.untip();
                card.stopGlowing();
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
        if (this.isDone && this.card != null) {
          addToTop((AbstractGameAction)new ShowCardAction(this.card));
        } 
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(BasicMod.makeID(PutInScheduleAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
