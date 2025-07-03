package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;

@Mixin(FleeEntityGoal.class)
public interface ClassToFleeFromAccessor {
    @Accessor("classToFleeFrom")
    public Class<LivingEntity> getClassToFleeFrom();
}
