package com.example.beanmod.items;

import org.jetbrains.annotations.ApiStatus.OverrideOnly;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class BeanBombRender extends ThrownItemRenderer<ThrownBeanBomb> {

    public BeanBombRender(Context p_174414_) {
        super(p_174414_);
        //TODO Auto-generated constructor stub
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownBeanBomb beanbomb) {
        return new ResourceLocation("beanmod", "textures/items/beanbomb.png");
    }
}