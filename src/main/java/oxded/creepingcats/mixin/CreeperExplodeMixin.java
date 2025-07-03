package oxded.creepingcats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.mob.CreeperEntity;
import oxded.creepingcats.CreepingCats;

@Mixin(CreeperEntity.class)
public class CreeperExplodeMixin {
    @Inject(method = "explode()V", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        CreeperEntity creeper = (CreeperEntity)(Object)this;
        if (CreepingCats.check(creeper)) {
            ci.cancel();
            return;
        }
    }

}
