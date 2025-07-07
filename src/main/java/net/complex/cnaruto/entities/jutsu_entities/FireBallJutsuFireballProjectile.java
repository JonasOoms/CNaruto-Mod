package net.complex.cnaruto.entities.jutsu_entities;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLineData.SkillLineData;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.client.rendering.ExplosionHandler.CExplosionHandler;
import net.complex.cnaruto.client.rendering.ExplosionHandler.CExplosions;
import net.complex.cnaruto.entities.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.awt.*;


public class FireBallJutsuFireballProjectile extends AbstractHurtingProjectile implements GeoEntity {

    private static final int EXPLOSION_POWER = 1;

    private static final EntityDataAccessor<Float> POWER = SynchedEntityData.defineId(FireBallJutsuFireballProjectile.class, EntityDataSerializers.FLOAT);

    public FireBallJutsuFireballProjectile(EntityType<? extends FireBallJutsuFireballProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }

    public FireBallJutsuFireballProjectile(Level world, Player shooter, double dirX, double dirY, double dirZ)
    {
        super(ModEntities.FIREBALLJUTSU_FIREBALL.get(), world);
        this.setOwner(shooter);
        setDeltaMovement(dirX, dirY, dirZ);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        PlayerLevelStats stats = PlayerLevelStatsProvider.get((Player) getOwner());
        SkillLineData data = stats.GetPlayerSkillLineDataObject(SkillLineRegister.FIRE_RELEASE);

        SetPower(1 + (3 * ((float) data.GetLevel() /SkillLineRegister.FIRE_RELEASE.get().MaxLevel)));
        this.refreshDimensions();
    }

    public float GetPower()
    {
        return this.entityData.get(POWER);
    }

    public void SetPower(float power)
    {
        this.entityData.set(POWER, power);
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
    public EntityDimensions getDimensions(Pose pose)
    {
        return EntityDimensions.scalable(GetPower(),GetPower());
    }

    @Override
    protected void onHit(HitResult result) {
        if (level().isClientSide) {
            super.onHit(result);
            return;
        }

        if (result.getType() == HitResult.Type.BLOCK) {
            Explosion explosion = this.level().explode(this, getX(), getY(), getZ(),
                    EXPLOSION_POWER + GetPower(), true,
                    Level.ExplosionInteraction.MOB);


            CExplosionHandler.SendExplosionEffect(CExplosions.DefaultExplosionEffect, explosion.getPosition(), GetPower() + 3 , Color.ORANGE, 20);

            this.discard();
            return;
        }

        if (result.getType() == HitResult.Type.ENTITY) {

            Explosion explosion = level().explode(this, getX(), getY(), getZ(),
                    GetPower(),
                    true,
                    Level.ExplosionInteraction.MOB);
            CExplosionHandler.SendExplosionEffect(CExplosions.DefaultExplosionEffect, explosion.getPosition(), GetPower() + 3, Color.ORANGE, 20);
            this.discard();
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
        super.defineSynchedData();
        this.entityData.define(POWER, 1.f);
    }

    protected static final RawAnimation POP_ANIM = RawAnimation.begin().then("animation.fireball.pop", Animation.LoopType.PLAY_ONCE);

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<FireBallJutsuFireballProjectile>(this, "pop_controller", state -> PlayState.STOP)
                .triggerableAnim("pop", POP_ANIM));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
