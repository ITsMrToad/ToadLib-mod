package com.mr_toad.lib.mtjava.util.quad;

import com.mr_toad.lib.mtjava.util.ThrowingSupplier;
import com.mr_toad.lib.mtjava.util.tri.Triplet;

import java.util.function.Function;

public class Quadruplets {

    public static<F, S, T, FR> Quadruplet<F, S, T, FR> convertTripletToQuadruplet(Triplet<F, S, T> triplet, FR v) {
        return Quadruplet.of(triplet.getFirst(), triplet.getSecond(), triplet.getThird(), v);
    }

    public static<F, S, T, FR, E extends Throwable> Quadruplet<F, S, T, FR> createThrowing(F f, S s, T t, FR fr, E exc) throws E {
        return ThrowingSupplier.setupThrowing(new Quadruplet<>(f, s, t, fr), exc);
    }

    public static<F, F2, S, S2, T, T2, FR, FR2, Q extends Quadruplet<F, S, T, FR>> Q mapAll(Q q, final Function<? super F, ? extends F2> ff, final Function<? super S, ? extends S2> sf, final Function<? super T, ? extends T2> tf, final Function<? super FR, ? extends FR2> frf) {
        q.mapFirst(ff);
        q.mapSecond(sf);
        q.mapThird(tf);
        q.mapFourth(frf);
        return q;
    }

}
