package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.interfaces.OnRefreshHandPower;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static schedulemod.BasicMod.makeID;

public class MetabolizePower extends BasePower implements CloneablePowerInterface, OnRefreshHandPower {
    public static final String POWER_ID = makeID("Metabolize");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MetabolizePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void onRefreshHand() {
        AbstractPlayer p = AbstractDungeon.player;
         if (AbstractDungeon.actionManager.actions.isEmpty() && p.hand.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded && 
      !p.hasPower(NoDrawPower.POWER_ID) && !AbstractDungeon.isScreenUp)
      if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT  && (
        p.discardPile.size() > 0 || p.drawPile.size() > 0)) {
        flash();
        addToBot((AbstractGameAction)new DrawCardAction((AbstractCreature)AbstractDungeon.player, this.amount));
        addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SatietyPower(p, amount), amount));
      }  
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + this.amount + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MetabolizePower(owner, source, amount);
    }
}
