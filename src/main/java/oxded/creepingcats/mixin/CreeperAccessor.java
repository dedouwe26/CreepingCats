package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;

@Mixin(CreeperIgniteGoal.class)
public interface CreeperAccessor {
    @Accessor("creeper")
    CreeperEntity getCreeper();
}
