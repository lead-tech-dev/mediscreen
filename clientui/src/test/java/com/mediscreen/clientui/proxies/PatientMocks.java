package com.mediscreen.clientui.proxies;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class PatientMocks {
    public static void setupMockGetAllPatientsResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/patient/getAll/1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        PatientMocks.class.getClassLoader().getResourceAsStream("payload/get-patients-response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockGetPatientResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/patient/get/1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        PatientMocks.class.getClassLoader().getResourceAsStream("payload/get-patient-response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockAddPatientResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/patient/add"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        PatientMocks.class.getClassLoader().getResourceAsStream("payload/add-patient-response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockUpdatePatientResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.put(WireMock.urlEqualTo("/patient/update"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        PatientMocks.class.getClassLoader().getResourceAsStream("payload/update-patient-response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockDeletePatientResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.delete(WireMock.urlEqualTo("/patient/delete/1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));
    }
}
