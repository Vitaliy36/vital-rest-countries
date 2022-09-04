package org.vital.service;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.vital.domain.Country;
import org.vital.domain.ResponseBody;
import org.vital.domain.ResponseCode;
import org.vital.domain.SearchParameter;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {
    private static final String BLANK_STRING = "";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryService countryService = new CountryService();

    Country country;

    @Before
    public void init() {
        country = new Country();
        country.setName("Germany");
        country.setAlpha2Code("de");
        country.setCapital("Berlin");
    }

    @Test
    public void whenCodeParameterCorrect_shouldReturnResponseSuccess() {
        String searchValue = "de";
        Mockito.when(restTemplate.getForEntity("https://restcountries.com/v2/alpha/" + searchValue, Country.class))
                .thenReturn(new ResponseEntity(country, HttpStatus.OK));

        ResponseBody actualResult = countryService.getCapitalByNameOrCode(searchValue, SearchParameter.ALPHA_CODE_2);

        assertEquals(ResponseCode.SUCCESS, actualResult.getResponseCode());
        assertEquals("Berlin", actualResult.getCapital());
    }

    @Test
    public void whenCodeLengthMoreThanTwo_shouldReturnResponseIncorrectLength() {
        String searchValue = "germany";
        Mockito.when(restTemplate.getForEntity("https://restcountries.com/v2/alpha/" + searchValue, Country.class))
          .thenReturn(new ResponseEntity(country, HttpStatus.OK));

        ResponseBody actualResult = countryService.getCapitalByNameOrCode(searchValue, SearchParameter.ALPHA_CODE_2);

        assertEquals(ResponseCode.INCORRECT_LENGTH, actualResult.getResponseCode());
        assertEquals(BLANK_STRING, actualResult.getCapital());
    }

    @Test
    public void whenCodeIncorrectSymbol_shouldReturnResponseIncorrectSymbol() {
        String searchValue = "g1";
        Mockito.when(restTemplate.getForEntity("https://restcountries.com/v2/alpha/" + searchValue, Country.class))
                .thenReturn(new ResponseEntity(country, HttpStatus.OK));

        ResponseBody actualResult = countryService.getCapitalByNameOrCode(searchValue, SearchParameter.ALPHA_CODE_2);

        assertEquals(ResponseCode.INCORRECT_SYMBOL, actualResult.getResponseCode());
        assertEquals(BLANK_STRING, actualResult.getCapital());
    }


    @Test
    public void whenNameParameterCorrect_shouldReturnResponseSuccess() {
        String searchValue = "germany";
        Country countries[] = { country };

        Mockito.when(restTemplate.getForEntity("https://restcountries.com/v2/name/" + searchValue, Country[].class))
                .thenReturn(new ResponseEntity(countries, HttpStatus.OK));

        ResponseBody actualResult = countryService.getCapitalByNameOrCode(searchValue, SearchParameter.COUNTRY_NAME);

        assertEquals(ResponseCode.SUCCESS, actualResult.getResponseCode());
        assertEquals("Berlin", actualResult.getCapital());
    }

    @Test
    public void whenNameIncorrectSymbol_shouldReturnIncorrectSymbol() {
        String searchValue = "germany1";
        Country countries[] = { country };

        Mockito.when(restTemplate.getForEntity("https://restcountries.com/v2/name/" + searchValue, Country[].class))
                .thenReturn(new ResponseEntity(countries, HttpStatus.OK));

        ResponseBody actualResult = countryService.getCapitalByNameOrCode(searchValue, SearchParameter.COUNTRY_NAME);

        assertEquals(ResponseCode.INCORRECT_SYMBOL, actualResult.getResponseCode());
        assertEquals(BLANK_STRING, actualResult.getCapital());
    }


    @Test
    public void whenNameIsNonExistingCountryCorrect_shouldReturnWrongCountryName() {
        String searchValue = "nonExistingCountry";
        Country countries[] = {};

        Mockito.when(restTemplate.getForEntity("https://restcountries.com/v2/name/" + searchValue, Country[].class))
                .thenReturn(new ResponseEntity(countries, HttpStatus.OK));

        ResponseBody actualResult = countryService.getCapitalByNameOrCode(searchValue, SearchParameter.COUNTRY_NAME);

        assertEquals(ResponseCode.WRONG_COUNTRY_NAME, actualResult.getResponseCode());
        assertEquals(BLANK_STRING, actualResult.getCapital());
    }

}