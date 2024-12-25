package mod.emt.harkenscythe.compat.groovyscript;

import com.cleanroommc.groovyscript.GroovyScript;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import mod.emt.harkenscythe.HarkenScythe;

public class HSGroovyScriptPlugin implements GroovyPlugin
{
    @Override
    public @NotNull String getModId()
    {
        return HarkenScythe.MOD_ID;
    }

    @Override
    public @NotNull String getContainerName()
    {
        return HarkenScythe.NAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> container)
    {
        GroovyScript.LOGGER.info(HarkenScythe.NAME + " container loaded");
    }
}
