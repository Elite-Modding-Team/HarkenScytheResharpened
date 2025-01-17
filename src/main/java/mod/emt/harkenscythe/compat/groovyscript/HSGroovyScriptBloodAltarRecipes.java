package mod.emt.harkenscythe.compat.groovyscript;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.recipe.HSRecipeBloodAltar;

@SuppressWarnings("unused")
@RegistryDescription(linkGenerator = HarkenScythe.MOD_ID)
public class HSGroovyScriptBloodAltarRecipes extends VirtualizedRegistry<HSRecipeBloodAltar>
{
    public HSGroovyScriptBloodAltarRecipes()
    {
        super(Alias.generateOf("BloodAltar"));
    }

    @Override
    public void onReload()
    {
        HSAltarRecipes.getBloodAltarRecipes().removeAll(removeScripted());
        HSAltarRecipes.getBloodAltarRecipes().addAll(restoreFromBackup());
    }

    public void add(HSRecipeBloodAltar t)
    {
        HSAltarRecipes.getBloodAltarRecipes().add(t);
        addScripted(t);
    }

    public boolean remove(HSRecipeBloodAltar t)
    {
        if (HSAltarRecipes.getBloodAltarRecipes().remove(t))
        {
            addBackup(t);
            return true;
        }
        return false;
    }

    @MethodDescription(type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<HSRecipeBloodAltar> streamRecipes()
    {
        return new SimpleObjectStream<>(HSAltarRecipes.getBloodAltarRecipes()).setRemover(this::remove);
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = @Example("item('harkenscythe:bloodweave_cloth')"))
    public boolean removeByOutput(IIngredient output)
    {
        return HSAltarRecipes.getBloodAltarRecipes().removeIf(r -> {
            if (output.test(r.getOutput()))
            {
                addBackup(r);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = @Example("item('minecraft:glass_bottle')"))
    public boolean removeByInput(IIngredient input)
    {
        return HSAltarRecipes.getBloodAltarRecipes().removeIf(r -> {
            if (input.test(r.getInput()))
            {
                addBackup(r);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, priority = 2000, example = @Example(commented = true))
    public void removeAll()
    {
        HSAltarRecipes.getBloodAltarRecipes().forEach(this::addBackup);
        HSAltarRecipes.getBloodAltarRecipes().clear();
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:cobblestone')).output(item('minecraft:gravel')).requiredBlood(42)"))
    public RecipeBuilder recipeBuilder()
    {
        return new RecipeBuilder();
    }

    @Property(property = "input", comp = @Comp(eq = 1))
    @Property(property = "output", comp = @Comp(eq = 1))
    public static class RecipeBuilder extends AbstractRecipeBuilder<HSRecipeBloodAltar>
    {
        @Property(comp = @Comp(gte = 1))
        private int requiredBlood;

        @RecipeBuilderMethodDescription
        public RecipeBuilder requiredBlood(int requiredBlood)
        {
            this.requiredBlood = requiredBlood;
            return this;
        }

        @Override
        public String getErrorMsg()
        {
            return "Error adding Harken Scythe Blood Altar ritual recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg)
        {
            validateItems(msg, 1, 1, 1, 1);
            msg.add(requiredBlood < 1, "requiredBlood must be 1 or greater");
        }

        @Override
        @Nullable
        @RecipeBuilderRegistrationMethod
        public HSRecipeBloodAltar register()
        {
            if (!validate()) return null;
            HSRecipeBloodAltar t = null;
            for (ItemStack in : input.get(0).getMatchingStacks())
            {
                t = new HSRecipeBloodAltar(in, output.get(0), requiredBlood);
                HSGroovyScriptPlugin.instance.bloodAltar.add(t);
            }
            return t;
        }
    }
}
