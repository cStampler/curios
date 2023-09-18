package com.traverse.bhc.client;

import ca.weblite.objc.Client;
import com.traverse.bhc.common.BaubleyHeartCanisters;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(modid = BaubleyHeartCanisters.MODID, value = Dist.CLIENT, bus = MOD)
public class ClientBaubleyHeartCanisters {


    public static final ResourceLocation SLOT2_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID,"slots/empty_gravitypendant");
    public static final ResourceLocation SLOT1_TEXTURE = new ResourceLocation(BaubleyHeartCanisters.MODID,"slots/empty_flightstopper");

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void textureStitch(final TextureStitchEvent.Pre evt) {
        evt.addSprite(ClientBaubleyHeartCanisters.SLOT2_TEXTURE);
        evt.addSprite(ClientBaubleyHeartCanisters.SLOT1_TEXTURE);
    }
}
