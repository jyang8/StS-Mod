package schedulemod.cards.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.BirthdayAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.patches.EventsPlayedPatch;
import schedulemod.util.CardStats;

public class TheSong extends EventCard {
    public static final String ID = makeID(TheSong.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            2);

    private static final int DAMAGE = 10;
    private static final int DAMAGE_INCREASE = 5;
    private static final int UPGRADE_DAMAGE_INCREASE = 2;

    public TheSong() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        ArrayList<EventCard> events = EventsPlayedPatch.EventsPlayedThisCombatField.eventsPlayedThisCombat
                .get(AbstractDungeon.actionManager);
        int increase = 0;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i) instanceof DreadfulStrikes) {
                if (events.get(i).upgraded) {
                    increase += UPGRADE_DAMAGE_INCREASE;
                } else {
                    increase += DAMAGE_INCREASE;
                }
            }
        }
        setDamage(DAMAGE + increase);
        setMagic(DAMAGE_INCREASE, UPGRADE_DAMAGE_INCREASE);
        this.damageType = DamageType.THORNS;
        this.damageTypeForTurn = DamageType.THORNS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
        addToBot(new BirthdayAction(this, this.magicNumber));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
        addToBot(new BirthdayAction(this, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TheSong();
    }
}
