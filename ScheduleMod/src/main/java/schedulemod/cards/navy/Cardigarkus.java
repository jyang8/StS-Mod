package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Cardigarkus extends BaseCard {
    public static final String ID = makeID(Cardigarkus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 14;
    private static final int UPGRADE_BLOCK = 5;
    private static final int DEXTERITY_LOSS = 1;

    public Cardigarkus() {
        super(ID, info);
        setBlock(BLOCK, UPGRADE_BLOCK);
        setMagic(DEXTERITY_LOSS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -DEXTERITY_LOSS)));
    }

    @Override
    public AbstractCard makeCopy() { return new Cardigarkus(); }
}
