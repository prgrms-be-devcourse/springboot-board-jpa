package com.programmers.springbootboardjpa.global.validate;

import com.programmers.springbootboardjpa.global.validate.ValidationGroups.NotBlankGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.NotNullGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.PositiveGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.SizeCheckGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class, NotNullGroup.class, SizeCheckGroup.class, PositiveGroup.class})
public interface ValidationSequence {

}
