package com.traverse.bhc.common.items;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.init.RegistryHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlightStopperPendant extends Item {

    public FlightStopperPendant(Properties properties) {
        super(properties);
    }

    @SubscribeEvent
    public static void onDamage(LivingHurtEvent event) {
        if (event.getSource() == DamageSource.FLY_INTO_WALL) {
            if (event.getEntity() instanceof Player) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.GRAVITY_PENDANT.get(), event.getEntityLiving()).isPresent()) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
