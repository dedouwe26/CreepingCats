package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.CreepingCats;

@Mixin(CreeperIgniteGoal.class)
public class GoalCanStartMixin {
    @Inject(method = "canStart()Z", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfoReturnable<Boolean> cir) {
        CreeperIgniteGoal thisGoal = (CreeperIgniteGoal)(Object)this;
        CreeperEntity creeper = ((CreeperAccessor)thisGoal).getCreeper();
        if (CreepingCats.check(creeper)) {
            cir.setReturnValue(false);
        }
    }
}
