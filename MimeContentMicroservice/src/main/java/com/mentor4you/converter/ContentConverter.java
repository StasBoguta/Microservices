package com.mentor4you.converter;

public interface ContentConverter<T> {

    String convert(Iterable<T> entities);
}
