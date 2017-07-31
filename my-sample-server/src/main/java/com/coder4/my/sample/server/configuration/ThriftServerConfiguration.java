/**
 * @(#)RpcServerConfiguration.java, Jul 31, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.server.configuration;

import com.coder4.my.sample.thrift.MySampleThrift;
import com.coder4.my.sample.server.thrift.ThriftServerHandler;
import org.apache.thrift.TProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author coder4
 */
@Configuration
@ConditionalOnProperty(name = "thriftServer.enabled", matchIfMissing = true)
public class ThriftServerConfiguration {

    @Bean(name = "thriftProcessor")
    @SuppressWarnings("unchecked")
    public TProcessor processor(ThriftServerHandler handler) {
        return new MySampleThrift.Processor(handler);
    }

}