/**
 * @(#)MySampleJobStarter.java, Aug 21, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.my.sample.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author coder4
 */
@SpringBootApplication
public class MySampleJobStarter {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MySampleJobStarter.class);
        application.setWebEnvironment(false);
        application.run(args);
    }

}