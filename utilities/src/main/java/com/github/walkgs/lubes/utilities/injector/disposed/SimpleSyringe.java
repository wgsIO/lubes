package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.Applicable;
import com.github.walkgs.lubes.utilities.Strings;
import com.github.walkgs.lubes.utilities.injector.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
@AllArgsConstructor
public class SimpleSyringe implements Syringe, Applicable<SimpleSyringe> {
    private Configurator configurator;

    @Getter
    private Collection<Syringe> parents;

    public SimpleSyringe(Configurator configurator) {
        this.configurator = configurator;
        this.parents = new ConcurrentLinkedQueue<>();
    }

    public void init() {
        configurator.configure();
    }

    @Override
    public <T> Class<? extends T> getInjectable(String name, Class<T> type) {
        final HashSet<Syringe> syringes = new HashSet<>();
        syringes.add(this);
        getParentsSyringes(this, syringes);
        for (Syringe syringe : syringes) {
            final Configurator configurator = syringe.getConfigurator();
            final Storage storage = configurator.getStorage();
            final Map<Class<?>, ElementList<Class<?>>> injectables = storage.getInjectables();
            final ElementList<Class<?>> elementList = injectables.get(type);
            if (elementList == null)
                continue;
            final Element<Class<?>> element = elementList.get(Strings.isNullOrBlank(name) ? Element.DEFAULT_NAME : name);
            if (element == null)
                continue;
            return element.get().asSubclass(type);
        }
        throw new IllegalArgumentException("No inject binded for type: " + type);
    }

    private void getParentsSyringes(Syringe syringe, HashSet<Syringe> set) {
        for (Syringe parent : syringe.getParents()) {
            if (set.contains(parent))
                continue;
            set.add(parent);
            getParentsSyringes(parent, set);
        }
    }

    @Override
    public void close() throws IOException {
        configurator = null;
        for (Syringe parent : parents)
            removeParent(parent);
        parents = null;
    }

    @Override
    public void addParent(Syringe parenting) {
        this.parents.add(parenting);
        parenting.getParents().add(this);
    }

    @Override
    public void removeParent(Syringe parenting) {
        this.parents.remove(parenting);
        parenting.getParents().remove(this);
    }

}
