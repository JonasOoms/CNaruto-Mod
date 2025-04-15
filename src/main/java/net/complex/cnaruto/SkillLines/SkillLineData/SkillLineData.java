package net.complex.cnaruto.SkillLines.SkillLineData;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class SkillLineData implements ISkillLineData {

    private String SkillLineObjectId;

    private int Level;

    private List<String> SkillLineTags;

    public SkillLineData(RegistryObject<SkillLine> SkillLineRegistry, int level)
    {
        this.SkillLineObjectId = SkillLineRegistry.getId().getPath();
        this.Level = level;
    }

    public SkillLineData(CompoundTag SkillLineDataTag)
    {
        this.SkillLineObjectId = SkillLineDataTag.getString("lineid");
        this.Level = SkillLineDataTag.getInt("level");
    }

    public String GetId()
    {
        return this.SkillLineObjectId;
    }

    public int GetLevel()
    {
        return this.Level;
    }

    public void SetLevel(int level)
    {
        //SkillLine skillLine = CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, new ResourceLocation(CNaruto.MODID, this.SkillLineObjectId)).get();
        this.Level = level;
    }


    @Override
    public void AddNewDataToSkillLineData(CompoundTag tag) {

    }

    public final CompoundTag getCompoundTag() {
        CompoundTag DataTag = new CompoundTag();

        DataTag.putString("lineid", this.SkillLineObjectId);
        DataTag.putInt("level", this.Level);

        AddNewDataToSkillLineData(DataTag);

        ListTag SkillLineTagList = new ListTag();
        for (int TagListIndex = 0; TagListIndex < SkillLineTagList.size(); TagListIndex++)
        {
            StringTag Tag = StringTag.valueOf(SkillLineTags.get(TagListIndex));
            SkillLineTagList.addTag(TagListIndex, Tag);
        }
        return DataTag;
    }

}
