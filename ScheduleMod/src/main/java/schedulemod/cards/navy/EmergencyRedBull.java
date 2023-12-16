package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.tempCards.JagerBomb;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class EmergencyRedBull extends BaseCard {
    public static final String ID = makeID(EmergencyRedBull.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int ENERGY = 2;

    public EmergencyRedBull() {
        super(ID, info);
        this.cardsToPreview = new JagerBomb();
        setMagic(ENERGY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 3));
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new EmergencyRedBull();
    }
}
