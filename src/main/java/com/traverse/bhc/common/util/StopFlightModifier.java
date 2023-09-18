package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID)
public class StopFlightModifier {


    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() != RegistryHandler.FLIGHT_STOPPER.get())
            return;

        ICurio curio = new ICurio() {

            @Override
            public ItemStack getStack() {
                return new ItemStack(RegistryHandler.FLIGHT_STOPPER.get());
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                LivingEntity livingEntity = slotContext.getWearer();
                Optional<ImmutableTriple<String, Integer, ItemStack>> stackOptional = CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.FLIGHT_STOPPER.get(), livingEntity);

                stackOptional.ifPresent(triple -> {
                    if(livingEntity instanceof Player) {
                        ItemStack stack = triple.getRight();
                        updatePlayerHealth((Player) livingEntity, stack, true);
                    }
                });
            }

            @Override
            public boolean canRightClickEquip() {
               return false;
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                if(slotContext.getWearer() instanceof Player)
                    updatePlayerHealth((Player) slotContext.getWearer(), ItemStack.EMPTY, false);
            }

        };

        ICapabilityProvider provider = new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };

        event.addCapability(CuriosCapability.ID_ITEM, provider);
    }

    public static void updatePlayerHealth(Player player, ItemStack stack, boolean NoGravity) {


        if(NoGravity && !stack.isEmpty()) {

        }
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

