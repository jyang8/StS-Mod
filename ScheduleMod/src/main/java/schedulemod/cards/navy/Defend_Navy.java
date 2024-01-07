package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Defend_Navy extends BaseCard {

    public static final String ID = makeID(Defend_Navy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    public Defend_Navy() {
        super(ID, info);
        tags.add(CardTags.STARTER_DEFEND);
        setBlock(BLOCK, UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.isDebug) {
            addToBot(new GainBlockAction(p, p, 50));
        } else {
            addToBot(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Defend_Navy(); }
}
