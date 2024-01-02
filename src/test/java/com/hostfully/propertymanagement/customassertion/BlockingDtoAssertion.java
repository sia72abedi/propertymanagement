package com.hostfully.propertymanagement.customassertion;

import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.entities.Block;
import org.assertj.core.api.AbstractAssert;

public class BlockingDtoAssertion extends AbstractAssert<BlockingDtoAssertion, BlockingDto> {
    public BlockingDtoAssertion(BlockingDto blockingDto) {
        super(blockingDto, BlockingDtoAssertion.class);
    }

    public static BlockingDtoAssertion assertThat(BlockingDto actual) {
        return new BlockingDtoAssertion(actual);
    }
    public BlockingDtoAssertion isEquivalent(BlockingDto blockingDto){
        if (!blockingDto.startDate().isEqual(actual.startDate())){
            failWithMessage("startDate Is Not Expected");
        }else if (!blockingDto.endDate().isEqual(actual.endDate())){
            failWithMessage("endDate Is Not Expected");
        }else if (!blockingDto.message().equals(actual.message())){
            failWithMessage("message Is Not Expected");
        }else if (!blockingDto.reasonId().equals(actual.reasonId())){
            failWithMessage("reasonId Is Not Expected");
        }else if (!blockingDto.propertyId().equals(actual.propertyId())){
            failWithMessage("propertyId Is Not Expected");
        }
        return this;
    }

    public BlockingDtoAssertion isEquivalent(Block block){
        if (!block.getStartDate().isEqual(actual.startDate())){
            failWithMessage("startDate Is Not Expected");
        }else if (!block.getEndDate().isEqual(actual.endDate())){
            failWithMessage("endDate Is Not Expected");
        }else if (!block.getMessage().equals(actual.message())){
            failWithMessage("message Is Not Expected");
        }else if (!block.getReason().getId().equals(actual.reasonId())){
            failWithMessage("reasonId Is Not Expected");
        }else if (!block.getProperty().getId().equals(actual.propertyId())){
            failWithMessage("propertyId Is Not Expected");
        }
        return this;
    }
}
