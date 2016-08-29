package org.ahant.validator.validation

import com.google.common.collect.ImmutableSet

import org.ahant.validator.util.CommonUtil.Companion.isNotBlank

/**
 * Created by ahant on 8/14/2016.
 */
internal class StringFieldValidator : FieldValidator<String> {

    private var regEx = ""
    private var msg = ""
    private var maxLength = 0
    private var minLength = 0

    constructor(){}

    @JvmOverloads constructor(regEx: String, msg: String, maxLength: Int = 0, minLength: Int = 0) {
        this.regEx = regEx
        this.msg = msg
        this.maxLength = maxLength
        this.minLength = minLength
    }

    override fun validate(input: String): Set<String> {
        var isValid = false
        if (isNotBlank(input)) {
            isValid = true
            if (minLength > 0) {
                isValid = input.length >= minLength
            }
            if (isValid && maxLength > 0) {
                isValid = input.length <= maxLength
            }
            if (isValid) {
                isValid = if (isNotBlank(regEx)) input.matches(regEx.toRegex()) else true
            }
        }
        return if (isValid) ImmutableSet.of() else ImmutableSet.of(if (isNotBlank(msg)) String.format(msg, input) else "Invalid value: " + input)
    }
}
