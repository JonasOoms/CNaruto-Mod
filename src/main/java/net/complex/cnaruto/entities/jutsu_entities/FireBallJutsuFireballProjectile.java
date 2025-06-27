package net.complex.cnaruto.entities.jutsu_entities;

import net.complex.cnaruto.entities.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;



public class FireBallJutsuFireballProjectile extends AbstractHurtingProjectile {

    private static final int EXPLOSION_POWER = 3;


    public FireBallJutsuFireballProjectile(EntityType<? extends FireBallJutsuFireballProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }

    public FireBallJutsuFireballProjectile(Level world, Player shooter, double dirX, double dirY, double dirZ)
    {
        super(ModEntities.FIREBALLJUTSU_FIREBALL.get(), world);
        setDeltaMovement(dirX, dirY, dirZ);



    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level().isClientSide || (entity == null || !entity.isRemoved())) {
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.setPos(d0, d1, d2);

        } else {
            this.discard();
        }

        Vec3 vec3 = this.getDeltaMovement();

        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {return false;}

    @Override
    protected void onHit(HitResult result) {
        if (level().isClientSide) {
            super.onHit(result);
            return;
        }

        if (result.getType() == HitResult.Type.BLOCK) {
            level().explode(this, getX(), getY(), getZ(),
                    EXPLOSION_POWER,
                    Level.ExplosionInteraction.MOB);
            this.discard();
            System.out.println("Explode on block!");
            return;
        }

        if (result.getType() == HitResult.Type.ENTITY) {

            level().explode(this, getX(), getY(), getZ(),
                    EXPLOSION_POWER,
                    Level.ExplosionInteraction.MOB);
            this.discard();
            System.out.println("Explode on entity!");
            return;

        }
    }

    @Override
    public boolean shouldBeSaved()
    {
        return true;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        Vec3 v = getDeltaMovement();
        tag.putDouble("velX", v.x);
        tag.putDouble("velY", v.y);
        tag.putDouble("velZ", v.z);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("velX")) {
            setDeltaMovement(
                    tag.getDouble("velX"),
                    tag.getDouble("velY"),
                    tag.getDouble("velZ")
            );
        }
    }

    @Override
    protected void defineSynchedData() {

    }
}
