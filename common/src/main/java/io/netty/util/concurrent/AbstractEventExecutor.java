/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.util.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * Abstract base class for {@link EventExecutor} implementations.
 */
public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor {

    @Override
    public EventExecutor next() {
        return this;
    }

    @Override
    public <V> Promise<V> newPromise() {
        return new DefaultPromise<V>(this);
    }

    @Override
    public <V> Future<V> newSucceededFuture(V result) {
        return new SucceededFuture<V>(this, result);
    }

    @Override
    public <V> Future<V> newFailedFuture(Throwable cause) {
        return new FailedFuture<V>(this, cause);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return (Future<?>) super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return (Future<T>) super.submit(task, result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return (Future<T>) super.submit(task);
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new PromiseTask<T>(this, runnable, value);
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new PromiseTask<T>(this, callable);
    }
}
