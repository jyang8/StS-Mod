package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class XChairMethod extends BaseCard {
    public static final String ID = makeID(XChairMethod.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            -1);

    private static final int FATIGUE = 6;

    public XChairMethod() {
        super(ID, info);
        setMagic(FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int tmp = this.energyOnUse;
        if (this.upgraded)
            tmp++;

        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            tmp += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new FatiguePower(mo, p, this.magicNumber * tmp)));
        }
        if (!this.freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);
    }

    @Override
    public AbstractCard makeCopy() {
        return new XChairMethod();
    }
}
