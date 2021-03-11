package by.emel.anton.facade.converter;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Converter<SOURCE, TARGET> {

    default List<TARGET> convertAll(Collection<SOURCE> sources) {
        return CollectionUtils.emptyIfNull(sources).stream().map(this::convert).collect(Collectors.toList());
    }
    TARGET convert(SOURCE from);
}
