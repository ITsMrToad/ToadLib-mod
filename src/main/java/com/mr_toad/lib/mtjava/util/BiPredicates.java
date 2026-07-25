package com.mr_toad.lib.mtjava.util;

import org.jetbrains.annotations.Nullable;
import java.util.function.BiPredicate;

public class BiPredicates {

    public static<O, O1> BiPredicate<O, O1> alwaysTrue() {
        return ObjectBiPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    public static<O, O1> BiPredicate<O, O1> alwaysFalse() {
        return ObjectBiPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    public static<O, O1> BiPredicate<O, O1> isNullBoth() {
        return ObjectBiPredicate.IS_NULL_AND.withNarrowedType();
    }

    public static<O, O1> BiPredicate<O, O1> isNullOne() {
        return ObjectBiPredicate.IS_NULL_OR.withNarrowedType();
    }

    public static<O, O1> BiPredicate<O, O1> nonNullBoth() {
        return ObjectBiPredicate.NON_NULL_AND.withNarrowedType();
    }

    public static<O, O1> BiPredicate<O, O1> nonNullOne() {
        return ObjectBiPredicate.NON_NULL_OR.withNarrowedType();
    }

    enum ObjectBiPredicate implements BiPredicate<@Nullable Object, @Nullable Object> {
        ALWAYS_TRUE {
            @Override
            public boolean test(Object o1, Object o2) {
                return true;
            }
        },
        ALWAYS_FALSE {
            @Override
            public boolean test(Object o1, Object o2) {
                return false;
            }
        },
        IS_NULL_AND {
            @Override
            public boolean test(Object o1, Object o2) {
                return o1 == null && o2 == null;
            }
            },
        IS_NULL_OR {
            @Override
            public boolean test(Object o1, Object o2) {
                return o1 == null || o2 == null;
            }
        },
        NON_NULL_AND {
            @Override
            public boolean test(Object o1, Object o2) {
                return o1 != null && o2 != null;
            }
        },
        NON_NULL_OR {
            @Override
            public boolean test(Object o1, Object o2) {
                return o1 != null || o2 != null;
            }
        };

        @SuppressWarnings("unchecked")
        public <T, T1> BiPredicate<T, T1> withNarrowedType() {
            return (BiPredicate<T, T1>) this;
        }
    }
}
