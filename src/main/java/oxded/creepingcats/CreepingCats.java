package oxded.creepingcats;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.mixin.CreeperAccessor;
import oxded.creepingcats.mixin.GoalSelectorAccessor;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreepingCats implements ModInitializer {
	public static final String MOD_ID = "creeping-cats";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean check(CreeperEntity creeper) {
        Set<PrioritizedGoal> goals = ((GoalSelectorAccessor)creeper).getGoalSelector().getGoals();
        for (PrioritizedGoal pGoal : goals) {
            Goal iterGoal = pGoal.getGoal();
            if (iterGoal instanceof FleeEntityGoal) {
                try {
                    FleeEntityGoal<?> fleeGoal = (FleeEntityGoal<?>)iterGoal;
                    if (fleeGoal.canStart()) {
                        fleeGoal.start();
                        return true;
                    }
                } catch (Exception e) {
                    CreepingCats.LOGGER.error("Exception: "+e.toString());
                }
            } 
        }
		return false;
	}

	@Override
	public void onInitialize() {
	}
}