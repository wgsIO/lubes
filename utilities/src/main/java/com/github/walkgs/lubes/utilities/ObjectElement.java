package com.github.walkgs.lubes.utilities;

import com.github.walkgs.lubes.utilities.properties.Properties;
import com.github.walkgs.lubes.utilities.properties.SimpleProperties;
import lombok.Data;

@Data
public class ObjectElement<T> implements Element, Chargeable {

    private final long id;
    private final String name;
    private final Charge charge;
    private final T element;
    private final Properties properties = new SimpleProperties();

}
