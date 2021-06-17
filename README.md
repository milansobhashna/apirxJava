# apirxJava
apirxJava
create interface  apiinterface.kt

``` @FormUrlEncoded
    @POST("end point") 
    fun loginApiCall(
        @Field("number") number: String?,
        @Field("id") id: String?,
    ): Observable<LogInResponse>
```
create response model loginresponse.kt

```data class LogInResponse(
    val `data`: String,
    val message: String,
    val sucess: Int
)
```
add dependency = > 

```
maven { url 'https://jitpack.io' }
```
```
dependencies {
    implementation 'com.github.milansobhashna:apirxJava:1.0.0'

    //retrofit, gson converter & rxJava adapter
    api("com.squareup.retrofit2:retrofit:2.9.0") {
        exclude group: 'com.squareup.okhttp', module: 'okhttp'
    }
    implementation("com.squareup.retrofit2:converter-gson:2.7.2") {
        exclude group: 'com.squareup.okhttp', module: 'okhttp'
    }
    implementation("com.squareup.retrofit2:adapter-rxjava:2.7.2") {
        exclude group: 'io.reactivex', module: 'rxjava'
    }
    implementation "io.reactivex:rxandroid:1.2.1"
    implementation "io.reactivex:rxjava:1.3.4"

    implementation "com.squareup.okhttp3:okhttp:4.4.0"
    implementation "com.squareup.okhttp3:logging-interceptor:4.4.0"
}
```

============================main activity => 

variable ==>
```
val mApiConfig = ApiConfig("base url ") 
val mCompositeSubscription : CompositeSubscription? = CompositeSubscription()
```
call api function ==>
```
loginApi()
```
create function for api call ==>
```
private fun loginApi () {
        
        val logOut: Observable<LogInResponse> = mapplyScheduler.call(
            mApiConfig.getClient()!!.create(ApiInterface::class.java).loginApiCall("number","id")

        ).compose(mapplyScheduler.applySchedulers())

        val subscription = logOut.subscribe(object : Observer<LogInResponse> {
            override fun onError(e: Throwable?) {

            }

            override fun onNext(t: LogInResponse?) {
                // response
            }

            override fun onCompleted() {
            }

        })
        mCompositeSubscription!!.add(subscription)
    }
```
