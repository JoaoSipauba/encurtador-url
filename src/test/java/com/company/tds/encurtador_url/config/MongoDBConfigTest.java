package com.company.tds.encurtador_url.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MongoDBConfigTest {

    @Mock
    private MappingMongoConverter mappingMongoConverter;

    @Mock
    private MongoDatabaseFactory mongoDatabaseFactory;

    private MongoDBConfig mongoDBConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mongoDBConfig = new MongoDBConfig(mappingMongoConverter);
    }

    @Test
    void configurarTypeMapperComValorNulo() {
        mongoDBConfig.afterPropertiesSet();
        verify(mappingMongoConverter).setTypeMapper(any());
    }

    @Test
    void criarTransactionManagerComDatabaseFactory() {
        assertNotNull(mongoDBConfig.transactionManager(mongoDatabaseFactory));
    }
}