package com.example.roomtest.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//created by wenhuan
public class AppExecutors {

    private static final int POOL_NUM = 3;//核心线程池的个数

    private static class Holder {
        static final AppExecutors instance = new AppExecutors();
    }

    public static AppExecutors getInstance() {
        return Holder.instance;
    }

    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
    }

    private AppExecutors() {
        this(Executors.newFixedThreadPool(POOL_NUM), Executors.newFixedThreadPool(POOL_NUM),
                new MainThreadExecutor());
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements ExecutorEX {
        private static final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }

        @Override
        public void executeDelay(Runnable command, long mSeconds) {
            handler.postDelayed(command, mSeconds);
        }
    }
}
