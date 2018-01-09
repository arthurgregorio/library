/*
 * Copyright 2018 Arthur Gregorio, AG.Software.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.eti.arthurgregorio.library.infrastructure.jersey;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * The mapper configuration for REST operations with JSON input/output
 *
 * @author Arthur Gregorio
 *
 * @since 1.0.0
 * @version 1.0.0, 08/01/2018
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor...
     */
    public ObjectMapperProvider() {
    	this.objectMapper
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setSerializationInclusion(Include.NON_NULL)
                .registerModules(
                        new Jdk8Module(),
                        new JavaTimeModule(),
                        new Hibernate5Module()
                );
    }
    
    /**
     * @see ContextResolver#getContext(java.lang.Class) 
     * 
     * @param type
     * @return 
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.objectMapper;
    }
}
