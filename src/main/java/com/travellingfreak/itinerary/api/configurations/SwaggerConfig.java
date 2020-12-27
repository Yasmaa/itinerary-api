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

package com.travellingfreak.itinerary.api.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        .paths(PathSelectors.any())
        .build()
        .securitySchemes(newArrayList(apiKey()))
        .enableUrlTemplating(true)
        .apiInfo(apiInfo())
        ;
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Travel Api")
        .description(
            "A must-have app for those interested in travel. "
            + "The app provides users with various features from choosing the correct destination to making all the bookings and to easily organizing the trip. "
            + "The app provides solutions for every possible problem a traveller might face during the course of his or her entire journey. "
            + "While travelling from one place to another, there are a lot of factors to be considered to make the trip a memorable one. "
            + "Our platform helps the traveller with anything and everything that he or she might need, from the moment he or she plans the journey till the time he or she is back home happy and content. "
            + "The platform includes a wide variety of options - from selection of Mode of Transport, to finding out about the destination city."
        )
        .license("Attribution-NonCommercial-NoDerivs 3.0 Unported (CC BY-NC-ND 3.0)")
        .licenseUrl("https://creativecommons.org/licenses/by-nc-nd/3.0/")
        .version("3.0.0")
        .build();

  }

  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .deepLinking(true)
        .displayOperationId(false)
        .defaultModelsExpandDepth(1)
        .defaultModelExpandDepth(1)
        .defaultModelRendering(ModelRendering.EXAMPLE)
        .displayRequestDuration(false)
        .docExpansion(DocExpansion.NONE)
        .filter(false)
        .maxDisplayedTags(null)
        .operationsSorter(OperationsSorter.METHOD)
        .showExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
        .validatorUrl(null)
        .build();
  }


  private ApiKey apiKey() {
    return new ApiKey("Bearer access_token", "Authorization", "header");
  }

}
