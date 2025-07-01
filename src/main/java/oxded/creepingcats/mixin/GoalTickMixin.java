package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.CreepingCats;

@Mixin(CreeperIgniteGoal.class)
public class GoalTickMixin {
    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        CreeperIgniteGoal thisGoal = (CreeperIgniteGoal)(Object)this;
        CreeperEntity creeper = ((CreeperAccessor)thisGoal).getCreeper();
        if (CreepingCats.check(creeper)) {
            ci.cancel();
            return;
        }
    }
}
