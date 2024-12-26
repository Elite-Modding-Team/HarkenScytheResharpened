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
import mod.emt.harkenscythe.recipe.HSRecipeSoulAltar;

@SuppressWarnings("unused")
@RegistryDescription(linkGenerator = HarkenScythe.MOD_ID)
public class HSGroovyScriptSoulAltarRecipes extends VirtualizedRegistry<HSRecipeSoulAltar>
{
    public HSGroovyScriptSoulAltarRecipes()
    {
        super(Alias.generateOf("SoulAltar"));
    }

    @Override
    public void onReload()
    {
        HSAltarRecipes.getSoulAltarRecipes().removeAll(removeScripted());
        HSAltarRecipes.getSoulAltarRecipes().addAll(restoreFromBackup());
    }

    public void add(HSRecipeSoulAltar t)
    {
        HSAltarRecipes.getSoulAltarRecipes().add(t);
        addScripted(t);
    }

    public boolean remove(HSRecipeSoulAltar t)
    {
        if (HSAltarRecipes.getSoulAltarRecipes().remove(t))
        {
            addBackup(t);
            return true;
        }
        return false;
    }

    @MethodDescription(type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<HSRecipeSoulAltar> streamRecipes()
    {
        return new SimpleObjectStream<>(HSAltarRecipes.getSoulAltarRecipes()).setRemover(this::remove);
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = @Example("item('harkenscythe:soul_cake')"))
    public boolean removeByOutput(IIngredient output)
    {
        return HSAltarRecipes.getSoulAltarRecipes().removeIf(r -> {
            if (output.test(r.getOutput()))
            {
                addBackup(r);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = @Example("item('minecraft:cookie')"))
    public boolean removeByInput(IIngredient input)
    {
        return HSAltarRecipes.getSoulAltarRecipes().removeIf(r -> {
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
        HSAltarRecipes.getSoulAltarRecipes().forEach(this::addBackup);
        HSAltarRecipes.getSoulAltarRecipes().clear();
    }

    @RecipeBuilderDescription(example = @Example(".input(item('minecraft:gravel')).output(item('minecraft:sand')).requiredSouls(69)"))
    public RecipeBuilder recipeBuilder()
    {
        return new RecipeBuilder();
    }

    @Property(property = "input", valid = @Comp("1"))
    @Property(property = "output", valid = @Comp("1"))
    public static class RecipeBuilder extends AbstractRecipeBuilder<HSRecipeSoulAltar>
    {
        @Property(valid = @Comp(type = Comp.Type.GTE, value = "1"))
        private int requiredSouls;

        @RecipeBuilderMethodDescription
        public RecipeBuilder requiredSouls(int requiredSouls)
        {
            this.requiredSouls = requiredSouls;
            return this;
        }

        @Override
        public String getErrorMsg()
        {
            return "Error adding Harken Scythe Soul Altar ritual recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg)
        {
            validateItems(msg, 1, 1, 1, 1);
            msg.add(requiredSouls < 1, "requiredSouls must be 1 or greater");
        }

        @Override
        @Nullable
        @RecipeBuilderRegistrationMethod
        public HSRecipeSoulAltar register()
        {
            if (!validate()) return null;
            HSRecipeSoulAltar t = null;
            for (ItemStack in : input.get(0).getMatchingStacks())
            {
                t = new HSRecipeSoulAltar(in, output.get(0), requiredSouls);
                HSGroovyScriptPlugin.instance.soulAltar.add(t);
            }
            return t;
        }
    }
}
