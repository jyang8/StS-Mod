package schedulemod.commands;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.FadeWipeParticle;

import basemod.devcommands.ConsoleCommand;

public class BossPortalCommand extends ConsoleCommand {

    public BossPortalCommand() {
        maxExtraTokens = 0;
        minExtraTokens = 0;
        requiresPlayer = true;
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        MapRoomNode node = new MapRoomNode(-1, 15);
        node.room = (AbstractRoom) new MonsterRoomBoss();
        AbstractDungeon.nextRoom = node;
        CardCrawlGame.music.fadeOutTempBGM();
        AbstractDungeon.pathX.add(Integer.valueOf(1));
        AbstractDungeon.pathY.add(Integer.valueOf(15));
        AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
        AbstractDungeon.nextRoomTransitionStart();
    }

}
