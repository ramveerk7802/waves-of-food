package com.example.food.Data

import android.os.Parcel
import android.os.Parcelable

class OrderDetailModel():Parcelable{
    var uid:String?=null
    var name:String?=null
    var address:String?=null
    var mobile:String?=null
    var totalAmt:Int=0
    var itemName:MutableList<String>?=null
    var itemImage:MutableList<String>?=null
    var itemQuantity:MutableList<Int>?=null
    var itemPrice:MutableList<Int>?=null
    var currentTime:Long=0
    var orderAccepted:Boolean=false
    var paymentReceived:Boolean=false
    var orderKey:String?=null

    constructor(parcel: Parcel) : this() {
        uid = parcel.readString()
        name = parcel.readString()
        address = parcel.readString()
        mobile = parcel.readString()
        totalAmt =parcel.readInt()
        currentTime = parcel.readLong()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        orderKey = parcel.readString()
    }

    constructor(
        userId: String,
        name: String,
        address: String,
        number: String,
        amount:Int,
        itemName: MutableList<String>,
        itemImage: MutableList<String>,
        itemQuantity: MutableList<Int>,
        itemPrice: MutableList<Int>,
        currentTime: Long,
        orderAccepted: Boolean,
        paymentReceived: Boolean,
        orderKey: String
    ):this(){
        this.uid=userId
        this.name=name
        this.address=address
        this.mobile=number
        this.totalAmt=amount
        this.itemName=itemName
        this.itemImage=itemImage
        this.itemQuantity=itemQuantity
        this.itemPrice=itemPrice
        this.currentTime=currentTime
        this.orderAccepted=orderAccepted
        this.paymentReceived=paymentReceived
        this.orderKey=orderKey
    }











    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(mobile)
        parcel.writeLong(currentTime)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(orderKey)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetailModel> {
        override fun createFromParcel(parcel: Parcel): OrderDetailModel {
            return OrderDetailModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetailModel?> {
            return arrayOfNulls(size)
        }
    }

}
