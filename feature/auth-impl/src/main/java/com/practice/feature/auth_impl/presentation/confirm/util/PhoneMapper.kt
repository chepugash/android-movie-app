package com.practice.feature.auth_impl.presentation.confirm.util

class PhoneMapper {

    fun originalToMapped(phone: String): String {
        return "+7 (" + (phone.subSequence(0, 3)) +
                ") " + (phone.subSequence(3, 6)) +
                "-" + (phone.subSequence(6, 8)) +
                "-" + (phone.subSequence(8, 10))
    }

    fun mappedToOriginal(phone: String): String {
        return phone.subSequence(4, phone.length).filter { it.isDigit() }.toString()
    }
}
