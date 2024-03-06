package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class JagerBomb extends EventCard {
    public static final String ID = makeID(JagerBomb.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            2
    );
    private static final int BLOCK = 15;
    private static final int UPGRADE_BLOCK = 5;
    private static final boolean EXHAUST = true;
    private static final int ENERGY = 1;

    public JagerBomb() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setBlock(BLOCK, UPGRADE_BLOCK);
        setMagic(ENERGY);
        setExhaust(EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        useEvent(p, m);
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(this.magicNumber));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { return new JagerBomb(); }
}
