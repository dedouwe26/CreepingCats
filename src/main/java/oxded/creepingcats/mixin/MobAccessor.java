package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;

@Mixin(FleeEntityGoal.class)
public interface MobAccessor {
    @Accessor("mob")
    public PathAwareEntity getMob();
}
