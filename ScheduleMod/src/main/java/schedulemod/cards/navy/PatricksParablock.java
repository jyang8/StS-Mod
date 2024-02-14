package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.WrinklerPower;
import schedulemod.util.CardStats;

public class PatricksParablock extends BaseCard {
    public static final String ID = makeID(PatricksParablock.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 4;
    private static final int WRINKLER = 2;

    public PatricksParablock() {
        super(ID, info);
        setBlock(BLOCK, UPGRADE_BLOCK);
        setMagic(WRINKLER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new WrinklerPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new PatricksParablock(); }
}
