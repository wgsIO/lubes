package com.github.walkgs.lubes.utilities.placeholders;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class SimplePlaceHolder implements PlaceHolder {

    private static final Pattern PLACE_HOLDER_PATTERN = Pattern.compile("\\{[^}]*\\}");
    private static final String EMPTY_STRING = "";

    private final Pattern pattern;

    public SimplePlaceHolder() {
        this.pattern = PLACE_HOLDER_PATTERN;
    }

    @Override
    public boolean is(String value) {
        return pattern.matcher(value).matches();
    }

    @Override
    public Collection<String> find(String value) {
        return findAnd(value, null);
    }

    @Override
    public Collection<String> findAnd(String value, BiFunction<Matcher, String, String> function) {
        final Matcher matcher = pattern.matcher(value);
        final Collection<String> results = new ArrayList<>();
        while (matcher.find()) {
            final String key = matcher.group();
            results.add(key);
            if (function == null)
                continue;
            value = function.apply(matcher, value);
        }
        return results;
    }

    @Override
    public String parse(String value, Map<String, Object> map) {
        final Matcher matcher = pattern.matcher(value);
        final StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            final String key = matcher.group();
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(map.getOrDefault(key, key).toString()));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

}
