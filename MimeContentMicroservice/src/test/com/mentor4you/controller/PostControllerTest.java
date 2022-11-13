package com.mentor4you.controller;

import com.mentor4you.converter.ContentConverter;
import com.mentor4you.domain.PostDTO;
import com.mentor4you.service.AuthService;
import com.mentor4you.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;

    @MockBean
    private AuthService authService;

    @MockBean
    private ContentConverter<PostDTO> csvPostConverter;

    @Autowired
    private MockMvc mockMvc;

    private static final String TEXT_CSV_VALUE = "text/csv";

    @BeforeEach
    public void setupStubs() {

    }

    @Test
    public void shouldReturn401IfAuthorizationHeaderIsNotPresent() throws Exception {
        mockMvc
                .perform(get("/api/posts")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401IfTokenIsInvalid() throws Exception {
        when(authService.isValidToken(anyString())).thenReturn(false);

        mockMvc
                .perform(get("/api/posts")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "some_token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnJsonIfAcceptHeaderIsJson() throws Exception {
        when(authService.isValidToken(anyString())).thenReturn(true);

        mockMvc
                .perform(get("/api/posts")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "some_token"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void shouldReturnCsvIfAcceptHeaderIsCsv() throws Exception {
        when(authService.isValidToken(anyString())).thenReturn(true);

        mockMvc
                .perform(get("/api/posts")
                        .header(HttpHeaders.ACCEPT, TEXT_CSV_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "some_token"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, TEXT_CSV_VALUE));
    }

    @Test
    public void shouldReturnHtmlIfAcceptHeaderIsHtml() throws Exception {
        when(authService.isValidToken(anyString())).thenReturn(true);

        mockMvc
                .perform(get("/api/posts")
                        .header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "some_token"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE));
    }

    @Test
    public void shouldReturnJsonIfAcceptHeaderIsNotSpecified() throws Exception {
        when(authService.isValidToken(anyString())).thenReturn(true);

        mockMvc
                .perform(get("/api/posts")
                        .header(HttpHeaders.AUTHORIZATION, "some_token"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }
}
