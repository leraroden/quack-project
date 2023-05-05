package de.ls5.wt2.rest;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping(path = "rest/simple")
public class SimpleREST {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String methodNamesDoNoMatter() {
        return "Hello World";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String reallyTheyDontMatter() {
        return "{\"hello\": \"world\"}";
    }

    @PostMapping(path = "withPath",
                 produces = MediaType.TEXT_PLAIN_VALUE)
    public String didIAlreadySayTheyDontMatter() {
        return "Goodbye world";
    }
}
