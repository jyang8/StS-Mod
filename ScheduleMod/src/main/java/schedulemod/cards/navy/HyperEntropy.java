package schedulemod.cards.navy;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.HyperEntropyAction;
import schedulemod.actions.LoseMaxHpAction;
import schedulemod.character.Entropy;
import schedulemod.patches.HyperfloorPatch.RewardItemClaimPatch.AbstractRoomIsHyperfloorField;
import schedulemod.util.CardStats;

public class HyperEntropy extends BaseCard {
    public static final String ID = makeID(HyperEntropy.class.getSimpleName());

    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            3
    );
    private static final boolean EXHAUST = true;

    private static final int MAX_HP_LOSS = 2;
    private static final int UPGRADE_MAX_HP_LOSS = -1;

    public HyperEntropy() {
        super(ID, info);
        setExhaust(EXHAUST);
        setMagic(MAX_HP_LOSS, UPGRADE_MAX_HP_LOSS);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return canUse;
        }
        if (AbstractRoomIsHyperfloorField.isHyperfloor.get(AbstractDungeon.currMapNode.room)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HyperEntropyAction((Entropy)p));
        addToBot(new LoseMaxHpAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new HyperEntropy(); }
}
