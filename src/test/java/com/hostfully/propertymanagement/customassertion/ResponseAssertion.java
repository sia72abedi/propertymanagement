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

    public ResponseAssertion hasMessage(String message){
        if (!actual.getMessage().equals(message)){
            failWithMessage("message Is Not Expected");
        }
        return this;
    }

    public ResponseAssertion isMessageContain(String partialMessage){
        if (!actual.getMessage().contains(partialMessage)){
            failWithMessage("message Is Not Expected.");
        }
        return this;
    }

    public ResponseAssertion isHrefContain(String partialHref){
        if (!actual.getMessage().contains(partialHref)){
            failWithMessage("href Is Not Expected.");
        }
        return this;
    }

    public ResponseAssertion isIdEqual(String id){
        if (!actual.getMessage().equals(id)){
            failWithMessage("id Is Not Expected.");
        }
        return this;
    }

    public ResponseAssertion isHrefNullOrEmpty(){
        if (actual.getHref() != null || !actual.getHref().isBlank()){
            failWithMessage("href Is Not Expected.");
        }
        return this;
    }

    public ResponseAssertion isIdNullOrEmpty(){
        if (actual.getId() != null || !actual.getId().isBlank()){
            failWithMessage("href Is Not Expected.");
        }
        return this;
    }
}
