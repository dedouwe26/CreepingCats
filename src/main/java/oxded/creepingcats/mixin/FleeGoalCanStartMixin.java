package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import oxded.creepingcats.CreepingCats;

@Mixin(FleeEntityGoal.class)
public class FleeGoalCanStartMixin {
    @Inject(method = "canStart()Z", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfoReturnable<Boolean> cir) {
        FleeEntityGoal<?> fleeGoal = (FleeEntityGoal<?>)(Object)this;
        PathAwareEntity mob = ((MobAccessor)fleeGoal).getMob();
        if (mob instanceof CreeperEntity) {
            LivingEntity targetEntity = ((ServerWorld)mob.getWorld()).getClosestEntity(mob.getWorld().getEntitiesByClass(((ClassToFleeFromAccessor)fleeGoal).getClassToFleeFrom(), mob.getBoundingBox().expand(CreepingCats.maxFleeDistance, CreepingCats.maxFleeDistance, CreepingCats.maxFleeDistance), (livingEntity) -> {
                return true;
            }), CreepingCats.targetPredicate, mob, mob.getX(), mob.getY(), mob.getZ());
            if (targetEntity == null) {
                return;
            }
            ((TargetEntityAccessor)fleeGoal).setTargetEntity(targetEntity);
            Vec3d vec3d = NoPenaltyTargeting.findFrom(mob, 16, 7, targetEntity.getPos());
            Path fleePath;
            if (vec3d != null) {
                fleePath = mob.getNavigation().findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
            } else {
                fleePath = mob.getNavigation().findPathTo(mob.getX(), mob.getY(), mob.getZ(), 0);
            }
            ((FleePathAccessor)fleeGoal).setFleePath(fleePath);
            cir.setReturnValue(true);
            return;
        }
    }

}
