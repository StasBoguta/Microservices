package com.mentor4you.converter;

import java.util.List;

public interface ContentConverter<T> {

    String convert(List<T> entities);
}
