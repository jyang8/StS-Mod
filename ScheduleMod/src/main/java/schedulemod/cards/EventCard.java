package schedulemod.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import schedulemod.cards.navy.BaseCard;
import schedulemod.character.Entropy;
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

    public void onSchedule() {
    }

    @Override
    public void render(SpriteBatch sb) {
        calculateCardDamage(null);
        super.render(sb);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            super.calculateCardDamage(m);
            if (AbstractDungeon.player.hasPower(PunctualPower.POWER_ID)) {
                int amount = AbstractDungeon.player.getPower(PunctualPower.POWER_ID).amount;
                this.damage = this.baseDamage + amount;
                this.isDamageModified = this.damage != this.baseDamage;
                this.block = this.baseBlock + amount;
                this.isBlockModified = this.block != this.baseBlock;
                if (this.tags.contains(Entropy.Enums.FATIGUE_EVENT) || this.tags.contains(Entropy.Enums.AMP_EVENT)) {
                    this.magicNumber = this.baseMagicNumber + amount;
                    this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
                }
            }
        }
    }
}
