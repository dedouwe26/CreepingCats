package oxded.creepingcats.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.CreepingCats;

@Mixin(CreeperIgniteGoal.class)
public class CreeperIgniteGoalMixin {
    @Inject(method = "canStart()Z", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfoReturnable<Boolean> cir) {
        CreeperIgniteGoal thisGoal = (CreeperIgniteGoal)(Object)this;
        CreeperEntity creeper = ((CreeperAccessor)thisGoal).getCreeper();
        Set<PrioritizedGoal> goals = ((GoalSelectorAccessor)creeper).getGoalSelector().getGoals();
        for (PrioritizedGoal pGoal : goals) {
            Goal iterGoal = pGoal.getGoal();
            if (iterGoal instanceof FleeEntityGoal) {
                try {
                    FleeEntityGoal<?> fleeGoal = (FleeEntityGoal<?>)iterGoal;
                    creeper.getNavigation().stop();
                    if (fleeGoal.canStart()) {
                        fleeGoal.start();
                        cir.setReturnValue(false);
                        return;
                    }
                } catch (Exception e) {
                    CreepingCats.LOGGER.error("Exception: "+e.toString());
                }
            } 
        }
    }
}
