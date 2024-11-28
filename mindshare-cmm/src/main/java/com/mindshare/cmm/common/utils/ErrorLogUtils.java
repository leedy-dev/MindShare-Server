package com.mindshare.cmm.common.utils;

import com.mindshare.core.common.utils.CommonObjectUtils;
import org.slf4j.Logger;

public class ErrorLogUtils {

    private ErrorLogUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void printError(Logger log, Exception e) {
        printError(log, e, null);
    }

    public static void printError(Logger log, Exception e, String message) {
        log.error(e.toString() + (message != null ? " : " + message : ""));
        if(CommonObjectUtils.isNotEmpty(e.getStackTrace())) {
            int stackTraceLength = e.getStackTrace().length;
            for (int i = 0; i < (Math.min(stackTraceLength, 3)); i++) {
                log.error(String.valueOf(e.getStackTrace()[i]));
            }
        }
    }

}
