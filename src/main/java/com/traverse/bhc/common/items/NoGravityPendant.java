package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NoGravityPendant extends Item {

    public NoGravityPendant(Properties properties) {
        super(properties);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;

        // Check if the player is wearing elytra and pressing the sneak key (Shift)
        if (CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.FLIGHT_STOPPER.get(), player).isPresent() && isSneaking(player)) {
            // Stop elytra flight
            stopElytraFlight(player);
        }
    }

    private static boolean isSneaking(Player player) {
        return player.isShiftKeyDown();
    }

    private static void stopElytraFlight(Player player) {
        // Stop the player's fall
        player.fallDistance = 0.0F;

        // Play a sound (you can customize the sound)

        // Spawn particles (you can customize the particles)
        for (int i = 0; i < 4; i++) {
            Vec3 motion = player.getDeltaMovement();
            double offsetX = player.getRandom().nextFloat() * 0.5 - 0.25;
            double offsetY = player.getRandom().nextFloat() * 0.5 - 0.25;
            double offsetZ = player.getRandom().nextFloat() * 0.5 - 0.25;
            player.setDeltaMovement(0, 0, 0);
            player.level.addParticle(ParticleTypes.HAPPY_VILLAGER,
                    player.getX() + offsetX, player.getY() + player.getEyeHeight(), player.getZ() + offsetZ,
                    0, 0, 0);
        }
    }
}
