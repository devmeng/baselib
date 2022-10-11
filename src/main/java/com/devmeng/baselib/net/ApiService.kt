package com.devmeng.baselib.net

import com.devmeng.baselib.beans.ArticleBean
import com.devmeng.baselib.beans.BannerBean
import com.devmeng.baselib.beans.HomePageArticleBean
import com.devmeng.baselib.beans.UserBean
import com.devmeng.baselib.utils.ConstantUtils
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Richard -> MHS
 * Date : 2022/5/30  15:09
 * Version : 1
 */
interface ApiService {

    @GET(ApiConstant.HomePage.HOME_BANNER)
    fun getBannerList(): Observable<HttpResult<MutableList<BannerBean>>>

    @GET(ApiConstant.HomePage.STICK_TOP_ARTICLE)
    fun getStickTopList(): Observable<HttpResult<MutableList<ArticleBean>>>

    @GET(ApiConstant.HomePage.ARTICLE_LIST)
    fun getNormalArticleList(@Path(ConstantUtils.PATH.PAGE) page: Int): Observable<HttpResult<HomePageArticleBean>>

    @FormUrlEncoded
    @POST(ApiConstant.UserOperation.SIGN_IN)
    fun signIn(
        @Field(ConstantUtils.Params.ACCOUNT) account: String,
        @Field(ConstantUtils.Params.PASSWORD) password: String
    ): Observable<HttpResult<UserBean>>

}