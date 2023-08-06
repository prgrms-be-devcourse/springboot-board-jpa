package com.example.springbootboardjpa.RestDocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
public class AbstractRestDocesTest {
    protected RestDocumentationResultHandler resultHandler;
    protected MockMvc mockMvc;

    protected AbstractRestDocesTest(RestDocumentationResultHandler resultHandler, MockMvc mockMvc) {
        this.resultHandler = resultHandler;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .alwaysDo(print())
                .alwaysDo(resultHandler)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }
}
