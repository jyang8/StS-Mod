package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
        if (this.m != null && !(this.m.intent == AbstractMonster.Intent.ATTACK || this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEFEND))
            addToTop(new DrawCardAction(this.amount));
        addToTop(new DamageAction(this.m, this.info, AttackEffect.SLASH_VERTICAL));
        this.isDone = true;
    }
}
