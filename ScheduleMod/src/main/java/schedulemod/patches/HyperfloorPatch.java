package schedulemod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomType;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import schedulemod.util.TextureLoader;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HyperfloorPatch {
  private static final Logger logger = LogManager.getLogger(RewardItem.class.getName());

  @SpireEnum
  public static RewardItem.RewardType HYPERFLOOR;

  private static final String hyperfloorRewardTexturePath = "schedulemod/images/ui/hyperfloorReward.png";
  private static final String hyperfloorProceedLabel = "Hyperfloor";

  @SpirePatch(clz = RewardItem.class, method = SpirePatch.CLASS)
  public static class RewardItemHyperfloorTypeField {
    public static SpireField<AbstractRoom.RoomType> hyperfloorType = new SpireField<>(() -> RoomType.MONSTER);
  }

  @SpirePatch(clz = RewardItem.class, method = "claimReward")
  public static class RewardItemClaimPatch {

    @SpirePrefixPatch
    public static SpireReturn<Boolean> Prefix(RewardItem obj) {
      if (obj.type == HyperfloorPatch.HYPERFLOOR) {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
          AbstractDungeon.cardRewardScreen.open(obj.cards, obj, "Choose a Hyperfloor");
          AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        ProceedButtonToHyperfloorField.toHyperfloor.set(AbstractDungeon.overlayMenu.proceedButton, true);
        return SpireReturn.Return(true);
      } else {
        return SpireReturn.Continue();
      }
    }

    @SpirePatch(clz = RewardItem.class, method = "render")
    public static class RewardItemRenderPatch {

      public static Texture UI_HYPERFLOOR = TextureLoader.getTexture(hyperfloorRewardTexturePath);

      @SpirePostfixPatch
      public static void Postfix(RewardItem obj, SpriteBatch sb) {
        if (obj.type == HyperfloorPatch.HYPERFLOOR) {
          sb.setColor(Color.WHITE);
          sb.draw(UI_HYPERFLOOR, RewardItem.REWARD_ITEM_X - 32.0F, obj.y - 32.0F - 2.0F * Settings.scale, 32.0F, 32.0F,
              64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
      }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "acquireCard", paramtypez = { AbstractCard.class })
    public static class HyperfloorCardRewardScreenPatch {

      @SpirePrefixPatch
      public static SpireReturn<Void> Prefix(CardRewardScreen obj, AbstractCard card) {
        if (obj.rItem.type == HyperfloorPatch.HYPERFLOOR) {

          MapRoomNode currNode = AbstractDungeon.currMapNode;

          // Setup new node
          MapRoomNode node = new MapRoomNode(currNode.x, currNode.y);
          for (Iterator<MapRoomNode> parents = currNode.getParents().iterator(); parents.hasNext();) {
            node.addParent(parents.next());
          }
          for (Iterator<MapEdge> edges = currNode.getEdges().iterator(); edges.hasNext();) {
            node.addEdge(edges.next());
          }
          switch (card.cardID) {
            case "ScheduleMod:HyperfloorMonster":
              node.room = (AbstractRoom) new MonsterRoomElite();
              break;
            case "ScheduleMod:HyperfloorEvent":
              node.room = (AbstractRoom) new EventRoom();
              break;
            case "ScheduleMod:HyperfloorShop":
              node.room = (AbstractRoom) new ShopRoom();
              break;
            default:
              break;
          }
          AbstractDungeon.nextRoom = node;
          return SpireReturn.Return();
        } else
          return SpireReturn.Continue();
      }
    }

    @SpirePatch(clz = ProceedButton.class, method = SpirePatch.CLASS)
    public static class ProceedButtonToHyperfloorField {
      public static SpireField<Boolean> toHyperfloor = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = ProceedButton.class, method = "update")
    public static class HyperfloorProceedButtonPatch {

      private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
          Matcher finalMatcher = new Matcher.MethodCallMatcher(DungeonMapScreen.class, "open");
          return LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
      }

      @SpireInsertPatch(locator = Locator.class)
      public static SpireReturn<Void> Insert(ProceedButton obj) {
        if (ProceedButtonToHyperfloorField.toHyperfloor.get(obj)) {
          AbstractDungeon.nextRoomTransitionStart();
          ProceedButtonToHyperfloorField.toHyperfloor.set(obj, false);
          return SpireReturn.Return();
        } else {
          return SpireReturn.Continue();
        }
      }
    }

    @SpirePatch(clz = ProceedButton.class, method = "setLabel", paramtypez = {String.class})
    public static class HyperfloorProceedButtonLabelPatch {
      
      @SpirePrefixPatch
      public static void Prefix(ProceedButton obj, @ByRef String[] newLabel) {
        if (ProceedButtonToHyperfloorField.toHyperfloor.get(obj) && newLabel[0].equals(ProceedButton.TEXT[0])) {
          newLabel[0] = hyperfloorProceedLabel;
        }
        logger.info("PROCEED BUTTON LABEL: " + newLabel[0]);
      }
    }
  }
}