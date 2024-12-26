package mod.emt.harkenscythe.compat.groovyscript;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import com.cleanroommc.groovyscript.documentation.linkgenerator.LinkGeneratorHooks;
import mod.emt.harkenscythe.HarkenScythe;

public class HSGroovyScriptPlugin implements GroovyPlugin
{
    @GroovyBlacklist
    public static HSGroovyScriptContainer instance;

    @Override
    public @Nullable GroovyPropertyContainer createGroovyPropertyContainer()
    {
        if (instance == null) instance = new HSGroovyScriptContainer();
        return instance;
    }

    @Override
    public @Nonnull String getModId()
    {
        return HarkenScythe.MOD_ID;
    }

    @Override
    public @Nonnull String getContainerName()
    {
        return HarkenScythe.NAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> container)
    {
        LinkGeneratorHooks.registerLinkGenerator(new HarkenScytheLinkGenerator());
    }

    private static class HarkenScytheLinkGenerator extends BasicLinkGenerator
    {
        @Override
        public String id()
        {
            return HarkenScythe.MOD_ID;
        }

        @Override
        protected String domain()
        {
            return "https://github.com/Elite-Modding-Team/HarkenScytheResharpened/";
        }
    }
}
