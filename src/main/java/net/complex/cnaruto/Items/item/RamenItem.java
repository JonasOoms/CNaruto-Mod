package net.complex.cnaruto.Items.item;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RamenItem extends Item {

    public RamenItem() {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(5)
                        .meat()
                        .build()
                )
        );
    }

    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        // give player an empty ramen bowl
        return this.isEdible() ? pLivingEntity.eat(pLevel, pStack) : pStack;
    }


}
