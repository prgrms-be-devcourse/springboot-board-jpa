package com.programmers.springbootboardjpa.dto.validate;

import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.NotBlankGroup;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.NotNullGroup;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.PositiveGroup;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.SizeGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class, NotNullGroup.class, SizeGroup.class, PositiveGroup.class})
public class ValidationSequence {

}
