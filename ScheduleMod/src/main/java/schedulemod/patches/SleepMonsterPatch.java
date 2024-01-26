package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class SleepMonsterPatch {
    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class GetNextAction {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.monsters.AbstractMonster")
                            && m.getMethodName().equals("takeTurn")) {
                        m.replace("if (!m.hasPower(schedulemod.powers.SleepPower.POWER_ID)) {" +
                                "$_ = $proceed($$);" +
                                "}");
                    }
                }
            };
        }
    }
}
