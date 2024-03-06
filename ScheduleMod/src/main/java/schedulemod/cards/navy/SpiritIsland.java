package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.patches.OnTurnEndedEarlyPatch.PrevTurnEndedEarlyField;
import schedulemod.util.CardStats;

public class SpiritIsland extends BaseCard {
    public static final String ID = makeID(SpiritIsland.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1);

    private static final int ATTACK_DAMAGE = 7;
    private static final int UPGRADE_ATTACK_DAMAGE = 2;
    private static final int REPEATS = 2;

    public SpiritIsland() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(REPEATS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn,
                AbstractGameAction.AttackEffect.FIRE));
        if (PrevTurnEndedEarlyField.endedEarly.get(AbstractDungeon.actionManager)) {
            for (int i = 0; i < magicNumber; i++) {
                addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.LIGHTNING));
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (PrevTurnEndedEarlyField.endedEarly.get(AbstractDungeon.actionManager)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } 
      }

    @Override
    public AbstractCard makeCopy() {
        return new SpiritIsland();
    }
}
