package schedulemod.character;

import basemod.BaseMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomSavable;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import schedulemod.BasicMod;
import schedulemod.cards.navy.Bakaham;
import schedulemod.cards.navy.Defend_Navy;
import schedulemod.cards.navy.PowerNap;
import schedulemod.cards.navy.Strike_Navy;
import schedulemod.orbs.ScheduleOrb;
import schedulemod.powers.CoffeePower;
import schedulemod.relics.SoothrRes;

import java.util.ArrayList;
import java.util.Collections;

import static schedulemod.BasicMod.characterPath;
import static schedulemod.BasicMod.logger;

public class Entropy extends CustomPlayer implements CustomSavable<Integer> {
    // Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 7;
    public static final int BASE_MAX_SATIETY = 3;
    private int satietyGainedThisCombat = 0;

    public int maxSatiety = BASE_MAX_SATIETY;

    // Strings
    private static final String ID = BasicMod.makeID("Entropy"); // This should match whatever you have in the
                                                                 // CharacterStrings.json file
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // Image file paths
    private static final String SHOULDER_1 = characterPath("shoulder.png"); // Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); // Corpse is when you die.
    private static final String ENERGY_ORB = characterPath("cardback/navy_energy_orb_p.png");

    private static final String SATIETY_SAVABLE = "ScheduleMod:MaxSatiety";

    public static class Enums {
        // These are used to identify your character, as well as your character's card
        // color.
        // Library color is basically the same as card color, but you need both because
        // that's how the game was made.
        @SpireEnum
        public static AbstractPlayer.PlayerClass ENTROPY;
        @SpireEnum(name = "CHARACTER_NAVY_COLOR") // These two MUST match. Change it to something unique for your
                                                  // character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "CHARACTER_NAVY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
        @SpireEnum(name = "FOOD")
        public static AbstractCard.CardTags FOOD;
        @SpireEnum(name = "BREAD")
        public static AbstractCard.CardTags BREAD;
        @SpireEnum(name = "EVENT")
        public static AbstractCard.CardTags EVENT;
        @SpireEnum(name = "FATIGUE_EVENT")
        public static AbstractCard.CardTags FATIGUE_EVENT;
        @SpireEnum(name = "AMP_EVENT")
        public static AbstractCard.CardTags AMP_EVENT;
        @SpireEnum(name = "SCHEDULE_GLOW")
        public static AbstractCard.CardTags SCHEDULE_GLOW;
    }

    private AbstractCard currentlyEvokingCard;
    private UseCardAction currentlyEvokingAction;
    private AbstractCreature currentlyEvokingMonster;

    public Entropy() {
        super(NAMES[0], Enums.ENTROPY,
                new CustomEnergyOrb(null, null, null), // Energy Orb
                new SpriterAnimation(characterPath("animation/default.scml"))); // Animation

        initializeClass(characterPath("Entropy_smol.png"),
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F, // Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        // Location for text bubbles. You can adjust it as necessary later. For most
        // characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);

        BaseMod.addSaveField(SATIETY_SAVABLE, this);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        // List of IDs of cards for your starting deck.
        // If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike_Navy.ID);
        retVal.add(Strike_Navy.ID);
        retVal.add(Strike_Navy.ID);
        retVal.add(Strike_Navy.ID);
        retVal.add(Defend_Navy.ID);
        retVal.add(Defend_Navy.ID);
        retVal.add(Defend_Navy.ID);
        retVal.add(Defend_Navy.ID);
        retVal.add(Bakaham.ID);
        retVal.add(PowerNap.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        // IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(SoothrRes.ID);

        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        // This card is used for the Gremlin card matching game.
        // It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Strike_Red();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; // Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        // These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    public int getSatietyGainedThisCombat() {
        return this.satietyGainedThisCombat;
    }

    public void gainSatiety(int amount) {
        if (amount >= 0) {
            this.satietyGainedThisCombat += amount;
        }
    }

    public void increaseMaxSatiety(int amount, boolean showEffect) {
        maxSatiety = maxSatiety + amount;
        AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(
                this.hb.cX - this.animX, this.hb.cY,
                TEXT[3] + ("+") + Integer.toString(amount), Settings.GREEN_TEXT_COLOR));

    }

    @Override
    public void channelOrb(AbstractOrb orbToSet) {
        if (!(orbToSet instanceof ScheduleOrb)) {
            return;
        }
        ScheduleOrb schedule = (ScheduleOrb) orbToSet;
        int index = schedule.slot;
        if (index < this.maxOrbs) {
            schedule.cX = (this.orbs.get(index)).cX;
            schedule.cY = (this.orbs.get(index)).cY;
            this.orbs.set(index, schedule);
            (this.orbs.get(index)).setSlot(index, this.maxOrbs);
            schedule.playChannelSFX();
            for (AbstractPower p : this.powers)
                p.onChannel(schedule);
            schedule.eventCard.onSchedule();
            AbstractDungeon.actionManager.orbsChanneledThisCombat.add(schedule);
            AbstractDungeon.actionManager.orbsChanneledThisTurn.add(schedule);
        } else {
            logger.info("Tried scheduling at slot greater than max:" + schedule.slot + "(max " + this.maxOrbs + ")");
        }
    }

    public void channelOrbs() {
        AbstractOrb schedule = new EmptyOrbSlot();
        int index = this.maxOrbs;
        schedule.cX = (this.orbs.get(index)).cX;
        schedule.cY = (this.orbs.get(index)).cY;
        this.orbs.set(index, schedule);
        (this.orbs.get(index)).setSlot(index, this.maxOrbs);
        schedule.playChannelSFX();
        for (AbstractPower p : this.powers)
            p.onChannel(schedule);
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); // Used for some vfx on moving cards (sometimes)
                                                                  // (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); // Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); // Used for a screen tint effect when you attack the
                                                                   // heart.

    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        // Font used to display your current energy.
        // energyNumFontRed, Blue, Green, and Purple are used by the basegame
        // characters.
        // It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        // This occurs when you click the character's button in the character select
        // screen.
        // See SoundMaster for a full list of existing sound effects, or look at
        // BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        // Similar to doCharSelectScreenSelectEffect, but used for the Custom mode
        // screen. No shaking.
        return "ATTACK_DAGGER_2";
    }

    // Don't adjust these four directly, adjust the contents of the
    // CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2]; // Generally, the only difference in this text is how the vampires refer to the
                        // player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    // This is used to display the character's information on the character
    // selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        // Makes a new instance of your character class.
        return new Entropy();
    }

    @Override
    public void evokeOrb() {
        if (!this.orbs.isEmpty()) {
            ((AbstractOrb) this.orbs.get(0)).onEvoke();
            AbstractOrb orbSlot = new EmptyOrbSlot();

            int i;
            for (i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }

            this.orbs.set(this.orbs.size() - 1, orbSlot);

            for (i = 0; i < this.orbs.size(); ++i) {
                ((AbstractOrb) this.orbs.get(i)).setSlot(i, this.maxOrbs);
            }
        }

    }

    public void onPlayCard(AbstractCard card, AbstractMonster monster) {
        if (!card.purgeOnUse) {
            this.currentlyEvokingCard = card;
            this.currentlyEvokingMonster = monster;
            if (this.hasPower(CoffeePower.POWER_ID)) {
                for (int i = 0; i < this.getPower(CoffeePower.POWER_ID).amount; i++) {
                    this.evokeOrb();
                }
            }
            this.evokeOrb();
        }
    }

    public AbstractCard getCurrentlyEvokingCard() {
        return currentlyEvokingCard;
    }

    public UseCardAction getCurrentlyEvokingAction() {
        return currentlyEvokingAction;
    }

    public AbstractCreature getCurrentlyEvokingMonster() {
        return currentlyEvokingMonster;
    }

    @Override
    public Integer onSave() {
        return maxSatiety;
    }

    @Override
    public void onLoad(Integer maxSatiety) {
        if (maxSatiety != null) {
            this.maxSatiety = maxSatiety;
        }
    }
}
