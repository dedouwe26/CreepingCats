package oxded.creepingcats.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.CreepingCats;

@Mixin(CreeperEntity.class)
public class CreeperExplodeMixin {
    @Inject(method = "explode()V", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo info) {
        CreeperEntity creeper = (CreeperEntity)(Object)this;
        Set<PrioritizedGoal> goals = ((GoalSelectorAccessor)creeper).getGoalSelector().getGoals();
        for (PrioritizedGoal prioritizedGoal : goals) {
            Goal goal = prioritizedGoal.getGoal();
            if (goal instanceof FleeEntityGoal) {
                try {
                    FleeEntityGoal<?> g = (FleeEntityGoal<?>)goal;
                    creeper.getNavigation().stop();
                    if (g.canStart()) {
                        g.start();
                        info.cancel();
                        return;
                    }
                } catch (Exception e) {
                    CreepingCats.LOGGER.error("Exception: "+e.toString());
                }
            } 
        }
    }
}
