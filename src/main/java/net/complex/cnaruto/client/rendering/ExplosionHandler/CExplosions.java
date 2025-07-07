package net.complex.cnaruto.client.rendering.ExplosionHandler;

import net.complex.cnaruto.client.rendering.ExplosionHandler.Explosions.DefaultExplosionEffect;

public class CExplosions {

    public static final CExplosionRegistrar registrar = new CExplosionRegistrar();

    public static final CExplosionEffect DefaultExplosionEffect = registrar.register(new DefaultExplosionEffect());


}
