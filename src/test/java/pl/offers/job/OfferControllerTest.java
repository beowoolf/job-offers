package pl.offers.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = MockMvcConfig.class)
public class OfferControllerTest implements SampleOfferDto {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void should_return_list_with_offer() throws Exception {
        //given
        List<OfferDto> offers = Lists.newArrayList(firstOfferDtoWithId(), secondOfferDtoWithId());
        String exceptedResponse = objectMapper.writeValueAsString(offers);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/offers")).andExpect(status().isOk()).andReturn();
        String receivedResponse = mvcResult.getResponse().getContentAsString();
        //then
        assertThat(receivedResponse).isEqualTo(exceptedResponse);
    }

    @Test
    public void should_return_status_ok_when_get_for_offer_with_given_id() throws Exception {
        //given
        OfferDto secondOffer = secondOfferDtoWithId();
        String expectedResponse = objectMapper.writeValueAsString(secondOffer);
        int offerId = 2;
        //when
        MvcResult mvcResult = mockMvc.perform(get("/offers/" + offerId)).andExpect(status().isOk()).andReturn();
        String receivedResponse = mvcResult.getResponse().getContentAsString();
        //then
        assertThat(receivedResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_status_not_found_when_there_is_not_offer_with_given_id_while_get_method() throws Exception {
        //given
        int offerId = 500;
        OfferErrorResponse offerErrorResponse = OfferErrorResponse.builder()
                .message("There is no offer with id: " + offerId)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
        String errorResponse = objectMapper.writeValueAsString(offerErrorResponse);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/offers/" + offerId)).andExpect(status().isNotFound()).andReturn();
        String receivedResponse = mvcResult.getResponse().getContentAsString();
        //then
        assertThat(receivedResponse).isEqualTo(errorResponse);
    }

    @Test
    public void should_return_status_bad_request_when_given_id_is_not_a_number() throws Exception {
        //given
        String offerId = "s";
        OfferErrorResponse offerErrorResponse = OfferErrorResponse.builder()
                .message("Offer id isn't a number.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
        String badRequestResponse = objectMapper.writeValueAsString(offerErrorResponse);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/offers/" + offerId)).andExpect(status().isBadRequest()).andReturn();
        String receivedResponse = mvcResult.getResponse().getContentAsString();
        //then
        assertThat(receivedResponse).isEqualTo(badRequestResponse);
    }

    @Test
    public void should_return_status_ok_when_offer_is_updated() throws Exception {
        //given
        String endpoint = "/offers";
        String updatedOffer = objectMapper.writeValueAsString(firstOfferDtoWithId());
        //when
        MvcResult mvcResult = mockMvc.perform(postMethodWithWithObjectAsJSON(endpoint, updatedOffer))
                .andExpect(status().isOk()).andReturn();
        String receivedResponse = mvcResult.getResponse().getContentAsString();
        //then
        assertThat(receivedResponse).isEqualTo(updatedOffer);
    }

    @Test
    public void should_return_status_created_when_new_offer_is_created() throws Exception {
        //given
        String endpoint = "/offers";
        String createdOffer = objectMapper.writeValueAsString(firstOfferDtoWithoutId());
        //when
        MvcResult mvcResult = mockMvc.perform(postMethodWithWithObjectAsJSON(endpoint, createdOffer))
                .andExpect(status().isCreated()).andReturn();
        String receivedResponse = mvcResult.getResponse().getContentAsString();
        //then
        assertThat(receivedResponse).isEqualTo(createdOffer);
    }

    @Test
    public void should_return_status_ok_when_offer_is_successful_deleted() throws Exception {
        //given
        int offerId = 1;
        //when
        MvcResult mvcResult = mockMvc.perform(delete("/offers/" + offerId)).andExpect(status().isOk()).andReturn();
        int responseStatus = mvcResult.getResponse().getStatus();
        //then
        assertThat(responseStatus).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void should_return_status_not_modified_when_there_is_no_offer_with_given_id_while_delete_method() throws Exception {
        //given
        int offerId = 500;
        //when
        MvcResult mvcResult = mockMvc.perform(delete("/offers/" + offerId)).andExpect(status().isNotModified()).andReturn();
        int responseStatus = mvcResult.getResponse().getStatus();
        //then
        assertThat(responseStatus).isEqualTo(HttpStatus.NOT_MODIFIED.value());
    }

    private MockHttpServletRequestBuilder postMethodWithWithObjectAsJSON(String endpoint, String objectFromJSON) {
        return post(endpoint)
                .contentType(MediaType.APPLICATION_JSON).content(objectFromJSON);
    }

}
