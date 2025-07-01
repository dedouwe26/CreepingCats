package oxded.creepingcats.mixin;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.CreepingCats;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CreeperEntity.class)
public class CreeperFuseSpeedMixin {
    @ModifyVariable(method = "setFuseSpeed(I)V", at = @At("HEAD"), ordinal = 0)
    private int init(int fuseSpeed) {
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
                        return -1;
                    }
                } catch (Exception e) {
                    CreepingCats.LOGGER.error("Exception: "+e.toString());
                }
            } 
        }
        return fuseSpeed;
    }
}