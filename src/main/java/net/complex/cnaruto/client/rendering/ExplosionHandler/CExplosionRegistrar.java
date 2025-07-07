package net.complex.cnaruto.client.rendering.ExplosionHandler;

import java.util.HashMap;
import java.util.function.Supplier;

public class CExplosionRegistrar {

    int currentId = 0;
    private HashMap<Integer, CExplosionEffect> RegisteredExplosions = new HashMap<>();

    CExplosionEffect Get(int id) {
        return RegisteredExplosions.get(id);
    }

    public CExplosionEffect register(CExplosionEffect effect) {

        int id = currentId;
        RegisteredExplosions.put(id, effect);
        effect.register(id);
        currentId++;
        return effect;
    }

}
