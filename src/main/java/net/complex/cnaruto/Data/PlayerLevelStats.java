package net.complex.cnaruto.Data;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.complex.cnaruto.SkillLines.SkillLineData.SkillLineData;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.complex.cnaruto.events.eventtypes.LevelUpEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.registries.RegistryObject;
import org.stringtemplate.v4.misc.Misc;

import javax.annotation.Nullable;
import javax.print.attribute.standard.MediaSize;
import javax.swing.text.Element;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AutoRegisterCapability
public class PlayerLevelStats {

    Player belongingPlayer;

    void SetPlayer(Player player)
    {
        this.belongingPlayer = player;
    }

    Player GetPlayer()
    {
        return this.belongingPlayer;
    }



    public static int MAX_STAT_LEVEL = 100;

    private int level = 1;
    private int xp = 0;
    private int points = 0;
    private int MaxXP;

    private void RecalculateLevels()
    {
        if (this.level < 1000)
        {
            if (this.xp >= this.MaxXP)
            {
                level++;

                MinecraftForge.EVENT_BUS.post(new LevelUpEvent(belongingPlayer, this.level));

                int newPoints = 1 + ((int) this.level/100);
                AddPoints(newPoints);

                this.xp -= this.MaxXP;

                MaxXPCalculate();
                RecalculateLevels();
            }
        } else
        {
            this.xp = 0;
            MaxXPCalculate();
        }
    }

    public void SetLevel(int level)
    {
        this.level = level;
        this.xp = 0;
        MaxXPCalculate();
    }

    public int GetLevel()
    {
        return this.level;
    }

    public int GetXP()
    {
        return this.xp;
    }

    public void SetXP(int XP)
    {
        this.xp = XP;
        RecalculateLevels();
    }

    public void AddXP(int XP)
    {
        this.xp += XP;
        RecalculateLevels();
    }

    public int GetMaxXP()
    {
        return this.MaxXP;
    }

    private void MaxXPCalculate()
    {
        this.MaxXP = (int) (15*Math.pow(level, 1.274821));
    }

    public void SetPoints(int Points)
    {
        this.points = Points;
    }

    public int GetPoints()
    {
        return this.points;
    }

    public void AddPoints(int points)
    {
        this.points += points;
    }



    public boolean RemovePointsIfEnough(int points)
    {
        if (this.points - points >= 0)
        {
            this.points -= points;
            return true;
        }
        return false;
    }

    private int Spirit = 0;
    private int Dexterity = 0;
    private int Agility = 0;
    private int Ninjutsu = 0;

    public int GetSpirit()
    {
        return this.Spirit;
    }

    public void SetSpirit(int val)
    {
        this.Spirit = val;
    }

    public int GetDexterity()
    {
        return this.Dexterity;
    }

    public void SetDexterity(int val)
    {
        this.Dexterity = val;
    }

    public int GetAgility()
    {
        return this.Agility;
    }

    public void SetAgility(int val)
    {
        this.Agility = val;
    }

    public double getAgilitySpeedModifier()
    {
        return ((this.GetAgility()*(1.20/500.0)));
    }

    public int GetNinjutsu()
    {
        return this.Ninjutsu;
    }

    public void SetNinjutsu(int ninjutsu) {
        this.Ninjutsu = ninjutsu;
    }

    // For the basic Taijutsu, Kenjutsu, Medical ninjutsu, Sealing, Summoning skillines
    private int Taijutsu = 0;
    private int Kenjutsu = 0;
    private int Medical = 0;
    private int Sealing = 0;
    private int Summoning = 0;

    public int GetTaijutsu()
    {
        return this.Taijutsu;
    }

    public double GetAddedDamageTaijutsu()
    {
        return (this.GetTaijutsu() * (17.f/500.f));
    }

    public void SetTaijutsu(int Taijutsu)
    {
        this.Taijutsu = Taijutsu;
    }

    public int GetKenjutsu()
    {
        return this.Kenjutsu;
    }

    public void SetKenjutsu(int Kenjutsu)
    {
        this.Kenjutsu = Kenjutsu;
    }

    public int GetMedical()
    {
        return this.Medical;
    }

    public void SetMedical(int Medical)
    {
        this.Medical = Medical;
    }

    public int GetSealing()
    {
        return this.Sealing;
    }

    public void SetSealing(int Sealing)
    {
        this.Sealing = Sealing;
    }

    public int GetSummoning()
    {
        return this.Summoning;
    }

    public void SetSummoning(int Summoning)
    {
        this.Summoning = Summoning;
    }


    public ArrayList<SkillLineData> ElementReleases = new ArrayList<>();
    public ArrayList<SkillLineData> KekkeiReleases = new ArrayList<>();
    public ArrayList<SkillLineData> OtherReleases = new ArrayList<>(); // These exist purely for other SkillLines. Things like sage mode and juubi skillines

    // Data Saving time

    public int GetElementReleasesSize()
    {
        return ElementReleases.size();
    }

    public int GetKekkeiReleasesSize()
    {
        return KekkeiReleases.size();
    }

    public int GetOtherReleasesSize()
    {
        return OtherReleases.size();
    }

    public ArrayList<SkillLineData> GetElementReleases()
    {
        return this.ElementReleases;
    }

    public ArrayList<SkillLineData> GetKekkeiReleases()
    {
        return this.KekkeiReleases;
    }

    public ArrayList<SkillLineData> GetOtherReleases()
    {
        return this.OtherReleases;
    }


    public boolean PlayerHasSkillLine(RegistryObject<SkillLine> SkillLineRegistry)
    {
        SkillLineData data = GetPlayerSkillLineDataObject(SkillLineRegistry);
        return (data != null);

    }

    public int GetPlayerLevelWithSkillLine(RegistryObject<SkillLine> SkillLineRegistry)
    {
        SkillLineData data = GetPlayerSkillLineDataObject(SkillLineRegistry);
        if (data != null)
        {
            return data.GetLevel();
        } else {
            return -1;
        }
    }

    public SkillLineData GetPlayerSkillLineDataObject(RegistryObject<SkillLine> SkillLineRegistry)
    {
        SkillLineCategories Category = SkillLineRegistry.get().GetCategory();

        ArrayList<SkillLineData> ArrayListToSearch = null;

        switch (Category)
        {
            case ELEMENT:
                ArrayListToSearch = ElementReleases;
                break;
            case KEKKEI:
                ArrayListToSearch = KekkeiReleases;
                break;
            case MISC:
                ArrayListToSearch = OtherReleases;
        }

        for (int i = 0; i < ArrayListToSearch.size(); i++)
        {
            if (SkillLineRegistry.getId().getPath().equals(ArrayListToSearch.get(i).GetId()))
            {
                SkillLineData data = ArrayListToSearch.get(i);
                return data;
            }
        }

        return null;
    }



    public boolean AddSkillLineToPlayer(RegistryObject<SkillLine> SkillLineRegistry)
    {
        if (!PlayerHasSkillLine(SkillLineRegistry))
        {
            SkillLine SkillLine = SkillLineRegistry.get();
            SkillLineData Data = SkillLine.SkillLineToData(SkillLineRegistry, 1);
            switch (SkillLine.GetCategory())
            {
                case ELEMENT:
                    ElementReleases.add(Data);
                    break;
                case KEKKEI:
                    KekkeiReleases.add(Data);
                    break;
                case MISC:
                    OtherReleases.add(Data);
                    break;
            }
            return true;
        }
        return false;
    }

    public boolean RemoveSkillLineFromPlayer(RegistryObject<SkillLine> SkillLineRegistry)
    {
        if (PlayerHasSkillLine(SkillLineRegistry))
        {
            SkillLine SkillLine = SkillLineRegistry.get();
            ArrayList<SkillLineData> SelectedArray = null;
            switch (SkillLine.GetCategory())
            {
                case ELEMENT:
                    SelectedArray = ElementReleases;
                    break;
                case KEKKEI:
                    SelectedArray = KekkeiReleases;
                    break;
                case MISC:
                    SelectedArray = OtherReleases;
                    break;
            }

            for (int i = 0; i < SelectedArray.size(); i++)
            {
               if (SelectedArray.get(i).GetId().equals(SkillLineRegistry.getId().getPath()))
                {
                    SelectedArray.remove(i);
                }
            }

            return true;
        }
        return false;
    }

    private void LoadInCompoundTagSkillLines(CompoundTag Tag)
    {

    }

    public void copyFrom(PlayerLevelStats source)
    {
        this.belongingPlayer = source.belongingPlayer;
        this.level = source.level;
        this.xp = source.xp;
        this.points = source.points;



        this.Spirit = source.Spirit;
        this.Dexterity = source.Dexterity;
        this.Agility = source.Agility;
        this.Ninjutsu = source.Ninjutsu;
        this.Taijutsu = source.Taijutsu;
        this.Kenjutsu = source.Kenjutsu;
        this.Medical = source.Medical;
        this.Sealing = source.Sealing;
        this.Summoning = source.Summoning;

        this.ElementReleases = source.ElementReleases;
        this.KekkeiReleases = source.KekkeiReleases;
        this.OtherReleases = source.OtherReleases;

    }


    public void saveDataNBT(CompoundTag nbt)
    {
        nbt.putInt("level", level);
        nbt.putInt("experience", xp);
        nbt.putInt("ninjapoints", points);

        nbt.putInt("spirit", Spirit);
        nbt.putInt("dexterity", Dexterity);
        nbt.putInt("agility", Agility);
        nbt.putInt("ninjutsu", Ninjutsu);
        nbt.putInt("taijutsu", Taijutsu);
        nbt.putInt("kenjutsu", Kenjutsu);
        nbt.putInt("medical", Medical);
        nbt.putInt("sealing", Sealing);
        nbt.putInt("summoning", Summoning);

       ListTag ElementList = new ListTag();
       if (ElementReleases != null)
       {
           for (int ElementIndex = 0; ElementIndex < this.ElementReleases.size(); ElementIndex++)
           {
               CompoundTag Data = ElementReleases.get(ElementIndex).getCompoundTag();
               ElementList.addTag(ElementIndex, Data);
           }

       }
        nbt.put("naturereleases", ElementList);

        ListTag KGList = new ListTag();
        if (KekkeiReleases != null)
        {
            for (int KekkeiIndex = 0; KekkeiIndex < this.KekkeiReleases.size(); KekkeiIndex++)
            {
                CompoundTag Data = KekkeiReleases.get(KekkeiIndex).getCompoundTag();
                KGList.add(KekkeiIndex, Data);
            }

        }
        nbt.put("kekkeigenkai", KGList);

        ListTag MiscList = new ListTag();
        if (OtherReleases != null)
        {
            for (int OtherIndex = 0; OtherIndex < this.OtherReleases.size(); OtherIndex++)
            {
                CompoundTag Data = OtherReleases.get(OtherIndex).getCompoundTag();
                MiscList.add(OtherIndex, Data);
            }
        }
        nbt.put("miscskills", MiscList);






    }


    public void loadDataNBT(CompoundTag nbt)
    {

        SetLevel(nbt.getInt("level"));
        SetXP(nbt.getInt("experience"));
        this.points = nbt.getInt("ninjapoints");

        this.Spirit = nbt.getInt("spirit");
        this.Dexterity = nbt.getInt("dexterity");
        this.Agility = nbt.getInt("agility");
        this.Ninjutsu = nbt.getInt("ninjutsu");
        this.Taijutsu = nbt.getInt("taijutsu");
        this.Kenjutsu = nbt.getInt("kenjutsu");;
        this.Medical = nbt.getInt("medical");
        this.Sealing = nbt.getInt("sealing");
        this.Summoning = nbt.getInt("summoning");

        ElementReleases = new ArrayList<>();
        KekkeiReleases = new ArrayList<>();
        OtherReleases = new ArrayList<>();

        ListTag ElementList = (ListTag) nbt.get("naturereleases");
        ListTag KGList = (ListTag) nbt.get("kekkeigenkai");
        ListTag MiscList = (ListTag) nbt.get("miscskills");

        ProcessSkillList(ElementList);
        ProcessSkillList(KGList);
        ProcessSkillList(MiscList);




    }

    private void ProcessSkillList(ListTag Tag)
    {
        for (int i = 0; i < Tag.size(); i++)
        {
            CompoundTag SkillInfo = (CompoundTag) Tag.get(i);
            String id = SkillInfo.getString("lineid");
            RegistryObject<SkillLine> SkillLineRegistry = CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, new ResourceLocation(CNaruto.MODID, id));
            SkillLineData Data = SkillLineRegistry.get().CompoundTagToData(SkillInfo);

            switch (SkillLineRegistry.get().GetCategory())
            {
                case ELEMENT:
                    ElementReleases.add(Data);
                    break;
                case KEKKEI:
                    KekkeiReleases.add(Data);
                    break;
                case MISC:
                    OtherReleases.add(Data);
                    break;
            }



        }
    }

}
