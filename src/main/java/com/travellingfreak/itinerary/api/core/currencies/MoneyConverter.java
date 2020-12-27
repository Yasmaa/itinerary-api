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

package com.travellingfreak.itinerary.api.core.currencies;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

/**
 * User: AustPC Date: 3/3/2018 Time: 5:37 PM
 *
 * @author AustPC
 */
@Service
public class MoneyConverter {

  private final USDExchangeRateProvider provider;

  public MoneyConverter(final USDExchangeRateProvider provider) {
    this.provider = provider;
  }

  private static boolean isCurrencyUSD(final CurrencyUnit from) {
    return "USD".equals(from.getCurrencyCode());
  }

  public MonetaryAmount convert(final MonetaryAmount money, final CurrencyUnit targetCurrency) {
    final CurrencyUnit currency = money.getCurrency();
    if (currency.equals(targetCurrency)) {
      return money;
    }
    final MonetaryAmount multiply = money.multiply(getExchangeRate(currency, targetCurrency));
    return Money.of(multiply.getNumber(), targetCurrency);
  }

  private double getExchangeRate(final CurrencyUnit from, final CurrencyUnit to) {
    final Map<CurrencyUnit, Double> rates = provider.provideRates();

    if (isCurrencyUSD(from)) {
      return rates.get(to);
    } else if (isCurrencyUSD(to)) {
      return 1.0 / rates.get(from);
    } else {
      return 1.0 / rates.get(from) * rates.get(to);
    }
  }

  public MonetaryAmount safeConvert(final double basePrice, final String currency, final String localCurrency) {
    if (currency != null) {
      if (localCurrency != null) {
        return convert(Money.of(basePrice, currency), Monetary.getCurrency(localCurrency));
      } else {
        return Money.of(0.0, currency);
      }
    } else {
      return Money.of(0.0, "EUR");
    }
  }

  public MonetaryAmount safeConvert(final MonetaryAmount money, final String localCurrency) {
    if (localCurrency != null) {
      return convert(money, Monetary.getCurrency(localCurrency));
    } else {
      return Money.of(0.0, money.getCurrency());
    }
  }

}
