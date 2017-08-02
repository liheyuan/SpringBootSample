/**
 * @(#)AbstractThriftClient.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author coder4
 */
public abstract class AbstractThriftClient<TCLIENT extends TServiceClient> implements ThriftClient<TCLIENT> {

    protected static final int THRIFT_CLIENT_DEFAULT_TIMEOUT = 5000;

    protected static final int THRIFT_CLIENT_DEFAULT_MAX_FRAME_SIZE = 1024 * 1024 * 16;

    protected String thriftServerHost;

    protected int thriftServerPort;

    private Class<?> thriftClass;

    private static final TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();

    private TServiceClientFactory<TCLIENT> clientFactory;

    // For async call
    private ExecutorService threadPool;

    public void init() {
        try {
            clientFactory = getThriftClientFactoryClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }

        if (!check()) {
            throw new RuntimeException("Client config failed check!");
        }

        threadPool = new ThreadPoolExecutor(
                10, 100, 0,
                TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>());
    }

    private boolean check() {
        if (thriftServerHost == null || thriftServerHost.isEmpty()) {
            return false;
        }
        if (thriftServerPort <= 0) {
            return false;
        }
        if (thriftClass == null) {
            return false;
        }
        return true;
    }

    @Override
    public <TRET> TRET call(ThriftCallFunc<TCLIENT, TRET> tcall) {

        // Step 1: get TTransport
        TTransport tpt = null;
        try {
            tpt = borrowTransport();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Step 2: get client & call
        try {
            TCLIENT tcli = createClient(tpt);
            TRET ret = tcall.call(tcli);
            returnTransport(tpt);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exec(ThriftExecFunc<TCLIENT> texec) {
        // Step 1: get TTransport
        TTransport tpt = null;
        try {
            tpt = borrowTransport();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Step 2: get client & exec
        try {
            TCLIENT tcli = createClient(tpt);
            texec.exec(tcli);
            returnTransport(tpt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <TRET> Future<TRET> asyncCall(ThriftCallFunc<TCLIENT, TRET> tcall) {
        return threadPool.submit(() -> this.call(tcall));
    }

    @Override
    public <TRET> Future<?> asyncExec(ThriftExecFunc<TCLIENT> texec) {
        return threadPool.submit(() -> this.exec(texec));
    }

    public String getThriftServerHost() {
        return thriftServerHost;
    }

    public void setThriftServerHost(String thriftServerHost) {
        this.thriftServerHost = thriftServerHost;
    }

    public int getThriftServerPort() {
        return thriftServerPort;
    }

    public void setThriftServerPort(int thriftServerPort) {
        this.thriftServerPort = thriftServerPort;
    }

    protected TCLIENT createClient(TTransport transport) throws Exception {
        // Step 1: get TProtocol
        TProtocol protocol = protocolFactory.getProtocol(transport);

        // Step 2: get client
        return clientFactory.getClient(protocol);
    }

    private Class<TServiceClientFactory<TCLIENT>> getThriftClientFactoryClass() {
        Class<TCLIENT> clientClazz = getThriftClientClass();
        if (clientClazz == null) {
            return null;
        }
        for (Class<?> clazz : clientClazz.getDeclaredClasses()) {
            if (TServiceClientFactory.class.isAssignableFrom(clazz)) {
                return (Class<TServiceClientFactory<TCLIENT>>) clazz;
            }
        }
        return null;
    }

    private Class<TCLIENT> getThriftClientClass() {
        for (Class<?> clazz : thriftClass.getDeclaredClasses()) {
            if (TServiceClient.class.isAssignableFrom(clazz)) {
                return (Class<TCLIENT>) clazz;
            }
        }
        return null;
    }

    protected abstract TTransport borrowTransport() throws Exception;

    protected abstract void returnTransport(TTransport transport);

    protected abstract void returnBrokenTransport(TTransport transport);

    public void setThriftClass(Class<?> thriftClass) {
        this.thriftClass = thriftClass;
    }
}