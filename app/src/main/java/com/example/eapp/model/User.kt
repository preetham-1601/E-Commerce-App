package com.example.eapp.model

class User {
    var name: String? = null
    var email: String?= null
    var uid: String? = null
    var mobileNumber: String?= null
    var address: String?= null
    var city: String?= null
    var state: String?= null
    var pinCode: String?= null
    var password: String?= null

    constructor(){}

    constructor(name:String?, email:String?, uid:String?,mobileNumber: String?,address: String?,city: String?,state: String?,pinCode: String?,password: String?){
        this.name=name
        this.email=email
        this.uid=uid
        this.mobileNumber=mobileNumber
        this.address=address
        this.city=city
        this.state=state
        this.pinCode=pinCode
        this.password=password



    }

}
