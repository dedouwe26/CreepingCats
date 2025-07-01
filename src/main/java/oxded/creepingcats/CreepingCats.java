package oxded.creepingcats;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.predicate.entity.EntityPredicates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreepingCats implements ModInitializer {
	public static final String MOD_ID = "creeping-cats";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final int maxDistance = 30;

	public static final TargetPredicate p = TargetPredicate.createAttackable().setBaseMaxDistance((double)maxDistance).setPredicate(EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);

	@Override
	public void onInitialize() {
	}
}