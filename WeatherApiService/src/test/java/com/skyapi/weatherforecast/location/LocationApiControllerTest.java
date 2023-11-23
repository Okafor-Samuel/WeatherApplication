package com.skyapi.weatherforecast.location;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(LocationApiController.class)

public class LocationApiControllerTest {
    private static final String END_POINT_PATH = "/v1/locations";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean LocationService locationService;

    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        Location location = new Location();
        String bodyContent = objectMapper.writeValueAsString(location);
        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testAddShouldReturn201Created() throws Exception {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Mockito.when(locationService.add(location)).thenReturn(location);
        String bodyContent = objectMapper.writeValueAsString(location);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code",is("NYC_USA")))
                .andExpect(header().string("Location",  "/v1/locations/NYC_USA"))
                .andDo(print());
    }

    @Test
    public void testListShouldReturn204NoContent() throws Exception {
        Mockito.when(locationService.list()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testValidateRequestBodyLocationCode() throws Exception {
        Location location = new Location();
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        String bodyContent = objectMapper.writeValueAsString(location);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
             //   .andExpect(jsonPath("$.code",is("NYC_USA")))
                .andDo(print());
    }

    @Test
    public void testListShouldReturn200Ok() throws Exception {
        Location location1 = new Location();
        location1.setCode("NYC_USA");
        location1.setCityName("New York City");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountryName("United States of America");
        location1.setEnabled(true);

        Location location2 = new Location();
        location2.setCode("NIG");
        location2.setCityName("LAGOS");
        location2.setRegionName("LAGOS");
        location2.setCountryCode("NG");
        location2.setCountryName("NIGERIA");
        location2.setEnabled(true);

        Mockito.when(locationService.list()).thenReturn(List.of(location1,location2));

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].code",is("NYC_USA")))
                .andExpect(jsonPath("$[0].city_name",is("New York City")))
                .andDo(print());
    }

    @Test
    public void testGetShouldReturn405MethodNotAllowed() throws Exception {
         String requestURI = END_POINT_PATH +"/ABCD";

         mockMvc.perform(post(requestURI))
                 .andExpect(status().isMethodNotAllowed())
                 .andDo(print());
    }

    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        String requestURI = END_POINT_PATH +"/ABCD";

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetShouldReturn200Ok() throws Exception {
        Location location = new Location();
        location.setCode("USA");
        location.setCityName("New Delhi");
        location.setRegionName("Delhi");
        location.setCountryCode("IN");
        location.setCountryName("India");
        location.setEnabled(true);

        String code = "USA";
        String requestURI = END_POINT_PATH + "/" +code;

        Mockito.when(locationService.get(code)).thenReturn(location);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code",is(code)))
                .andExpect(jsonPath("$.city_name", is("New Delhi")))
                .andDo(print());
    }

    @Test
    public  void testUpdateShouldReturn404NotFound() throws LocationNotFoundException, Exception {
        Location location = new Location();
        location.setCode("ABCDEF");
        location.setCityName("New Delhi");
        location.setRegionName("Delhi");
        location.setCountryCode("IN");
        location.setCountryName("India");
        location.setEnabled(true);

        Mockito.when(locationService.update(location)).thenThrow(new LocationNotFoundException("No location found"));
        String bodyContent = objectMapper.writeValueAsString(location);
        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public  void testUpdateShouldReturn400BadRequest() throws LocationNotFoundException, Exception {
        Location location = new Location();
        location.setCityName("New Delhi");
        location.setRegionName("Delhi");
        location.setCountryCode("IN");
        location.setCountryName("India");
        location.setEnabled(true);

        String bodyContent = objectMapper.writeValueAsString(location);
        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn200OK() throws Exception, LocationNotFoundException {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Mockito.when(locationService.update(location)).thenReturn(location);
        String bodyContent = objectMapper.writeValueAsString(location);

        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isOk() )
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code",is("NYC_USA")))
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception, LocationNotFoundException {
        String code = "USA";
        String requestURI = END_POINT_PATH + "/" +code;

        Mockito.doThrow(LocationNotFoundException.class).when(locationService).delete(code);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    public void testDeleteShouldReturn204NoContent() throws LocationNotFoundException, Exception {
        String code = "USA";
        String requestURI = END_POINT_PATH + "/" +code;

        Mockito.doNothing().when(locationService).delete(code);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
