package com.example.beanmod.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.beanmod.BeanMod;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.ForgeRegistries;

public class ThrownMagicBean extends ThrowableItemProjectile {

    public ThrownMagicBean(EntityType<? extends ThrownMagicBean> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public ThrownMagicBean(Level p_37481_, LivingEntity p_37482_) {
        super(BeanMod.MAGICBEANENT.get(), p_37482_, p_37481_);
    }

    public ThrownMagicBean(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(BeanMod.MAGICBEANENT.get(), p_37477_, p_37478_, p_37479_, p_37476_);
    }

    public void handleEntityEvent(byte p_37484_) {
        for (int i = 0; i < 5; i++)
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), 10D, 10D, 10D);
    }

    protected void onHitEntity(EntityHitResult p_37486_) {
        super.onHitEntity(p_37486_);
        p_37486_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            dropLoot();
            this.discard();
        }
    }

    protected void dropLoot() {
        List<Map<String, String>> listMap = lootMap();
        boolean dropped = false;
        while (!dropped) {
            String item = "";
            int chance = 0;
            LogUtils.getLogger().error(listMap.toString());
            for (Map.Entry<String, String> entry : listMap.get(this.random.nextInt(listMap.size())).entrySet()) {
                if (entry.getKey().equals("item")) {
                    item = entry.getValue();
                }
                if (entry.getKey().equals("chance")) {
                    chance = Integer.parseInt(entry.getValue());
                }
                LogUtils.getLogger().error(entry.getKey() + entry.getValue());
                LogUtils.getLogger().error(chance + item);
            }

            if (this.random.nextInt(chance) == 0) {
                dropped = true;
                this.level
                        .addFreshEntity(
                                new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(),
                                        new ItemStack(ForgeRegistries.ITEMS
                                                .getValue(new ResourceLocation(item)))));
            }
        }
    }

    protected Item getDefaultItem() {
        return BeanMod.MAGICBEAN.get();
    }

    public List<Map<String, String>> lootMap() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
                .getResourceAsStream("data/beanmod/loot_tables/entities/magicbean.json")));
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> listMap = gson.fromJson(reader, listType);
        return listMap;
    }
}