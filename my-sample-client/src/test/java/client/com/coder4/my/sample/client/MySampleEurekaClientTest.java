/**
 * @(#)MySampleEurekaClientTest.java, Aug 10, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package client.com.coder4.my.sample.client;

import com.coder4.my.sample.client.MySampleEurekaClientBuilder;
import com.coder4.my.sample.thrift.MySampleThrift.Client;
import com.coder4.sbmvt.thrift.client.ThriftClient;

/**
 * @author coder4
 */
public class MySampleEurekaClientTest {

    public static void main(String[] args) throws InterruptedException {
        String serviceName = "my-sample-thrift-server";
        ThriftClient<Client> client = MySampleEurekaClientBuilder.buildClient(serviceName);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            try {
                String str = client.call(cli -> cli.sayHi());
            } catch (Exception e) {
                i--;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}