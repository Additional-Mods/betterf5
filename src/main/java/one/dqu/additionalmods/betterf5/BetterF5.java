package one.dqu.additionalmods.betterf5;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class BetterF5 implements ModInitializer {
    public static float THIRD_PERSON_DISTANCE = 0.0f;

    @Override
    public void onInitialize() {}

    public static float getMaxDistance() {
        MinecraftClient client = MinecraftClient.getInstance();
        float distance = 0.5f;

        if (client.player != null && client.player.hasVehicle() && client.player.getRootVehicle() != null) {
            distance += client.player.getRootVehicle().getBoundingBox().getAverageSideLength() * 1.5;
        }

        return distance;
    }

    public static void clipDistance() {
        if (BetterF5.THIRD_PERSON_DISTANCE > getMaxDistance()) {
            BetterF5.THIRD_PERSON_DISTANCE = getMaxDistance();
        } else if (BetterF5.THIRD_PERSON_DISTANCE < -2.0f) {
            BetterF5.THIRD_PERSON_DISTANCE = -2.0f;
        }
    }
}
