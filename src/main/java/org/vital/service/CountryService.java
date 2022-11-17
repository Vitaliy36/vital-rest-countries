package org.vital.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vital.domain.Country;
import org.vital.domain.ResponseBody;
import org.vital.domain.ResponseCode;
import org.vital.domain.SearchParameter;

import java.util.Arrays;
import java.util.List;

@Service
public class CountryService {

    public static final String BLANK_STRING = "";
    private RestTemplate restTemplate = new RestTemplate();

    private static final String URL_COUNTRY_NAME = "https://restcountries.com/v2/name/";
    private static final String URL_ALPHA_CODE_2 = "https://restcountries.com/v2/alpha/";

    public ResponseBody getCapitalByNameOrCode(String searchValue, SearchParameter searchParameter) {
        ResponseBody responseBody = getCapitalByNameOrCodeAux(searchValue, searchParameter);

        System.out.println("Response code: " + responseBody.getResponseCode());
        System.out.println("Capital is: " + responseBody.getCapital());
        return responseBody;
    }

    private ResponseBody getCapitalByNameOrCodeAux(String searchValue, SearchParameter searchParameter) {
        String capital = BLANK_STRING;
        ResponseCode responseCode;
        try {
            if (searchParameter.equals(SearchParameter.COUNTRY_NAME)) {
                responseCode = validateName(searchValue);
                if (ResponseCode.SUCCESS.equals(responseCode)) {
                    final ResponseEntity<Country[]> response = restTemplate.getForEntity(URL_COUNTRY_NAME + searchValue, Country[].class);
                    List<Country> countries = Arrays.asList(response.getBody());

                    if (countries.size() != 1) {
                        return new ResponseBody(ResponseCode.WRONG_COUNTRY_NAME, BLANK_STRING);
                    }

                    capital = countries.get(0).getCapital();
                    System.out.println("The capital of " + countries.get(0).getName() + " is " + capital);
                } else {
                    return new ResponseBody(responseCode, BLANK_STRING);
                }

            } else if (searchParameter.equals(SearchParameter.ALPHA_CODE_2)) {
                responseCode = validateAlpha2Code(searchValue);
                if (ResponseCode.SUCCESS.equals(responseCode)) {
                    final ResponseEntity<Country> response = restTemplate.getForEntity(URL_ALPHA_CODE_2 + searchValue, Country.class);
                    Country country = response.getBody();
                    capital = country == null ? BLANK_STRING : country.getCapital();

                    System.out.println("The capital of " + country.getName() + " is " + capital);
                } else {
                    return new ResponseBody(responseCode, BLANK_STRING);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseBody(ResponseCode.CLIENT_ERROR, BLANK_STRING);
        }

        return new ResponseBody(ResponseCode.SUCCESS, capital);
    }

    private ResponseCode validateName(String countryName) {
        if (countryName.chars().allMatch(Character::isLetter)) {
            return ResponseCode.SUCCESS;
        } else {
            return ResponseCode.INCORRECT_SYMBOL;
        }
    }

    private ResponseCode validateAlpha2Code(String alpha2Code) {

        if (alpha2Code.length() != 2) {
            return ResponseCode.INCORRECT_LENGTH;
        } else if (!alpha2Code.chars().allMatch(Character::isLetter)) {
            return ResponseCode.INCORRECT_SYMBOL;
        } else {
            return ResponseCode.SUCCESS;
        }
    }

}
