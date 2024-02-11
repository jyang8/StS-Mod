package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import schedulemod.BasicMod;
import schedulemod.powers.SatietyPower;

import static schedulemod.BasicMod.makeID;

public class ExquisitelyStuffedAction extends AbstractGameAction {
    private DamageInfo info;
    private float startingDuration;

    public ExquisitelyStuffedAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.SLASH_HEAVY;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = startingDuration;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int count = AbstractDungeon.player.getPower(SatietyPower.POWER_ID).amount;
            BasicMod.logger.info("EXQUISITE: " + count);
            for (int i = 0; i < count; i++) {
                addToTop(new DamageAction(this.target, this.info, AttackEffect.SLASH_HEAVY));
            }
            tickDuration();
            this.isDone = true;
        }
    }
}
