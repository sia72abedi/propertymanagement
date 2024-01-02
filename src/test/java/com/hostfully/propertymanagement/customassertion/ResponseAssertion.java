package com.hostfully.propertymanagement.customassertion;

import com.hostfully.propertymanagement.dto.Response;
import org.assertj.core.api.AbstractAssert;

public class ResponseAssertion extends AbstractAssert<ResponseAssertion, Response> {
    public ResponseAssertion(Response response) {
        super(response, ResponseAssertion.class);
    }

    public static ResponseAssertion assertThat(Response actual) {
        return new ResponseAssertion(actual);
    }

    public ResponseAssertion isEquivalent(Response response){
        if (!response.getMessage().equals(actual.getMessage())){
            failWithMessage("message Is Not Expected");
        }else if (!response.getId().equals(actual.getId())){
            failWithMessage("id Is Not Expected");
        }else if (!response.getHref().equals(actual.getHref())){
            failWithMessage("href Is Not Expected");
        }
        return this;
    }
}