package mod.emt.harkenscythe.advancement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class HSAdvancementTrigger implements ICriterionTrigger
{
    private final ResourceLocation id;
    private final Map<PlayerAdvancements, Listeners> listeners = Maps.newHashMap();

    public HSAdvancementTrigger(String string)
    {
        super();
        id = new ResourceLocation(string);
    }

    public HSAdvancementTrigger(ResourceLocation resourceLocation)
    {
        super();
        id = resourceLocation;
    }

    @Override
    @Nonnull
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addListener(PlayerAdvancements playerAdvancements, ICriterionTrigger.Listener listener)
    {
        HSAdvancementTrigger.Listeners customTrigger$listeners = this.listeners.get(playerAdvancements);

        if (customTrigger$listeners == null)
        {
            customTrigger$listeners = new HSAdvancementTrigger.Listeners(playerAdvancements);
            this.listeners.put(playerAdvancements, customTrigger$listeners);
        }

        customTrigger$listeners.add(listener);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removeListener(PlayerAdvancements playerAdvancements, ICriterionTrigger.Listener listener)
    {
        HSAdvancementTrigger.Listeners customTrigger$listeners = this.listeners.get(playerAdvancements);

        if (customTrigger$listeners != null)
        {
            customTrigger$listeners.remove(listener);

            if (customTrigger$listeners.isEmpty())
            {
                this.listeners.remove(playerAdvancements);
            }
        }
    }

    @Override
    public void removeAllListeners(@Nonnull PlayerAdvancements playerAdvancements)
    {
        this.listeners.remove(playerAdvancements);
    }

    @Override
    @Nonnull
    public HSAdvancementTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        return new HSAdvancementTrigger.Instance(this.getId());
    }

    public void trigger(EntityPlayerMP parPlayer)
    {
        HSAdvancementTrigger.Listeners customTrigger$listeners = this.listeners.get(parPlayer.getAdvancements());

        if (customTrigger$listeners != null)
        {
            customTrigger$listeners.trigger(parPlayer);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {
        public Instance(ResourceLocation parID)
        {
            super(parID);
        }

        public boolean test()
        {
            return true;
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener> listenerSet = new HashSet<>();

        public Listeners(PlayerAdvancements playerAdvancements)
        {
            this.playerAdvancements = playerAdvancements;
        }

        public boolean isEmpty()
        {
            return this.listenerSet.isEmpty();
        }

        public void add(ICriterionTrigger.Listener listener)
        {
            this.listenerSet.add(listener);
        }

        public void remove(ICriterionTrigger.Listener listener)
        {
            this.listenerSet.remove(listener);
        }

        public void trigger(EntityPlayerMP player)
        {
            List<Listener> list = null;

            for (ICriterionTrigger.Listener listener : this.listenerSet)
            {
                if (((Instance) listener.getCriterionInstance()).test())
                {
                    if (list == null)
                    {
                        list = new ArrayList<>();
                    }

                    list.add(listener);
                }
            }

            if (list != null)
            {
                for (Object listener1 : list)
                {
                    ((ICriterionTrigger.Listener) listener1).grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
