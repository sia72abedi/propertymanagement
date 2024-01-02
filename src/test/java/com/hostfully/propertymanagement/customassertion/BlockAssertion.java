package com.hostfully.propertymanagement.customassertion;

import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.entities.Block;
import org.assertj.core.api.AbstractAssert;

public class BlockAssertion extends AbstractAssert<BlockAssertion, Block> {
    public BlockAssertion(Block block) {
        super(block, BlockAssertion.class);
    }

    public static BlockAssertion assertThat(Block actual) {
        return new BlockAssertion(actual);
    }
    public BlockAssertion isEquivalent(BlockingDto blockingDto){
        if (!blockingDto.startDate().isEqual(actual.getStartDate())){
            failWithMessage("startDate Is Not Expected");
        }else if (!blockingDto.endDate().isEqual(actual.getEndDate())){
            failWithMessage("endDate Is Not Expected");
        }else if (!blockingDto.message().equals(actual.getMessage())){
            failWithMessage("message Is Not Expected");
        }else if (!blockingDto.reasonId().equals(actual.getReason().getId())){
            failWithMessage("reasonId Is Not Expected");
        }else if (!blockingDto.propertyId().equals(actual.getProperty().getId())){
            failWithMessage("propertyId Is Not Expected");
        }
        return this;
    }

    public BlockAssertion isEquivalent(Block block){
        if (!block.getStartDate().isEqual(actual.getStartDate())){
            failWithMessage("startDate Is Not Expected");
        }else if (!block.getEndDate().isEqual(actual.getEndDate())){
            failWithMessage("endDate Is Not Expected");
        }else if (!block.getMessage().equals(actual.getMessage())){
            failWithMessage("message Is Not Expected");
        }else if (!block.getReason().equals(actual.getReason())){
            failWithMessage("reason Is Not Expected");
        }else if (!block.getProperty().equals(actual.getProperty())){
            failWithMessage("property Is Not Expected");
        }
        return this;
    }
}
