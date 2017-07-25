/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jingdroid.cook.data.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Api Connection class used to retrieve data from the cloud. Implements
 * {@link Callable} so when executed asynchronously can
 * return a value.
 */
public class ApiConnection implements Callable<String> {

	private static final String CONTENT_TYPE_LABEL = "Content-Type";
	private static final String CONNECT_TYPE_CHAR = "Charset";
	private static final String CONNECT_TYPE_CONN = "Connection";
	private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
	public static final MediaType JSON = MediaType
			.parse("application/json; charset=utf-8");

	private URL url;
	private String response;

	private ApiConnection(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public static ApiConnection createGET(String url)
			throws MalformedURLException {
		return new ApiConnection(url);
	}

	public static ApiConnection createPost(String url)
			throws MalformedURLException {
		return new ApiConnection(url);
	}

	public static ApiConnection createGetAndPost(String url)
			throws MalformedURLException {
		return new ApiConnection(url);
	}

	/**
	 * Do a request to an api synchronously. It should not be executed in the
	 * main thread of the application.
	 * 
	 * @return A string response
	 */
	@Nullable
	public String requestSyncCall() {
		connectToApi();
		return response;
	}

	public String requestSyncCallPost(String json) {
		connectToApi(json);
		return response;
	}

	public String requestSyncCallGetAndPost(String params, String filepath) {
		connectToApi(params, filepath);
		return response;
	}

	private void connectToApi() {
		OkHttpClient okHttpClient = this.createClient();
		Log.d("TAG", "get_url:" + this.url);
		final Request request = new Request.Builder().url(this.url)
				.addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON).get()
				.build();

		try {
			this.response = okHttpClient.newCall(request).execute().body()
					.string();
			Log.d("TAG", "get_response:" + this.response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 网络接口
	 * 
	 * @param params
	 */
	private void connectToApi(String params) {
		Log.d("TAG", "url:" + this.url + ",params:" + params);
		OkHttpClient okHttpClient = this.createClient();
		RequestBody fromBody = RequestBody.create(JSON, params);

		Request request = new Request.Builder().url(this.url).post(fromBody)
				.build();
		try {
			this.response = okHttpClient.newCall(request).execute().body()
					.string();
			Log.d("TAG", "response:" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传图片接口
	 * 
	 * @param
	 */
	private void connectToApi(String getParams, String filepath) {
		String end = "\r\n";// 回车换行
		String twoHyphens = "--";// 参数分隔符ps:与boundary分割传入的参数
		String boundary = "***********";// 分界线可以任意分配
		try {
			URL url = new URL(this.url + getParams);
			Log.d("TAG", "uploadIMG:"+url.toString());
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);// 不适用缓存
			httpURLConnection.setRequestMethod("POST");// post请求
			httpURLConnection.setRequestProperty("Connection", "keep-Alive");// 一直保持链接状态
			httpURLConnection.setRequestProperty("Charset", "utf-8");// 字符集编码为utf-8
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// 请求数据为多元性数据，这里只用分界线不用分隔符表示，必须严格按照这样写，不然服务器无法识别

			DataOutputStream dataOutputStream = new DataOutputStream(
					httpURLConnection.getOutputStream());
			// 获取输出流
			String newName = "head.jpg";// 临时文件名字。可任意改.我的服务器端存储的不是它，以为我用了全球唯一标识符（Guid）来命名的
			dataOutputStream.writeBytes(twoHyphens + boundary + end);
			dataOutputStream.writeBytes("Content-Disposition: form-data; "
					+ "name=\"KLBGYPicture\";filename=\"" + newName + "\""
					+ end);
			/*
			 * 注意，千万注意这的MyHeadPicture与浏览器端的<input type="file"
			 * name="MyHeadPicture"/>name对应的属性一致 ，记住不能少了回车换行结束标志
			 */
			dataOutputStream.writeBytes(end);
			File file = new File(filepath);
			InputStream fStream = new FileInputStream(file);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			// StringBuffer sb = new StringBuffer();
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				// sb.append(length);
				/* 将资料写入DataOutputStream中 */
				dataOutputStream.write(buffer, 0, length);// 将文件一字节流形式输入到输出流中
			}
			dataOutputStream.writeBytes(end);
			dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens
					+ end);
			/* close streams */
			fStream.close();
			dataOutputStream.flush();
			Log.d("TAG", "httpURLConnection.getResponseCode():"+httpURLConnection.getResponseCode());
			if (httpURLConnection.getResponseCode() == 200) {
				/* 取得Response内容 */
				InputStream is = httpURLConnection.getInputStream();// 服务器端响应
				int ch;
				StringBuffer b = new StringBuffer();
				while ((ch = is.read()) != -1) {
					b.append((char) ch);
				}
				System.err.println(b.toString());
				dataOutputStream.close();
				response = b.toString();// 返回响应内容
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private OkHttpClient createClient() {
		final OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setReadTimeout(100000, TimeUnit.MILLISECONDS);
		okHttpClient.setConnectTimeout(150000, TimeUnit.MILLISECONDS);

		return okHttpClient;
	}

	@Override
	public String call() throws Exception {
		return requestSyncCall();
	}
}
