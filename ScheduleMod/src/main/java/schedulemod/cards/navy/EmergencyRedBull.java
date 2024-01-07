package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
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
            0
    );

    private static final int ENERGY = 2;
    private static final int UPGRADE_ENERGY = 1;
    private static final int HP_LOSS = 3;

    public EmergencyRedBull() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        this.cardsToPreview = new JagerBomb();
        setMagic(ENERGY, UPGRADE_ENERGY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOSS));
        addToBot(new GainEnergyAction(this.magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview, 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new EmergencyRedBull();
    }
}
