package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class SpiritIsland extends BaseCard {
    public static final String ID = makeID(SpiritIsland.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 8;
    private static final int ATTACK_ALL_DAMAGE = 10;
    private static final int UPGRADE_ATTACK_ALL_DAMAGE = 2;

    public SpiritIsland() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE);
        setMagic(ATTACK_ALL_DAMAGE, UPGRADE_ATTACK_ALL_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        boolean skillPlayed = false;
        boolean powerPlayed = false;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.SKILL) {
                skillPlayed = true;
            }
            else if (c.type == CardType.POWER) {
                powerPlayed = true;
            }
        }
        if (skillPlayed && powerPlayed) {
            addToBot(new DamageAllEnemiesAction(p, this.magicNumber, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SpiritIsland(); }
}
