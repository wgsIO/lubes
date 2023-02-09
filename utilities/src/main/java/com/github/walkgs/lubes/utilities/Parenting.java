package com.github.walkgs.lubes.utilities;

import java.io.Closeable;
import java.util.Collection;

public interface Parenting<T> extends Closeable {

    void addParent(T parenting);

    void removeParent(T parenting);

    Collection<T> getParents();

}
