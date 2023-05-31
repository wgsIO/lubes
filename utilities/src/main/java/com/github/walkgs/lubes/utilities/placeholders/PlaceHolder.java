package com.github.walkgs.lubes.utilities.placeholders;

import com.github.walkgs.lubes.utilities.Applicable;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;

public interface PlaceHolder extends Applicable<PlaceHolder> {

    boolean is(String value);
    Collection<String> find(String value);
    Collection<String> findAnd(String value, BiFunction<Matcher, String, String> function);

    String parse(String value, Map<String, Object> map);

}
