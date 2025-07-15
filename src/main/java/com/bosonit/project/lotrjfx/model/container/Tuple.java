package com.bosonit.project.lotrjfx.model.container;

import java.util.Objects;

public record Tuple<T>(T first, T second) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?> tuple = (Tuple<?>) o;
        return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
