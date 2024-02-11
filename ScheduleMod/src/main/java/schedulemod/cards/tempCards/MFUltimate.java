package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import schedulemod.actions.RemoveFromScheduleAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.orbs.ScheduleOrb;
import schedulemod.util.CardStats;

public class MFUltimate extends EventCard implements OnPlayerDamagedSubscriber {
    public static final String ID = makeID(MFUltimate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            2);

    private static final int ATTACK_DAMAGE = 7;
    private static final int ATTACK_COUNT = 3;
    private static final int UPGRADE_ATTACK_COUNT = 1;

    public MFUltimate() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setDamage(ATTACK_DAMAGE);
        setMagic(ATTACK_COUNT, UPGRADE_ATTACK_COUNT);
        this.damageType = DamageType.THORNS;
        this.damageTypeForTurn = DamageType.THORNS;
        BaseMod.subscribe(this);
    }

    @Override
    public int receiveOnPlayerDamaged(int amount, DamageInfo info) {
        if (amount > AbstractDungeon.player.currentBlock) {
            for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
                AbstractOrb o = AbstractDungeon.player.orbs.get(i);
                if (o instanceof ScheduleOrb && ((ScheduleOrb) o).eventCard instanceof MFUltimate) {
                    addToBot(new RemoveFromScheduleAction(i));
                }
            }
        }
        return amount;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction) new VFXAction(
                    (AbstractGameEffect) new DaggerSprayEffect(dontTriggerOnUseCard)));
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                addToBot(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.NONE));
            }
            addToBot((AbstractGameAction) new WaitAction(0.1F));
        }
        BaseMod.unsubscribe(this);
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction) new VFXAction(
                    (AbstractGameEffect) new DaggerSprayEffect(dontTriggerOnUseCard)));
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                addToBot(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.NONE));
            }
            addToBot((AbstractGameAction) new WaitAction(0.1F));
        }
        BaseMod.unsubscribe(this);
    }

    @Override
    public AbstractCard makeCopy() {
        return new MFUltimate();
    }
}
