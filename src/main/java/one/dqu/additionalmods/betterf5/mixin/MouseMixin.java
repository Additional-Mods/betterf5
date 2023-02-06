package one.dqu.additionalmods.betterf5.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.Perspective;
import net.minecraft.text.Text;
import one.dqu.additionalmods.betterf5.BetterF5;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSpectator()Z"), cancellable = true)
    private void scrollChangeDistance(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (this.client.options.getPerspective() != Perspective.FIRST_PERSON && client.player != null && client.currentScreen == null) {
            BetterF5.THIRD_PERSON_DISTANCE += vertical * 0.1;
            BetterF5.clipDistance();

            client.player.sendMessage(Text.of(String.format("%.2f", BetterF5.THIRD_PERSON_DISTANCE)), true);

            ci.cancel();
        }
    }
}
