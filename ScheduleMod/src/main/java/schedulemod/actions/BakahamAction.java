package schedulemod.actions;

import java.lang.reflect.Field;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

public class BakahamAction extends AbstractGameAction {
    private AbstractMonster m;
    private DamageInfo info;
    private int amount;

    public BakahamAction(AbstractMonster m, DamageInfo info, int amount) {
        this.m = m;
        this.info = info;
        this.amount = amount;
    }

    @Override
    public void update() {

        if (m == null) {
            this.isDone = true;
            return;
        }
        try {
            Field f = AbstractMonster.class.getDeclaredField("move");
            f.setAccessible(true);
            if (((EnemyMoveInfo) f.get(m)).baseDamage == -1) {
                addToTop(new DrawCardAction(this.amount));
                this.isDone = true;
                return;
            }
        } catch (NoSuchFieldException | IllegalAccessException var3) {
            var3.printStackTrace();
        }
        addToTop(new DamageAction(this.m, this.info, AttackEffect.SLASH_VERTICAL));
        this.isDone = true;
    }
}
