package oxded.creepingcats;

import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.mixin.GoalSelectorAccessor;

public class CreepingCats implements ModInitializer {
	public static final String MOD_ID = "creeping-cats";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static int maxFleeDistance = 30;
    public static TargetPredicate targetPredicate = TargetPredicate.createAttackable().ignoreVisibility().setBaseMaxDistance(30);

    private static @Nullable CreeperIgniteGoal getGoal(Set<PrioritizedGoal> goals) {
        for (PrioritizedGoal pGoal : goals) {
            Goal iterGoal = pGoal.getGoal();
            if (iterGoal instanceof CreeperIgniteGoal) {
                return (CreeperIgniteGoal)iterGoal;
            }
        }
        return null;
    }
	public static boolean check(CreeperEntity creeper) {
        Set<PrioritizedGoal> goals = ((GoalSelectorAccessor)creeper).getGoalSelector().getGoals();
        CreeperIgniteGoal igniteGoal = null;
        for (PrioritizedGoal pGoal : goals) {
            Goal iterGoal = pGoal.getGoal();
            if (iterGoal instanceof CreeperIgniteGoal) {
                igniteGoal = (CreeperIgniteGoal)iterGoal;
            }
            if (iterGoal instanceof FleeEntityGoal) {
                FleeEntityGoal<?> fleeGoal = (FleeEntityGoal<?>)iterGoal;
                if (fleeGoal.canStart()) {
                    if (igniteGoal == null) { igniteGoal = getGoal(goals); }
                    if (igniteGoal != null) {
                        igniteGoal.stop();
                    }
                    fleeGoal.start();
                    return true;
                }
            } 
        }
		return false;
	}

	@Override
	public void onInitialize() {
	}
}