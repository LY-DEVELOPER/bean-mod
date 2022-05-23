package com.example.beanmod.blocks;
import com.example.beanmod.BeanMod;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class BeanCrop extends CropBlock{

    public BeanCrop(Properties p_52247_) {
        super(p_52247_);
        //TODO Auto-generated constructor stub
    }

    protected ItemLike getBaseSeedId() {
        return BeanMod.BEAN.get();
     }
    
}
