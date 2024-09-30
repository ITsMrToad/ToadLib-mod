package com.mr_toad.lib.mtjava.io;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.server.Bootstrap;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MTIO {

    public static final AtomicInteger WORKERS = new AtomicInteger(1);
    public static final ExecutorService IO = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("MTIO-Worker-" + WORKERS.getAndIncrement()).setUncaughtExceptionHandler(MTIO::uncaught).build());

    public static void shutdownUncommon() {
        Util.shutdownExecutors();
        shutdownService(IO);
    }

    public static void uncaught(Thread pool, Throwable tr) {
        Util.pauseInIde(tr);
        if (tr instanceof CompletionException) {
            tr = tr.getCause();
        }

        if (tr instanceof ReportedException) {
            Bootstrap.realStdoutPrintln(((ReportedException)tr).getReport().getFriendlyReport());
            System.exit(-1);
        }

        ToadLib.LOGGER.error("Caught exception in thread '{}'", pool, tr);
    }

    public static void shutdownService(ExecutorService service) {
        service.shutdown();

        boolean flag;
        try {
            flag = service.awaitTermination(3L, TimeUnit.SECONDS);
        } catch (InterruptedException interruptedexception) {
            flag = false;
        }

        if (!flag) {
            service.shutdownNow();
        }
    }

}
