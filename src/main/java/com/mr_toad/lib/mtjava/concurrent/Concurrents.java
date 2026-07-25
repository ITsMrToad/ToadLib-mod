package com.mr_toad.lib.mtjava.concurrent;

import com.mr_toad.lib.core.ToadLib;
import net.minecraft.ReportType;
import net.minecraft.ReportedException;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.Util;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Concurrents {

    public static void uncaught(Thread pool, Throwable tr) {
        Util.pauseInIde(tr);
        if (tr instanceof CompletionException) {
            tr = tr.getCause();
        }

        if (tr instanceof ReportedException cre) {
            Bootstrap.realStdoutPrintln(cre.getReport().getFriendlyReport(ReportType.CRASH));
            System.exit(-1);
        }

        ToadLib.LOGGER.error("Caught exception in thread '{}'", pool, tr);
    }

    public static void shutdownService(ExecutorService service) {
        shutdownService(service, 4L);
    }

    public static void shutdownService(ExecutorService service, long secondsAwait) {
        service.shutdown();
        boolean flag;
        try {
            flag = service.awaitTermination(secondsAwait, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            flag = false;
        }
        if (!flag) {
            service.shutdownNow();
        }
    }
}
