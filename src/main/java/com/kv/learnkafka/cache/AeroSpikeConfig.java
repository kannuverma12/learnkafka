package com.kv.learnkafka.cache;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.kv.learnkafka.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
public class AeroSpikeConfig {

    private static final Logger logger = LoggerFactory.getLogger(AeroSpikeConfig.class);

    @Value("${AEROSPIKE_HOSTS}")
    private String AEROSPIKE_HOSTS;

    @Value("${AEROSPIKE_NAMESPACE}")
    private String AEROSPIKE_NAMESPACE;


    public @Bean(destroyMethod = "close") AerospikeClient aerospikeClient() {
        List<Host> HostServers= new ArrayList<>();

        for(String hostAndPort : AEROSPIKE_HOSTS.split(",")){
            String[] HOST_PORT = hostAndPort.split( Constants.COLON_SEPARATOR);
            HostServers.add(new Host(HOST_PORT[0], Integer.valueOf(HOST_PORT[1])));
        }

        logger.info("servers={}", HostServers);
        Host[] hosts = HostServers.toArray(new Host[0]);
        ClientPolicy policy = new ClientPolicy();
        return new AerospikeClient(policy, hosts);
    }
}
