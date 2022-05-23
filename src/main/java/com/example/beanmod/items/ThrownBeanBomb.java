package com.example.beanmod.items;

import com.example.beanmod.BeanMod;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownBeanBomb extends ThrowableItemProjectile {

  public ThrownBeanBomb(EntityType<? extends ThrownBeanBomb> p_37473_, Level p_37474_) {
    super(p_37473_, p_37474_);
 }

 public ThrownBeanBomb(Level p_37481_, LivingEntity p_37482_) {
    super(BeanMod.BEANBOMBENT.get(), p_37482_, p_37481_);
 }

 public ThrownBeanBomb(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
    super(BeanMod.BEANBOMBENT.get(), p_37477_, p_37478_, p_37479_, p_37476_);
 }

 public void handleEntityEvent(byte p_37484_) {
    if (p_37484_ == 3) {


       for(int i = 0; i < 8; ++i) {
          this.level.addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 10D, 10D, 10D);
       }
    }

 }

 protected void onHitEntity(EntityHitResult p_37486_) {
    super.onHitEntity(p_37486_);
    p_37486_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
 }

 protected void onHit(HitResult p_37488_) {
    super.onHit(p_37488_);
    if (!this.level.isClientSide) {
             this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, Explosion.BlockInteraction.BREAK);
       }
       this.level.broadcastEntityEvent(this, (byte)3);
       this.discard();
    }

 protected Item getDefaultItem() {
    return BeanMod.BEANBOMB.get();
 }
}

