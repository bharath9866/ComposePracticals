package com.example.ktor.dto

import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("isdCode")
    val isdCode: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("tenantName")
    val tenantName: String,
    @SerializedName("userDto")
    var userDto: UserDto
) {
    constructor() : this("", "", "", "", UserDto())
}

data class UserDto(
    var active: Boolean? = null, // true
    var admissionNumber: String? = null, // string
    var board: Board? = null,
    var country: String? = null, // string
    var createdTime: String? = null, // 2021-07-29T13:39:11.600Z
    var email: String? = null, // string
    var exams: List<Exam?>? = null,
    var firstName: String? = null, // string
    var grade: Grade? = null,
    var isPassSet: Boolean? = null, // true
    var isProfileComplete: Boolean? = null, // true
    var isdCode: String? = null, // string
    var lastLoggedInTime: String? = null, // 2021-07-29T13:39:11.600Z
    var lastName: String? = null, // string
    var middleName: String? = null, // string
    var modifiedTime: String? = null, // 2021-07-29T13:39:11.600Z
    var password: String? = null, // string
    var phone: String? = null, // string
    var products: List<Product?>? = null,
    var profilePhoto: String? = null, // string
    var stream: Stream? = null,
    var tenantId: Int? = 0, // 0
    var subTenant: Int? = null,
    var userId: Int? = null, // 0
    var userType: UserType? = null,
    var whatsappConsent: Boolean? = null, // true
    var roles: List<Role>? = null,
) {
    data class Role(
        var roleId: Int,
        var roleName: String,
        var createdTime: String,
        var roleCode: String,
        var displayName: String,
        var active: Boolean
    )

    data class Board(
        var active: Boolean? = null, // true
        var boardId: Int? = null, // 0
        var createdTime: String? = null, // 2021-07-29T13:39:11.600Z
        var description: String? = null, // string
        var image: String? = null, // string
        var name: String? = null, // string
        var tenantId: Int? = null // 0
    )

    data class Exam(
        var active: Boolean? = null, // true
        var createdTime: String? = null, // 2021-07-29T13:39:11.600Z
        var description: String? = null, // string
        var examId: Long? = 0,
        var image: String? = null, // string
        var name: String = "", // string
        var tenantId: Int? = null, // 0
        var isSelected: Boolean = false // 0
    )

    data class Grade(
        var active: Boolean? = null, // true
        var createdTime: String? = null, // 2021-07-29T13:39:11.600Z
        var description: String? = null, // string
        var gradeId: Int? = null, // 0
        var image: String? = null, // string
        var name: String? = null, // string
        var tenantId: Int? = null // 0
    )

    data class Product(
        var description: String? = null, // string
        var name: String? = null, // string
        var productId: Int? = null, // 0
        var tenantId: Int? = null // 0
    )

    data class Stream(
        var active: Boolean? = null, // true
        var createdTime: String? = null, // 2021-07-29T13:39:11.600Z
        var description: String? = null, // string
        var image: String? = null, // string
        var name: String? = null, // string
        var streamId: Int? = null, // 0
        var tenantId: Int? = null // 0
    )

    data class UserType(
        var active: Boolean? = null, // true
        var createdTime: String? = null, // 2021-07-29T13:39:11.600Z
        var name: String? = null, // string
        var tenantId: Int? = null, // 0
        var userTypeId: Int? = null // 0
    )
}