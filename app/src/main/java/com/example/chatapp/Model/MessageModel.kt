package com.example.chatapp.Model

data class MessageModel(
    var message:String?="",
    var senderId:String?="",
    var timeStamp:Long?=0
)
