package com.sandbox.device.api.controller;

import com.sandbox.device.api.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @MockitoBean
    private DeviceService deviceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void when_createDevice_and_requestIsInvalid_then_returnBadRequestWithValidationErrors() throws Exception {
        // Invalid request with empty fields
        String invalidRequestJson = "{\"name\":\"\",\"brand\":\"\"}";

        mockMvc.perform(post("/api/devices")
                        .contentType("application/json")
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assertThat(responseContent).contains("Name is mandatory field. Every device needs a name");
                    assertThat(responseContent).contains("Brand is a mandatory field. Every device needs a brand");
                });
    }


}
