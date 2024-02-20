package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.StrengthPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import basemod.abstracts.CustomMonster;

public class BossBen extends CustomMonster {

    public static final String ID = makeID("TheBen");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private boolean firstTurn = true;

    private enum Form {
        VIEGO, LILLIA, BRAND, NEEKO
    }

    private static final byte BLADE_OF_THE_RUINED_KING = 2;

    private static final byte SPECTRAL_MAW = 3;

    private static final byte HARROWED_PATH = 4;

    private Form currentForm = Form.VIEGO;

    private int bladeOfTheRuinedKingDamage;
    private int spectralMawDamage;

    public BossBen() {
        super(NAME, ID, 223, -10.0F, -30.0F, 476.0F, 410.0F, null, -50.0F, 30.0F);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(233);
        } else {
            setHp(223);
        }
        loadAnimation("images/monsters/theForest/timeEater/skeleton.atlas",
                "images/monsters/theForest/timeEater/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        this.type = AbstractMonster.EnemyType.BOSS;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.bladeOfTheRuinedKingDamage = 15;
            this.spectralMawDamage = 27;
        } else {
            this.bladeOfTheRuinedKingDamage = 12;
            this.spectralMawDamage = 22;
        }
        this.damage.add(
                new DamageInfo(this, this.bladeOfTheRuinedKingDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.spectralMawDamage, DamageInfo.DamageType.NORMAL));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        // UnlockTracker.markBossAsSeen(ID);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                this, this,
                new ViegoPower(this)));
    }

    @Override
    protected void getMove(int num) {
        switch (currentForm) {
            case VIEGO:
                if (num < 40) {
                    if (!lastMove(BLADE_OF_THE_RUINED_KING)) {
                        this.setMove(BLADE_OF_THE_RUINED_KING, Intent.ATTACK);
                        return;
                    }
                } else if (num < 75) {
                    if (!lastMove(SPECTRAL_MAW)) {
                        this.setMove(SPECTRAL_MAW, Intent.ATTACK_DEBUFF);
                        return;
                    }
                } else {
                    if (!lastMove(HARROWED_PATH)) {
                        this.setMove(HARROWED_PATH, Intent.DEFEND_BUFF);
                        return;
                    }
                }
                break;
            default:
                return;
        }
        getMove(AbstractDungeon.aiRng.random(100));
    }

    @Override
    public void takeTurn() {
        int i;
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(
                    new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }
        switch (this.nextMove) {
            case BLADE_OF_THE_RUINED_KING:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                for (i = 0; i < 3; i++) {
                    AbstractDungeon.actionManager
                            .addToBottom(
                                    (AbstractGameAction) new VFXAction((AbstractCreature) this,
                                            (AbstractGameEffect) new ShockWaveEffect(this.hb.cX, this.hb.cY,
                                                    Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC),
                                            0.75F));
                    AbstractDungeon.actionManager.addToBottom(
                            (AbstractGameAction) new DamageAction((AbstractCreature) AbstractDungeon.player, this.damage
                                    .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
                break;
            case SPECTRAL_MAW:
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(AbstractDungeon.player, this.damage
                                .get(1), AbstractGameAction.AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, this,
                        new DrawReductionPower(AbstractDungeon.player, 1)));
                if (AbstractDungeon.ascensionLevel >= 19)
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                            AbstractDungeon.player, this,
                            new FrailPower(AbstractDungeon.player, 1, true), 1));
                break;
            case HARROWED_PATH:
                AbstractDungeon.actionManager.addToBottom(
                        new GainBlockAction(this, this, 20));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        this, this,
                        new StrengthPower(this, 2)));
                if (AbstractDungeon.ascensionLevel >= 19)
                    AbstractDungeon.actionManager.addToBottom(
                            new MakeTempCardInDiscardAction(new Slimed(), 2));
                break;
            case 5:
                // Storage
                AbstractDungeon.actionManager.addToBottom(
                        new ShoutAction(this, DIALOG[1], 0.5F, 2.0F));
                AbstractDungeon.actionManager
                        .addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager
                        .addToBottom(new RemoveSpecificPowerAction(this,
                                this, "Shackled"));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this,
                        this, this.maxHealth / 2 - this.currentHealth));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, this,
                        new WeakPower(AbstractDungeon.player, 1, true), 1));
                if (AbstractDungeon.ascensionLevel >= 19)
                    AbstractDungeon.actionManager
                            .addToBottom(new GainBlockAction(this,
                                    this, 0));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

}
