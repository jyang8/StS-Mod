package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import schedulemod.character.Entropy;
import schedulemod.powers.MaxSatietyPower;

public class GluttonyAction extends AbstractGameAction {
    private int increaseSatietyAmount;

    private Entropy source;
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public GluttonyAction(Entropy source, AbstractCreature target, DamageInfo info, int maxSatietyAmount) {
        this.source = source;
        this.info = info;
        setValues(target, info);
        this.increaseSatietyAmount = maxSatietyAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) &&
                    !this.target.halfDead && !this.target.hasPower("Minion")) {
                addToBot(new ApplyPowerAction(target, source, new MaxSatietyPower(source, increaseSatietyAmount), increaseSatietyAmount));
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
