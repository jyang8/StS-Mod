package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.tempCards.Bomb;
import schedulemod.character.Entropy;
import schedulemod.powers.BombePower;
import schedulemod.util.CardStats;

public class Bombe extends BaseCard {
    public static final String ID = makeID(Bombe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Bombe() {
        super(ID, info);
        this.cardsToPreview = new Bomb();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.cardsToPreview.upgrade();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BombePower(p, this.upgraded)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bombe();
    }
}
