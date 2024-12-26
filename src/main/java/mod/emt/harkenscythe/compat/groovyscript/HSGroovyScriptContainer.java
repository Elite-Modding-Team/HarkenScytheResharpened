package mod.emt.harkenscythe.compat.groovyscript;

import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;

public class HSGroovyScriptContainer extends GroovyPropertyContainer
{
    public final HSGroovyScriptBloodAltarRecipes bloodAltar = new HSGroovyScriptBloodAltarRecipes();
    public final HSGroovyScriptSoulAltarRecipes soulAltar = new HSGroovyScriptSoulAltarRecipes();

    public HSGroovyScriptContainer()
    {
        addPropertyFieldsOf(this, false);
    }
}
