package com.traverse.bhc.common.init;

import com.traverse.bhc.common.BaubleyHeartCanisters;
import com.traverse.bhc.common.items.*;
import com.traverse.bhc.common.util.HeartType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler {

    public static final DeferredRegister <Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BaubleyHeartCanisters.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BaubleyHeartCanisters.MODID);

    //Items
    public static final RegistryObject<Item> RED_HEART = ITEMS.register("red_heart", () -> new ItemHeart(HeartType.RED));
    public static final RegistryObject<Item> YELLOW_HEART = ITEMS.register("yellow_heart", () -> new ItemHeart(HeartType.YELLOW));
    public static final RegistryObject<Item> GREEN_HEART = ITEMS.register("green_heart", () -> new ItemHeart(HeartType.GREEN));
    public static final RegistryObject<Item> BLUE_HEART = ITEMS.register("blue_heart", () -> new ItemHeart(HeartType.BLUE));
    public static final RegistryObject<Item> CANISTER = ITEMS.register("canister", BaseItem::new);
    public static final RegistryObject<Item> RED_CANISTER = ITEMS.register("red_heart_canister", () -> new BaseHeartCanister(HeartType.RED));
    public static final RegistryObject<Item> YELLOW_CANISTER = ITEMS.register("yellow_heart_canister", () -> new BaseHeartCanister(HeartType.YELLOW));
    public static final RegistryObject<Item> GREEN_CANISTER = ITEMS.register("green_heart_canister", () -> new BaseHeartCanister(HeartType.GREEN));
    public static final RegistryObject<Item> BLUE_CANISTER = ITEMS.register("blue_heart_canister", () -> new BaseHeartCanister(HeartType.BLUE));
    public static final RegistryObject<Item> WITHER_BONE = ITEMS.register("wither_bone", BaseItem::new);
    public static final RegistryObject<Item> RELIC_APPLE = ITEMS.register("relic_apple", ItemRelicApple::new);

    public static final RegistryObject<Item> GRAVITY_PENDANT = ITEMS.register("gravity_pendant", () ->
            new NoGravityPendant(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
    );

    public static final RegistryObject<Item> FLIGHT_STOPPER = ITEMS.register("flight_stopper", () ->
            new FlightStopperPendant(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
    );

    //Container
}
