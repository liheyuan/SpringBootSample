/**
 * @(#)MySampleClientConfiguration.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client.configuration;

import com.coder4.my.sample.client.MySampleEasyClientBuilder;
import com.coder4.my.sample.client.MySampleEurekaClientBuilder;
import com.coder4.my.sample.client.ThriftClient;
import com.coder4.my.sample.thrift.MySampleThrift;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author coder4
 */
@Configuration
public class MySampleClientConfiguration {

    @Bean(name = "mySampleThriftClient")
    @ConditionalOnMissingBean(name = "mySampleThriftClient")
    @ConditionalOnProperty(name = {"mySampleThriftServer.host", "mySampleThriftServer.port"})
    public ThriftClient<MySampleThrift.Client> easyClient(
            @Value("${mySampleThriftServer.host}") String host,
            @Value("${mySampleThriftServer.port}") int port
    ) {
        // TODO LOG
        return MySampleEasyClientBuilder.buildClient(host, port);
    }

    @Bean(name = "mySampleThriftClient")
    @ConditionalOnMissingBean(name = "mySampleThriftClient")
    public ThriftClient<MySampleThrift.Client> eurekaClient() {
        // TODO LOG
        return MySampleEurekaClientBuilder.buildClient("my-sample-thrift-server");
    }

}