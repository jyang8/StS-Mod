package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.interfaces.AfterMonsterCalculateDamagePower;

public class AfterMonsterCalculateDamagePatch {

    @SpirePatch(clz = AbstractMonster.class, method = "calculateDamage")
    public static class OnRefreshHandCardGroupPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster __instance) {
          for (AbstractPower pow : __instance.powers) {
            if (pow instanceof AfterMonsterCalculateDamagePower) {
              ((AfterMonsterCalculateDamagePower)pow).afterMonsterCalculateDamage();
            }
          }
        }
    }
}
