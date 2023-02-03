package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.injector.annotation.Priority;

import java.lang.reflect.AnnotatedElement;
import java.util.Comparator;

public class SimplePriorityComparator implements Comparator<AnnotatedElement> {

    @Override
    public int compare(AnnotatedElement element1, AnnotatedElement element2) {
        final Priority annotation1 = element1.getAnnotation(Priority.class);
        final Priority annotation2 = element2.getAnnotation(Priority.class);
        final int priority1 = annotation1 != null ? annotation1.value() : 1;
        final int priority2 = annotation2 != null ? annotation2.value() : 1;
        return Integer.compare(priority1, priority2);
    }

}
