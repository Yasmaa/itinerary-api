/*
 * The MIT License (MIT)
 * Copyright (c) 2018 Michele Masili
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.travellingfreak.itinerary.api.core.currencies.openexchange;

import com.travellingfreak.itinerary.api.core.caches.Cached;
import com.travellingfreak.itinerary.api.core.currencies.ServiceNotAvailableException;
import com.travellingfreak.itinerary.api.core.currencies.USDExchangeRateProvider;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User: AustPC
 * Date: 3/3/2018
 * Time: 8:27 PM
 *
 * @author AustPC
 */
@Service
public class OpenExchangeRatesProvider implements USDExchangeRateProvider {

  private static final String API_KEY = "6598434cd222447fb0f35325400af149";
  private static final String ENDPOINT = "http://openexchangerates.org";
  private final OpenExchangeRatesApi api;

  public OpenExchangeRatesProvider(final OpenExchangeRatesApi api) {
    this.api = api;
  }

  public OpenExchangeRatesProvider() {
    final Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(ENDPOINT)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
    this.api = retrofit.create(OpenExchangeRatesApi.class);
  }

  @Override
  @Cached(name = "rates")
  public Map<CurrencyUnit, Double> provideRates() {
    final Map<CurrencyUnit, Double> conversionRates = new HashMap<>();
    try {
      final Response<OpenExchangeResponse> execute = api.fetchLatest(API_KEY).execute();
      final OpenExchangeResponse response = execute.body();
      if (response != null) {
        final Map<String, Double> rates = response.getRates();
        if (rates != null) {
          rates.forEach((currencyCode, conversionRate) -> {
            try {
              final CurrencyUnit currency = Monetary.getCurrency(currencyCode);
              conversionRates.put(currency, conversionRate);
            } catch (final UnknownCurrencyException ignored) {
              // ignoring unknown currencies
            }
          });
        }
      }
    } catch (final IOException e) {
      throw new ServiceNotAvailableException("Error while retrieving data from underlying API", e);
    }
    return conversionRates;
  }
}
