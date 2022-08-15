package com.mediscreen.clientui.proxies;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class Mocks {
    public static void setupMockGetAllResponse(WireMockServer mockService, String url, String response) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        Mocks.class.getClassLoader().getResourceAsStream(response),
                                        defaultCharset()))));
    }

    public static void setupMockGetResponse(WireMockServer mockService, String url, String response) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        Mocks.class.getClassLoader().getResourceAsStream(response),
                                        defaultCharset()))));
    }

    public static void setupMockAddResponse(WireMockServer mockService, String url, String response) throws IOException {
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        Mocks.class.getClassLoader().getResourceAsStream(response),
                                        defaultCharset()))));
    }

    public static void setupMockUpdateResponse(WireMockServer mockService, String url, String response) throws IOException {
        mockService.stubFor(WireMock.put(WireMock.urlEqualTo(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        Mocks.class.getClassLoader().getResourceAsStream(response),
                                        defaultCharset()))));
    }

    public static void setupMockDeleteResponse(WireMockServer mockService, String url) throws IOException {
        mockService.stubFor(WireMock.delete(WireMock.urlEqualTo(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));
    }
}
