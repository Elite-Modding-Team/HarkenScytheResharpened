package mod.emt.harkenscythe.compat.crafttweaker;

import java.util.LinkedList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

public class HSCraftTweakerPlugin
{
    protected static final List<IAction> LATE_REMOVALS = new LinkedList<>();
    protected static final List<IAction> LATE_ADDITIONS = new LinkedList<>();

    public static void applyActions()
    {
        try
        {
            LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            CraftTweakerAPI.logError("Error while applying actions", e);
        }
        LATE_REMOVALS.clear();
        LATE_ADDITIONS.clear();
    }
}
