package one.dqu.additionalmods.betterf5.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import one.dqu.additionalmods.betterf5.BetterF5;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    private static boolean wasRiding = false;

    @Shadow
    protected abstract void moveBy(double x, double y, double z);

    @Shadow
    protected abstract double clipToSpace(double desiredCameraDistance);

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;moveBy(DDD)V", ordinal = 0))
    private void thirdPersonDistance(Camera camera, double x, double y, double z) {
        moveBy(-clipToSpace(4 + BetterF5.THIRD_PERSON_DISTANCE), 0.0, 0.0);
    }

    @Inject(method = "update", at = @At(value = "HEAD"))
    private void changeF5state(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        boolean isRiding = client.player.hasVehicle();

        if (isRiding != wasRiding) {
            if (isRiding) {
                client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            } else {
                client.options.setPerspective(Perspective.FIRST_PERSON);
            }

            BetterF5.clipDistance();

            wasRiding = isRiding;
        }
    }
}
