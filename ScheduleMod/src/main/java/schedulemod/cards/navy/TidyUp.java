package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.TidyUpPower;
import schedulemod.util.CardStats;

public class TidyUp extends BaseCard {
    public static final String ID = makeID(TidyUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    private static final int PUNCTUAL = 2;
    private static final int UPGRADE_PUNCTUAL = 1;


    public TidyUp() {
        super(ID, info);
        setMagic(PUNCTUAL, UPGRADE_PUNCTUAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new TidyUpPower(p, this.magicNumber)));
        addToBot(new PressEndTurnButtonAction());

    }

    @Override
    public AbstractCard makeCopy() { return new TidyUp(); }
}
