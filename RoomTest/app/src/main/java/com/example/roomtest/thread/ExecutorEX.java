package com.example.roomtest.thread;

import java.util.concurrent.Executor;

public interface ExecutorEX extends Executor {
    void executeDelay(Runnable command, long mSeconds);

}
