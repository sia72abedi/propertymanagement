package com.hostfully.propertymanagement.customassertion;

import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.entities.Block;
import org.assertj.core.api.AbstractAssert;

public class BlockedGetDtoAssertion extends AbstractAssert<BlockedGetDtoAssertion, BlockedGetDto> {
    public BlockedGetDtoAssertion(BlockedGetDto blockedGetDto) {
        super(blockedGetDto, BlockedGetDtoAssertion.class);
    }

    public static BlockedGetDtoAssertion assertThat(BlockedGetDto actual) {
        return new BlockedGetDtoAssertion(actual);
    }
    public BlockedGetDtoAssertion isEquivalent(BlockedGetDto blockedGetDto){
        if (!blockedGetDto.startDate().isEqual(actual.startDate())){
            failWithMessage("startDate Is Not Expected");
        }else if (!blockedGetDto.endDate().isEqual(actual.endDate())){
            failWithMessage("endDate Is Not Expected");
        }else if (!blockedGetDto.message().equals(actual.message())){
            failWithMessage("message Is Not Expected");
        }else if (!blockedGetDto.reason().equals(actual.reason())){
            failWithMessage("reason Is Not Expected");
        }else if (!blockedGetDto.property().equals(actual.property())){
            failWithMessage("property Is Not Expected");
        }
        return this;
    }

    public BlockedGetDtoAssertion isEquivalent(Block block){
        if (!block.getStartDate().isEqual(actual.startDate())){
            failWithMessage("startDate Is Not Expected");
        }else if (!block.getEndDate().isEqual(actual.endDate())){
            failWithMessage("endDate Is Not Expected");
        }else if (!block.getMessage().equals(actual.message())){
            failWithMessage("message Is Not Expected");
        }else if (!block.getReason().getId().equals(actual.reason())){
            failWithMessage("reasonId Is Not Expected");
        }else if (!block.getProperty().getId().equals(actual.property())){
            failWithMessage("propertyId Is Not Expected");
        }
        return this;
    }
}
