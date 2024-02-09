package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import schedulemod.cards.tempCards.HyperfloorEvent;
import schedulemod.cards.tempCards.HyperfloorMonster;
import schedulemod.cards.tempCards.HyperfloorShop;
import schedulemod.character.Entropy;
import schedulemod.patches.HyperfloorPatch;
import schedulemod.patches.HyperfloorPatch.RewardItemClaimPatch.AbstractRoomIsHyperfloorField;

import java.util.ArrayList;
import java.util.Arrays;

public class HyperEntropyAction extends AbstractGameAction {
    private ArrayList<AbstractCard> hyperfloorCards;
    private static final float DURATION = 0.1F;

    public HyperEntropyAction(Entropy source) {
        this.actionType = ActionType.SPECIAL;
        this.duration = DURATION;
        this.hyperfloorCards = new ArrayList<AbstractCard>(
                Arrays.asList(new HyperfloorMonster(), new HyperfloorEvent(), new HyperfloorShop()));
    }

    public void update() {
        if (this.duration == 0.1F) {
            if (!AbstractRoomIsHyperfloorField.isHyperfloor.get(AbstractDungeon.currMapNode.room)) {
                RewardItem rItem = new RewardItem();
                rItem.type = HyperfloorPatch.HYPERFLOOR;
                rItem.cards = hyperfloorCards;
                rItem.text = "Open a Hyperfloor";
                AbstractDungeon.currMapNode.room.rewards.add(0, rItem);
            }
        }
        tickDuration();
    }
}
