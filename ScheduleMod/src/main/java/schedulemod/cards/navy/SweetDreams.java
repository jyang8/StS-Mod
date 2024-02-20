package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.SweetDreamsPower;
import schedulemod.util.CardStats;

public class SweetDreams extends BaseCard {
    public static final String ID = makeID(SweetDreams.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 2;

    public SweetDreams() {
        super(ID, info);
        setMagic(BLOCK, UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SweetDreamsPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new SweetDreams(); }
}
