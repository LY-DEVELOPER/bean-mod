package com.example.beanmod.items;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class BeanBombRender extends ThrownItemRenderer<ThrownBeanBomb> {
    public BeanBombRender(Context p_174414_) {
        super(p_174414_);
        //TODO Auto-generated constructor stub
    }

    public static final ResourceLocation TEXTURE = new ResourceLocation("beanmod", "textures/items/beanbomb.png");


    public ResourceLocation getTextureLocation(ThrownBeanBomb arrow) {
        return TEXTURE;
    }
}