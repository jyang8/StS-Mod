package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import schedulemod.cards.navy.HyperfloorEvent;
import schedulemod.cards.navy.HyperfloorMonster;
import schedulemod.cards.navy.HyperfloorShop;
import schedulemod.character.Entropy;
import schedulemod.patches.HyperfloorPatch;

import java.util.ArrayList;
import java.util.Arrays;

public class HyperEntropyAction extends AbstractGameAction {
    private static final ArrayList<AbstractCard> hyperfloorCards = new ArrayList<AbstractCard>(Arrays.asList(new HyperfloorMonster(), new HyperfloorEvent(), new HyperfloorShop())); 
    private static final float DURATION = 0.1F;

    public HyperEntropyAction(Entropy source) {
        this.actionType = ActionType.SPECIAL;
        this.duration = DURATION;
    }

    public void update() {
        if (this.duration == 0.1F) {
            RewardItem rItem = new RewardItem();
            rItem.type = HyperfloorPatch.HYPERFLOOR;
            rItem.cards = hyperfloorCards;
            rItem.text = "Open a Hyperfloor";
            AbstractDungeon.currMapNode.room.rewards.add(0, rItem);
        }
        tickDuration();
    }
}
