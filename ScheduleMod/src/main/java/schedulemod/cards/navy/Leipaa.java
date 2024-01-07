package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.tempCards.Bread;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Leipaa extends BaseCard {
    public static final String ID = makeID(Leipaa.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int TIMES_ATTACK = 3;
    private static final int UPGRADE_TIMES_ATTACK = 1;

    public Leipaa() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setMagic(TIMES_ATTACK, UPGRADE_TIMES_ATTACK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int breadCount = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c instanceof Bread)
                breadCount++;
        }
        setDamage(this.magicNumber * breadCount);
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
    }

    @Override
    public void applyPowers() {
        int breadCount = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c instanceof Bread)
                breadCount++;
        }
        if (breadCount > 0) {
            setDamage(this.magicNumber * breadCount);
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { return new Leipaa(); }
}
