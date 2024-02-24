package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
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
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
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
import com.megacrit.cardcrawl.potions.AbstractPotion;
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
import schedulemod.BasicMod;

/*
 * TODO
 * 
 * - More forms
 *   - Attacks
 *   - Power
 * - Form switching logic
 * - Art for boss
 * - Art for power icons
 * - Animations for attacks
 * - Playtest 
 *   - Balance
 *   - Bugs
 *   - Funness
 */
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

    private static final byte CHANGE_FORM = 99;

    private static final byte BLADE_OF_THE_RUINED_KING = 2;

    private static final byte SPECTRAL_MAW = 3;

    private static final byte HARROWED_PATH = 4;

    private static final byte BLOOMING_BLOWS = 12;
    private static final byte WATCH_OUT_EEP = 13;
    private static final byte SWIRLSEED = 14;

    private ArrayList<Form> forms = new ArrayList<>();
    private int currentFormIndex = -1;
    private AbstractPower currentFormPower = null;
    private AbstractPower formPhasePower = null;

    private int bladeOfTheRuinedKingDamage;
    private int spectralMawDamage;
    private int bloomingBlowsDamage;
    private int watchOutEepDamage;
    private int swirlseedDamage;

    public int formHealth() {
        if (AbstractDungeon.ascensionLevel >= 9) {
            return 100;
        } else {
            return 10;
        }
    }

    public BossBen() {
        super(NAME, ID, 223, -10.0F, -30.0F, 476.0F, 410.0F, null, -50.0F, 30.0F);
        // Shuffle forms
        forms.clear();
        forms.add(Form.LILLIA);
        forms.add(Form.NEEKO);
        forms.add(Form.BRAND);
        Collections.shuffle(forms, new Random(AbstractDungeon.aiRng.randomLong()));
        // Force Viego to be first form
        forms.add(0, Form.VIEGO);
        setHp(formHealth() * 4);
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
            this.bloomingBlowsDamage = 5;
            this.watchOutEepDamage = 22;
            this.swirlseedDamage = 19;
        } else {
            this.bladeOfTheRuinedKingDamage = 12;
            this.spectralMawDamage = 22;
            this.bloomingBlowsDamage = 4;
            this.watchOutEepDamage = 18;
            this.swirlseedDamage = 15;
        }
        this.damage.add(
                new DamageInfo(this, this.bladeOfTheRuinedKingDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.spectralMawDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.bloomingBlowsDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.watchOutEepDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.swirlseedDamage, DamageInfo.DamageType.NORMAL));
    }

    public Form getCurrentForm() {
        if (currentFormIndex < 0) {
            return forms.get(0);
        }
        return forms.get(currentFormIndex);
    }

    public void switchForm() {
        if (currentFormIndex == forms.size() - 1) {
            return;
        }
        BasicMod.logger.info("Switching form to " + (currentFormIndex + 1));
        if (currentFormIndex >= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
                    this, this, currentFormPower));
        } else {
            formPhasePower = new FormPhasePower(this, formHealth());
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    this, this,
                    formPhasePower));
        }
        currentFormIndex++;
        switch (getCurrentForm()) {
            case VIEGO:
                currentFormPower = new ViegoPower(this);
                break;
            case LILLIA:
                currentFormPower = new LilliaPower(this);
                break;
            case BRAND:
                // TODO
                break;
            case NEEKO:
                // TODO
                break;
            default:
                currentFormPower = new ViegoPower(this);
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                this, this,
                currentFormPower));
        formPhasePower.amount = formHealth();
        formPhasePower.updateDescription();
    }

    @Override
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        switchForm();
        // UnlockTracker.markBossAsSeen(ID);
        // AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
        // this, this,
        // new ViegoPower(this)));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (formPhasePower != null && formPhasePower.amount == 0) {
            setMove(CHANGE_FORM, Intent.UNKNOWN);
            createIntent();
            // TODO change text
            AbstractDungeon.actionManager
                    .addToBottom((AbstractGameAction) new TextAboveCreatureAction((AbstractCreature) this,
                            TextAboveCreatureAction.TextType.INTERRUPTED));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, "Change Form", CHANGE_FORM,
                    AbstractMonster.Intent.UNKNOWN));
        }
    }

    @Override
    protected void getMove(int num) {
        BasicMod.logger.info("Rolling move: " + num);
        if (formPhasePower != null && formPhasePower.amount == 0) {
            BasicMod.logger.info("Changing form: " + formPhasePower);
            this.setMove(CHANGE_FORM, Intent.UNKNOWN);
            return;
        }
        switch (getCurrentForm()) {
            case VIEGO:
                if (num < 40) {
                    if (!lastMove(BLADE_OF_THE_RUINED_KING)) {
                        this.setMove(MOVES[0], BLADE_OF_THE_RUINED_KING, Intent.ATTACK,
                                this.damage.get(0).base, 2, true);
                        return;
                    }
                } else if (num < 75) {
                    if (!lastMove(SPECTRAL_MAW)) {
                        this.setMove(MOVES[1], SPECTRAL_MAW, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                        return;
                    }
                } else {
                    if (!lastMove(HARROWED_PATH)) {
                        this.setMove(MOVES[2], HARROWED_PATH, Intent.DEFEND_BUFF);
                        return;
                    }
                }
                break;
            case LILLIA:
                if (num < 40) {
                    if (!lastMove(BLOOMING_BLOWS)) {
                        this.setMove(MOVES[3], BLOOMING_BLOWS, Intent.ATTACK_BUFF,
                                this.damage.get(2).base, 4, true);
                        return;
                    }
                } else if (num < 75) {
                    if (!lastMove(WATCH_OUT_EEP)) {
                        this.setMove(MOVES[4], WATCH_OUT_EEP, Intent.ATTACK_DEBUFF, this.damage.get(3).base);
                        return;
                    }
                } else {
                    if (!lastMove(SWIRLSEED)) {
                        this.setMove(MOVES[5], SWIRLSEED, Intent.ATTACK_DEBUFF, this.damage.get(4).base);
                        return;
                    }
                }
            default:
                return;
        }
        getMove(AbstractDungeon.aiRng.random(100));
    }

    @Override
    public void takeTurn() {
        BasicMod.logger.info("Taking turn: " + this.nextMove);
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(
                    new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }
        int stacks;
        switch (this.nextMove) {
            case CHANGE_FORM:
                switchForm();
                break;
            case BLADE_OF_THE_RUINED_KING:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(AbstractDungeon.player, this.damage
                                .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(AbstractDungeon.player, this.damage
                                .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case SPECTRAL_MAW:
                AbstractDungeon.actionManager.addToBottom(
                        new VFXAction(this,
                                new ShockWaveEffect(this.hb.cX, this.hb.cY,
                                        Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.NORMAL),
                                0.75F));
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
            case BLOOMING_BLOWS:
                for (int i = 0; i < 4; i++) {
                    AbstractDungeon.actionManager.addToBottom(
                            new DamageAction(AbstractDungeon.player, this.damage
                                    .get(2), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
                }
                stacks = 4;
                if (AbstractDungeon.ascensionLevel >= 19) {
                    stacks = 5;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        this, this,
                        new SpeedPower(this, this, stacks)));
                break;
            case WATCH_OUT_EEP:
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1F));
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(AbstractDungeon.player, this.damage
                                .get(3), AbstractGameAction.AttackEffect.SMASH));
                stacks = 2;
                if (AbstractDungeon.ascensionLevel >= 19) {
                    stacks = 3;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, stacks, true)));
                break;
            case SWIRLSEED:
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(AbstractDungeon.player, this.damage
                                .get(4), AbstractGameAction.AttackEffect.LIGHTNING));
                stacks = 4;
                if (AbstractDungeon.ascensionLevel >= 19) {
                    stacks = 5;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, this,
                        new SpeedPower(AbstractDungeon.player, this, -stacks)));
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
        BasicMod.logger.info("Add roll move action");
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

}
