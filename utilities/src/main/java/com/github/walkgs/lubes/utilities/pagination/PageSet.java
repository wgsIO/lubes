package com.github.walkgs.lubes.utilities.pagination;

import com.github.walkgs.lubes.utilities.Applicable;

import java.util.Set;

public interface PageSet<T> extends Applicable<PageSet<T>>, Iterable<Page<T>> {

    int getLimit();

    int getPagesCapacity();

    int getSize();

    Set<Page<T>> getPages();

    Page<T> addPage();

    int addPage(Page<T> page);

    int removePage(Page<T> page);

    Page<T> getPage(int index);

    int add(T value);

    void set(int index, T value);

    void remove(T value);

    T remove(int index);

    void clear();

}
