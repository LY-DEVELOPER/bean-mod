package com.example.beanmod;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LootMods {
    public static final String MODID = "beanmod";

    public LootMods() {
        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister
            .create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, MODID);
    private static final RegistryObject<MobModifier.Serializer> COW = GLM.register("cow",
            MobModifier.Serializer::new);
    private static final RegistryObject<MobModifier.Serializer> CHICKEN = GLM.register("chicken",
            MobModifier.Serializer::new);

    private static class MobModifier extends LootModifier {
        private final int chance;
        private final Item itemReward;

        public MobModifier(LootItemCondition[] conditionsIn, int chanceNum, Item reward) {
            super(conditionsIn);
            chance = chanceNum;
            itemReward = reward;
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            Random rand = new Random();
            if (rand.nextInt(chance) == 0) {
                generatedLoot.add(new ItemStack(itemReward, 1));
            }
            return generatedLoot;
        }

        private static class Serializer extends GlobalLootModifierSerializer<MobModifier> {

            @Override
            public MobModifier read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
                int chance = GsonHelper.getAsInt(object, "chance");
                Item item = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
                return new MobModifier(conditionsIn, chance, item);
            }

            @Override
            public JsonObject write(MobModifier instance) {
                JsonObject json = makeConditions(instance.conditions);
                json.addProperty("chance", instance.chance);
                json.addProperty("item", ForgeRegistries.ITEMS.getKey(instance.itemReward).toString());
                return json;
            }
        }
    }
}