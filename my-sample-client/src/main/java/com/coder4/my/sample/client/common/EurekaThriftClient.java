/**
 * @(#)EurekaThriftClient.java, Aug 10, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.client.common;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.EurekaEvent;
import com.netflix.discovery.EurekaEventListener;
import com.netflix.discovery.shared.Application;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @author coder4
 */
public class EurekaThriftClient<TCLIENT extends TServiceClient>
        extends AbstractThriftClient<TCLIENT> {

    private ApplicationInfoManager applicationInfoManager;

    private EurekaClient eurekaClient;

    private String thriftServiceName;

    private Random randomGenerator;

    @Override
    public void init() {
        super.init();
        // init eurekaClient by hand
        initializeApplicationInfoManager();
        initializeEurekaClient();
        eurekaClient.registerEventListener(new EurekaEventListener() {
            @Override
            public void onEvent(EurekaEvent event) {
                System.out.println("EurekaEvent Cache Updated");
                System.out.println(event);
            }
        });
        // init random gen
        randomGenerator = new Random();
    }

    @Override
    protected TTransport borrowTransport() throws Exception {
        // Get Application on eureka
        Application application = eurekaClient.getApplication(thriftServiceName);
        if (application == null) {
            throw new RuntimeException("Application " + thriftServiceName +" not found on eureka.");
        }

        // Get Instances on eureka
        List<InstanceInfo> instances = application.getInstances();
        if (CollectionUtils.isEmpty(instances)) {
            throw new RuntimeException("Application " + thriftServiceName + " instances empty");
        }

        // Get random instance
        int idx = randomGenerator.nextInt(instances.size());
        InstanceInfo instanceSelected = instances.get(idx);

        // Get ip and port
        String ip = instanceSelected.getIPAddr();
        int port = instanceSelected.getPort();

        // get transport
        TSocket socket = new TSocket(ip, port, THRIFT_CLIENT_DEFAULT_TIMEOUT);

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

    private synchronized void initializeApplicationInfoManager() {
        if (applicationInfoManager == null) {
            EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }
    }

    private synchronized void initializeEurekaClient() {
        if (eurekaClient == null) {
            EurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }
    }

    public String getThriftServiceName() {
        return thriftServiceName;
    }

    public void setThriftServiceName(String thriftServiceName) {
        this.thriftServiceName = thriftServiceName;
    }
}