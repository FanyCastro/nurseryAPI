package com.estefaniacastro.nurseryAPI.infrastructure.config;

import com.estefaniacastro.nurseryAPI.application.service.ChildServiceImpl;
import com.estefaniacastro.nurseryAPI.domain.port.in.ChildService;
import com.estefaniacastro.nurseryAPI.domain.port.out.ChildRepository;
import com.estefaniacastro.nurseryAPI.infrastructure.adapters.ChildJdbcRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {

    @Bean
    public ChildRepository childRepository(JdbcTemplate jdbcTemplate) {
        return new ChildJdbcRepositoryAdapter(jdbcTemplate);
    }

    @Bean
    public ChildService childService(ChildRepository childRepository) {
        return new ChildServiceImpl(childRepository);
    }

}
