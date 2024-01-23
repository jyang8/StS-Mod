package schedulemod.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.navy.BaseCard;
import schedulemod.util.CardStats;

public abstract class EventCard extends BaseCard {
      
  public EventCard(String ID, CardStats info) {
        super(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor);
    }
    public EventCard(String ID, CardStats info, boolean upgradesDescription) {
        super(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, upgradesDescription);
    }
    public EventCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color)
    {
        super(ID, cost, cardType, target, rarity, color);
 
    }
    public EventCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, boolean upgradesDescription)
    {
        super(ID, cost, cardType, target, rarity, color, upgradesDescription);
    }

  public abstract void useEvent(AbstractPlayer p, AbstractMonster m, AbstractCard triggeringCard);
}
