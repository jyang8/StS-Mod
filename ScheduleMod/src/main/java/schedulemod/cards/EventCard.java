package schedulemod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.navy.BaseCard;
import schedulemod.powers.PunctualPower;
import schedulemod.util.CardStats;

public abstract class EventCard extends BaseCard {

    public AbstractCard triggeringCard;

    public EventCard(String ID, CardStats info) {
        super(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor);
    }

    public EventCard(String ID, CardStats info, boolean upgradesDescription) {
        super(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, upgradesDescription);
    }

    public EventCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color) {
        super(ID, cost, cardType, target, rarity, color);

    }

    public EventCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color,
            boolean upgradesDescription) {
        super(ID, cost, cardType, target, rarity, color, upgradesDescription);
    }

    public abstract void useEvent(AbstractPlayer p, AbstractMonster m);

    public void useEvent(AbstractPlayer p, AbstractMonster m, AbstractCard triggeringCard) {
        this.triggeringCard = triggeringCard;
        useEvent(p, m);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (AbstractDungeon.player.hasPower(PunctualPower.POWER_ID)) {
            this.damage += AbstractDungeon.player.getPower(PunctualPower.POWER_ID).amount;
        }
    }
}
