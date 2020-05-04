package com.company.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Loggers {
    public Logger log;
    public Loggers(String o)
    {
        this.log = LogManager.getLogger(o);
    }
}

