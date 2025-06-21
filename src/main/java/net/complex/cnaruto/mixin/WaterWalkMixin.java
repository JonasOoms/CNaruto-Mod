package net.complex.cnaruto.mixin;

import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public class WaterWalkMixin {


    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void modifyWaterCollision(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext, CallbackInfoReturnable<VoxelShape> cir) {
        Entity entity = pContext instanceof EntityCollisionContext ecc ? ecc.getEntity() : null;

        if (pState.getFluidState().is(Fluids.WATER) && entity instanceof Player player) {
            if (ChakraManagerProvider.get(player).isChakraControlOn() && !player.isCrouching() && !player.isInWater()) {
                cir.setReturnValue(Shapes.block());
            }
        }
    }
//    @Inject(method = "canStandOnFluid", at = @At("HEAD"), cancellable = true )
//    public void canStandOnFluid(FluidState pFluidState, CallbackInfoReturnable<Boolean> cir) {
//        if ((Object) this instanceof Player player)
//        {
//            if (ChakraManagerProvider.get(player).isChakraControlOn() && !player.isCrouching())
//            {
//                if (pFluidState.is(Fluids.WATER) || pFluidState.is(Fluids.FLOWING_WATER))
//                {
//
//                    cir.setReturnValue(true);
//                }
//            }
//        }
//    }
}

