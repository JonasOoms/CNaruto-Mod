package net.complex.cnaruto.Data;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuRegister;
import net.complex.cnaruto.api.CUtils;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.EquippedJutsuSyncWithServerC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.complex.cnaruto.Jutsu.JutsuInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class EquippedJutsu {


    private JutsuInstance[] equippedJutsuList = new JutsuInstance[slots];
    public static final int slots = 6;

    public JutsuInstance get(int slot)
    {
        return equippedJutsuList[slot];
    }

    public Jutsu getJutsuInSlot(int slot)
    {
        if (slot >= 0 && slot <= slots)
        {
            JutsuInstance jutsuInstanceReturn = equippedJutsuList[slot];
            if (jutsuInstanceReturn != null)
            {
                return jutsuInstanceReturn.jutsu;
            }
        } else
        {
            return null;
        }
        return null;
    }
    public void setJutsuInSlot(int slot, RegistryObject<Jutsu> jutsu)
    {
        // this method needs to know if its being set on the client or the server. This is for syncing purposes
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            JutsuInstance newJutsuInstance = new JutsuInstance(jutsu);
            equippedJutsuList[slot] = newJutsuInstance;

            CompoundTag syncTag = new CompoundTag();
            this.serializeNBT(syncTag);
            ModMessages.SendToServer(new EquippedJutsuSyncWithServerC2SPacket(syncTag));
        } else
        {
            System.out.println("[CNARUTO]: The server was attempting to set jutsu slot. This is not allowed.");
        }
    }

    public boolean isJutsuEquipped(Jutsu jutsu)
    {
        boolean isJutsuEquipped = false;
        for (JutsuInstance jutsuInstance : equippedJutsuList)
        {
            if (jutsuInstance != null)
            {
                if (jutsuInstance.jutsu.equals(jutsu))
                {
                    isJutsuEquipped = true;
                }
            }
        }
        return isJutsuEquipped;
    }

    public void copyFrom(EquippedJutsu source)
    {
        this.equippedJutsuList = source.equippedJutsuList;
    }

    public void deserializeNBT(CompoundTag tag)
    {
        ListTag equippedJutsuListAsTag = (ListTag) tag.get("equippedjutsu");
        for (int jutsuIdIdx = 0; jutsuIdIdx < equippedJutsuListAsTag.size(); jutsuIdIdx++)
        {
            CompoundTag jutsuInstanceTag = (CompoundTag) equippedJutsuListAsTag.get(jutsuIdIdx);
            if (!jutsuInstanceTag.getString("jutsuId").equals("null"))
            {
                JutsuInstance jutsuInstance = new JutsuInstance((CompoundTag) equippedJutsuListAsTag.get(jutsuIdIdx));
                equippedJutsuList[jutsuIdIdx] = jutsuInstance;
            } else
            {
                JutsuInstance jutsuInstance = null;
                equippedJutsuList[jutsuIdIdx] = jutsuInstance;
            }



        }

    }

    public void serializeNBT(CompoundTag tag)
    {
        ListTag equippedJutsuListAsTag = new ListTag();

        for (int jutsuInstanceId = 0; jutsuInstanceId < slots; jutsuInstanceId++)
        {
            JutsuInstance instance = equippedJutsuList[jutsuInstanceId];
            if (instance != null)
            {
                CompoundTag JutsuInstanceTag = instance.serializeNBT();
                equippedJutsuListAsTag.add(JutsuInstanceTag);
            } else
            {
                CompoundTag JutsuInstanceTag = JutsuInstance.NullJutsuInstance();
                equippedJutsuListAsTag.add(JutsuInstanceTag);
            }
        }
        tag.put("equippedjutsu", equippedJutsuListAsTag);
    }


}
