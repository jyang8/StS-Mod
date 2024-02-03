package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.tempCards.Bread;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class SourdoughBread extends BaseCard {
    public static final String ID = makeID(SourdoughBread.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int NUM_GENERATED = 2;
    private static final int UPGRADE_NUM_GENERATED = 1;
    private static final int BLOCK = 3;

    public SourdoughBread() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        this.cardsToPreview = new Bread();
        setMagic(NUM_GENERATED, UPGRADE_NUM_GENERATED);
        setBlock(BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { return new SourdoughBread(); }
}
