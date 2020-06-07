package com.hfad.parkingfinder.apicalls.fb.dto

data class UserPictureResponseDto(val picture: PictureDataDto, val id: String)
data class PictureDataDto(val data: DataDto)
data class DataDto(val height: Int, val width: Int, val url: String)