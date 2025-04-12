package net.complex.cnaruto.SkillLines;

import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.SkillLines.SkillLineData.SkillLineData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class SkillLine {



    private final String name;
    private final String description;
    private final ResourceLocation icon;
    private final SkillLineCategories category;

    private Map<String, Jutsu> JutsuMap;

    public SkillLine(String name, String description, ResourceLocation icon, SkillLineCategories Category)
    {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.category = Category;
        this.JutsuMap = new HashMap<>();

    }

    public List<Jutsu> GetJutsuAsList()
    {
        List<Jutsu> JutsuList = new ArrayList<>();
        JutsuMap.forEach((key, value) -> {
            JutsuList.add(value);
        });
        return JutsuList;
    }

    public Map<String, Jutsu> GetJutsuAsMap()
    {
        return this.JutsuMap;
    }

    public boolean
    AddJutsu(String id, Jutsu JutsuObject)
    {
        if (JutsuMap.containsKey(id))
        {
            return false;
        }
        else
        {
            JutsuMap.put(id, JutsuObject);
            return true;
        }

    }

    public Jutsu GetJutsu(String Key)
    {
        return JutsuMap.get(Key);
    }

    public boolean ContainsJutsu(Jutsu JutsuObject)
    {
        if (JutsuMap.containsValue(JutsuObject))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String GetName()
    {
        return this.name;
    }

    public String GetDescription()
    {
        return this.description;
    }

    public ResourceLocation GetIcon()
    {
        return this.icon;
    }

    public SkillLineCategories GetCategory()
    {
        return this.category;
    }

    public SkillLineData SkillLineToData(RegistryObject<SkillLine> RegistryObject, int level)
    {
        return new SkillLineData(RegistryObject, level);
    }

    public SkillLineData CompoundTagToData(CompoundTag Data)
    {
        return new SkillLineData(Data);
    }



}
