package com.mr_toad.lib.mtjava.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@FunctionalInterface
public interface LazyCompletableFuture<J> extends Supplier<CompletableFuture<J>> {}
