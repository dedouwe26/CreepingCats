package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.pathing.Path;

@Mixin(FleeEntityGoal.class)
public interface FleePathAccessor {
    @Accessor("fleePath")
    public void setFleePath(Path fleePath);
}
