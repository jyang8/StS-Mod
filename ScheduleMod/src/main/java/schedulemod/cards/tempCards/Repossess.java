package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Repossess extends EventCard {
    public static final String ID = makeID(Repossess.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);

    public Repossess() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
    }
    
    public Repossess(AbstractCard target) {
        this();
        this.cardsToPreview = target;
        if (this.cardsToPreview != null) {
            this.rawDescription = cardStrings.DESCRIPTION + " (" + this.cardsToPreview.name + ")";
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        if (this.cardsToPreview != null) {
            AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
            if (upgraded && c.canUpgrade()) {
                c.upgrade();
                c.superFlash();
                c.applyPowers();
            }
            addToBot((AbstractGameAction) new MakeTempCardInHandAction(c, false));
        }
    }

    
    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (this.cardsToPreview != null) {
            this.rawDescription = cardStrings.DESCRIPTION + " (" + this.cardsToPreview.name + ")";
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Repossess(this.cardsToPreview);
    }

}
