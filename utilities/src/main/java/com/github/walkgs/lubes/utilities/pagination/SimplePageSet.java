package com.github.walkgs.lubes.utilities.pagination;

import com.github.walkgs.lubes.utilities.ArraysUtils;
import com.github.walkgs.lubes.utilities.exceptions.ArrayIndexLimitException;
import com.github.walkgs.lubes.utilities.exceptions.KeyAlreadyExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class SimplePageSet<T> implements PageSet<T> {

    private static final int DEFAULT_LIMIT = 0;
    private static final int DEFAULT_PAGES_CAPACITY = 10;
    @Getter
    private final int limit;
    @Getter
    private final int pagesCapacity;

    private Page<T>[] pages = new Page[DEFAULT_LIMIT];

    public SimplePageSet() {
        this.limit = DEFAULT_LIMIT;
        this.pagesCapacity = DEFAULT_PAGES_CAPACITY;
    }

    public SimplePageSet(int pagesCapacity) {
        this.limit = DEFAULT_LIMIT;
        this.pagesCapacity = pagesCapacity;
    }

    @Override
    public int getSize() {
        return pages.length;
    }

    @Override
    public Set<Page<T>> getPages() {
        return Set.of(pages);
    }

    @Override
    public Page<T> addPage() {
        if (limit != 0 && pages.length >= limit)
            throw new ArrayIndexLimitException();
        return new SimplePage<T>(pagesCapacity).apply(this::addPage);
    }

    @Override
    public int addPage(Page<T> page) {
        final int lastLength = pages.length;
        if (limit != 0 && lastLength >= limit)
            throw new ArrayIndexLimitException();
        for (final Page<?> $page : getPages()) {
            if ($page != null && $page.equals(page))
                throw new KeyAlreadyExistsException();
        }
        Objects.requireNonNull(page);
        for (int index = 0; index < lastLength; index++) {
            final Page<T> $page = pages[index];
            if ($page != null)
                continue;
            pages[index] = page;
            return index;
        }
        pages = Arrays.copyOf(pages, lastLength + 1);
        pages[lastLength] = page;
        return lastLength;
    }

    @Override
    public int removePage(Page<T> page) {
        for (int index = 0; index < pages.length; index++) {
            final Page<?> $page = pages[index];
            if ($page != null && $page.equals(page)) {
                pages = ArraysUtils.copy(pages, 0, pages.length - 1, index);
                return index;
            }
        }
        return -1;
    }

    @Override
    public Page<T> getPage(int index) {
        if (limit != 0 && index > limit)
            throw new ArrayIndexOutOfBoundsException(index);
        return pages[index];
    }

    @Override
    public int add(T value) {
        return 0;
    }

    @Override
    public void set(int index, T value) {

    }

    @Override
    public void remove(T value) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public void clear() {
        pages = new Page[DEFAULT_LIMIT];
    }

    @Override
    public Iterator<Page<T>> iterator() {
        return getPages().iterator();
    }

}
