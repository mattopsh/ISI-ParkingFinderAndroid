package com.hfad.parkingfinder.restclient.fb.dto

data class UserPictureResponseDto(val picture: PictureDataDto, val id: String)
data class PictureDataDto(val data: DataDto)
data class DataDto(val height: Int, val width: Int, val url: String)