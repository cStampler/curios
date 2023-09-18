package com.traverse.bhc.common.util;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
public class NoGravityModifier {


    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() != RegistryHandler.GRAVITY_PENDANT.get())
            return;

        ICurio curio = new ICurio() {

            @Override
            public ItemStack getStack() {
                return new ItemStack(RegistryHandler.GRAVITY_PENDANT.get());
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                LivingEntity livingEntity = slotContext.getWearer();
                Optional<ImmutableTriple<String, Integer, ItemStack>> stackOptional = CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.GRAVITY_PENDANT.get(), livingEntity);

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
    public static void onDamage(LivingHurtEvent event) {
        if (event.getSource() == DamageSource.FLY_INTO_WALL) {
            if (CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.GRAVITY_PENDANT.get(), event.getEntityLiving()).isPresent()) {

                event.setCanceled(true);
            }
            }
        }
    }

