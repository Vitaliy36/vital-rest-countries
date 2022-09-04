package org.vital;

import org.vital.domain.SearchParameter;
import org.vital.service.CountryService;

import java.util.Scanner;

public class RestCountriesApplication {

    public static void main(String[] args) {
        CountryService countryService = new CountryService();

        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter country name: ");
            String countryName = scanner.next();

            countryService.getCapitalByNameOrCode(countryName, SearchParameter.COUNTRY_NAME);

            System.out.print("Enter country code: ");
            String alpha2Code = scanner.next();
            countryService.getCapitalByNameOrCode(alpha2Code, SearchParameter.ALPHA_CODE_2);

        }

    }
}







