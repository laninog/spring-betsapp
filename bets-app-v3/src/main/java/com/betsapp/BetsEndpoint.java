package com.betsapp;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Endpoint(id = "authors")
@Component
public class BetsEndpoint {

    private Map<String, Author> authors = new ConcurrentHashMap<>();

    public BetsEndpoint() {
        authors.put("developer", new Author("Pepe"));
    }

    @ReadOperation
    public Map<String, Author> authors() {
        return authors;
    }

    private class Author {
        private String name;

        public Author(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

}
