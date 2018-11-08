package com.fight.light.httpbuild;

import android.os.Build;

import com.fight.light.basemvp.IFragment;

import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Build
 * Created by yawei.kang on 2018/6/13.
 */

public final class RetrofitUtil {

      private Map<String,String> headers;
      private Map<String,String> parameters;
      private Retrofit retrofit;
      private Retrofit.Builder retrofitBuilder;
      private OkHttpClient.Builder okhttpBuilder;
      private OkHttpClient okHttpClient;
      private okhttp3.Call.Factory callFactory;
      private final String baseUrl;
      private final List<Converter.Factory> converterFactories;
      private final List<CallAdapter.Factory> adapterFactories;
      private final Executor callbackExecutor;
      private final boolean validateEagerly;

    public RetrofitUtil(Map<String, String> headers, Map<String, String> parameters,
                        Call.Factory callFactory, String baseUrl,
                        List<Converter.Factory> converterFactories,
                        List<CallAdapter.Factory> adapterFactories, Executor callbackExecutor, boolean validateEagerly) {
        this.headers = headers;
        this.parameters = parameters;
        this.okhttpBuilder = okhttpBuilder;
        this.okHttpClient = okHttpClient;
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
        this.converterFactories = converterFactories;
        this.adapterFactories = adapterFactories;
        this.callbackExecutor = callbackExecutor;
        this.validateEagerly = validateEagerly;
    }
   public <T> T create(final Class<T> service){
        return  retrofit.create(service);
   }

public Builder newBuilder(){
       return new Builder(this);
}
   public  final class Builder{

       private static final int DEFAULT_TIMEOUT = 15;
       private static final int DEFAULT_MAXIDLE_CONNECTIONS = 5;
       private static final long DEFAULT_KEEP_ALIVEDURATION = 8;
       private static final long cacheMaxSize = 10*1024*1024;
       private okhttp3.Call.Factory callFactory;
       private String baseUrl;
       private Boolean isLog = false;
       private Boolean isCookie = false;
       private Boolean isCache = true;
       private List<InputStream> certificateList;
       private HostnameVerifier hostnameVerifier;
       private CertificatePinner certificatePinner;
       private List<Converter.Factory> converterFactories = new ArrayList<>();
       private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
       private Executor callbackExecutor;
       private boolean validateEagerly;
       private Converter.Factory converterFactory;
       private CallAdapter.Factory callAdapterFactory;
       private Proxy proxy;
       private Object tag;


       public Builder(RetrofitUtil retrofitUtil) {

       }
         public Builder(){
             okhttpBuilder = new OkHttpClient.Builder();
             retrofitBuilder = new Retrofit.Builder();

          }
       /**
        * The HTTP client used for requests. default OkHttpClient
        * <p/>
        * This is a convenience method for calling {@link #callFactory}.
        * <p/>
        * Note: This method <b>does not</b> make a defensive copy of {@code client}. Changes to its
        * settings will affect subsequent requests. Pass in a {@linkplain OkHttpClient#clone() cloned}
        * instance to prevent this if desired.
        */
      public Builder client(OkHttpClient client){
             retrofitBuilder.client(Utils.checkNotNull(client, "client == null"));
             return this;
      }
       /**
        * Specify a custom call factory for creating {@link } instances.
        * <p/>
        * Note: Calling {@link #client} automatically sets this value.
        */
       public Builder callFactory(okhttp3.Call.Factory factory) {
           this.callFactory = Utils.checkNotNull(factory, "factory == null");
           return this;
       }

       /**
        * Sets the default connect timeout for new connections. A value of 0 means no timeout,
        * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
        * milliseconds.
        */
       public Builder connectTimeout(int timeout) {
           return connectTimeout(timeout, TimeUnit.SECONDS);
       }

       private Builder connectTimeout(int timeout, TimeUnit unit) {
           if (timeout!= -1){
               okhttpBuilder.connectTimeout(timeout,unit);
           }else{
               okhttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
           }
        return this;
       }
       /**
        * Sets the default connect timeout for new connections. A value of 0 means no timeout,
        * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
        * milliseconds.
        */
       public Builder writeTimeout(int timeout) {
           return writeTimeout(timeout, TimeUnit.SECONDS);
       }

       public Builder writeTimeout(int timeout, TimeUnit unit) {
           if (timeout != -1) {
               okhttpBuilder.writeTimeout(timeout, unit);
           } else {
               okhttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
           }
           return this;
       }

       /**
        * Attaches {@code tag} to the request. It can be used later to cancel the request. If the tag
        * is unspecified or null, the request is canceled by using the request itself as the tag.
        */
       public Builder tag(Object tag) {
           this.tag = tag;
           return this;
       }

       /**
        * open default logcat
        *
        * @param isLog
        * @return
        */
       public Builder addLog(boolean isLog) {
           this.isLog = isLog;
           return this;
       }

       /**
        * open sync default Cookie
        *
        * @param isCookie
        * @return
        */
       public Builder addCookie(boolean isCookie) {
           this.isCookie = isCookie;
           return this;
       }

       /**
        * open default Cache
        *
        * @param isCache
        * @return
        */
       public Builder addCache(boolean isCache) {
           this.isCache = isCache;
           return this;
       }

       public Builder proxy(Proxy proxy) {
           this.proxy = proxy;
           okhttpBuilder.proxy(Utils.checkNotNull(proxy, "proxy == null"));
           return this;
       }

       /**
        * Set an API base URL which can change over time.
        *
        * @see ( HttpUrl )
        */
       public Builder baseUrl(String baseUrl) {
           this.baseUrl = Utils.checkNotNull(baseUrl, "baseUrl == null");
           return this;
       }

       /**
        * Add converter factory for serialization and deserialization of objects.
        */
       public Builder addConverterFactory(Converter.Factory factory) {
           this.converterFactory = factory;
           return this;
       }

       /**
        * Add a call adapter factory for supporting service method return types other than {@link CallAdapter
        * }.
        */
       public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
           this.callAdapterFactory = factory;
           return this;
       }

       /**
        * Add Header for serialization and deserialization of objects.
        */
       public <T> Builder addHeader(Map<String, T> headers) {
           okhttpBuilder.addInterceptor(new BaseInterceptor(Utils.checkNotNull(headers, "header == null")));
           return this;
       }

       /**
        * Add parameters for serialization and deserialization of objects.
        */
       public <T> Builder addParameters(Map<String, T> parameters) {
           okhttpBuilder.addInterceptor(new BaseInterceptor(Utils.checkNotNull(parameters, "parameters == null")));
           return this;
       }

       /**
        * Returns a modifiable list of interceptors that observe a single network request and response.
        * These interceptors must call {@link Interceptor.Chain#proceed} exactly once: it is an error
        * for a network interceptor to short-circuit or repeat a network request.
        */
       public Builder addInterceptor(Interceptor interceptor) {
           okhttpBuilder.addInterceptor(Utils.checkNotNull(interceptor, "interceptor == null"));
           return this;
       }

       /**
        * The executor on which {@link retrofit2.Call} methods are invoked when returning {@link retrofit2.Call} from
        * your service method.
        * <p/>
        * Note: {@code executor} is not used for {@linkplain #addCallAdapterFactory custom method
        * return types}.
        */
       public Builder callbackExecutor(Executor executor) {
           this.callbackExecutor = Utils.checkNotNull(executor, "executor == null");
           return this;
       }
       /**
        * When calling {@link #create} on the resulting {@link Retrofit} instance, eagerly validate
        * the configuration of all methods in the supplied interface.
        */
       public Builder validateEagerly(boolean validateEagerly) {
           this.validateEagerly = validateEagerly;
           return this;
       }

       /**
        * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
        * outgoing HTTP requests.
        * <p/>
        * <p>If unset, {@linkplain NovateCookieManager#NO_COOKIES no cookies} will be accepted nor provided.
        */
     /*  public Builder cookieManager(NovateCookieManager cookie) {
           if (cookie == null) throw new NullPointerException("cookieManager == null");
           this.cookieManager = cookie;
           return this;
       }*/

       /**
        *
        */
      /* public Builder addSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
           this.sslSocketFactory = sslSocketFactory;
           return this;
       }*/

       public Builder addHostnameVerifier(HostnameVerifier hostnameVerifier) {
           this.hostnameVerifier = hostnameVerifier;
           return this;
       }

       public Builder addCertificatePinner(CertificatePinner certificatePinner) {
           this.certificatePinner = certificatePinner;
           return this;
       }


      /* *//**
        * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
        * outgoing HTTP requests.
        * <p/>
        * <p>If unset, { NovateCookieManager#NO_COOKIES no cookies} will be accepted nor provided.@linkplain
        */
    /*   public Builder addSSL(String[] hosts, int[] certificates) {
           if (hosts == null) throw new NullPointerException("hosts == null");
           if (certificates == null) throw new NullPointerException("ids == null");


           addSSLSocketFactory(NovateHttpsFactroy.getSSLSocketFactory(context, certificates));
           addHostnameVerifier(NovateHttpsFactroy.getHostnameVerifier(hosts));
           return this;
       }*/

       public Builder addNetworkInterceptor(Interceptor interceptor) {
           okhttpBuilder.addNetworkInterceptor(interceptor);
           return this;
       }

       /**
        * setCache
        *
        * @param cache cahe
        * @return Builder
        */
      public Builder addCache(Cache cache){
           int maxStale = 60*60*24*3;
           return addCache(cache,maxStale);
      }
       /**
        * @param cache
        * @param cacheTime ms
        * @return
        */
       public Builder addCache(Cache cache, final int cacheTime) {
//           addCache(cache, String.format("max-age=%d", cacheTime));
           return this;
       }
       /**
        * @param cache
        * @param cacheControlValue Cache-Control
        * @return
        */
    /*   private Builder addCache(Cache cache, final String cacheControlValue) {
           REWRITE_CACHE_CONTROL_INTERCEPTOR = new CacheInterceptor(mContext, cacheControlValue);
           REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE = new CacheInterceptorOffline(mContext, cacheControlValue);
           addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
           addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
           addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
           this.cache = cache;
           return this;
       }*/
       /**
        * Create the {@link Retrofit} instance using the configured values.
        * <p/>
        * Note: If neither {@link #client} nor {@link #callFactory} is called a default {@link
        * OkHttpClient} will be created and used.
        */
      public RetrofitUtil build(){
            if (baseUrl == null){
                throw new IllegalStateException("Base URL required");
            }
           if (okhttpBuilder == null){

                throw new IllegalStateException("okhttpBuilder required");
           }
          if (retrofitBuilder == null){
               throw new IllegalStateException("retrofitBuild required");
           }

           retrofitBuilder.baseUrl(baseUrl);
          if (converterFactory == null){
              converterFactory = GsonConverterFactory.create();
          }
          retrofitBuilder.addConverterFactory(converterFactory);
          /**
           * Add a call adapter factory for supporting service method return types other than {@link
           * retrofit2.Call}.
           */
          if (callAdapterFactory == null) {
              callAdapterFactory = RxJava2CallAdapterFactory.create();
          }
          retrofitBuilder.addCallAdapterFactory(callAdapterFactory);
          /**
           *okhttp3.Call.Factory callFactory = this.callFactory;
           */
          if (callFactory != null) {
              retrofitBuilder.callFactory(callFactory);

          }
          /**
           * create okHttpClient
           */
          okHttpClient = okhttpBuilder.build();

          /**
           * set Retrofit client
           */


          retrofitBuilder.client(okHttpClient);

          /**
           *create BaseApiService;
           */
//          apiManager = retrofit.create(BaseApiService.class);
       return new RetrofitUtil(headers,parameters,callFactory,baseUrl,converterFactories,adapterFactories,callbackExecutor,validateEagerly);
      }


   }





}
