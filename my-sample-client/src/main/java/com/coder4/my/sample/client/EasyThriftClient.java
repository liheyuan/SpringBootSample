/**
 * @(#)EasyThriftClient.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author coder4
 */
public class EasyThriftClient<TCLIENT extends TServiceClient> extends AbstractThriftClient<TCLIENT> {

    private static final int EASY_THRIFT_BUFFER_SIZE = 1024 * 16;

    @Override
    protected TTransport borrowTransport() throws Exception {
        TSocket socket = new TSocket(thriftServerHost, thriftServerPort, THRIFT_CLIENT_DEFAULT_TIMEOUT);

        TTransport transport = new TFramedTransport(
                socket, THRIFT_CLIENT_DEFAULT_MAX_FRAME_SIZE);

        transport.open();

        return transport;
    }

    @Override
    protected void returnTransport(TTransport transport) {
        if (transport != null && transport.isOpen()) {
            transport.close();
        }
    }

    @Override
    protected void returnBrokenTransport(TTransport transport) {
        if (transport != null && transport.isOpen()) {
            transport.close();
        }
    }

}