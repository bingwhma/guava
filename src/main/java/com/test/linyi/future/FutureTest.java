package com.test.linyi.future;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class FutureTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Guava Future 能够 减少主函数的等待时间，使得多任务能够异步非阻塞执行
		
		FutureTest f = new FutureTest();
		f.test001();

	}

	public void test001() {
		ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

		ListenableFuture<Boolean> booleanTask = service.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				Thread.sleep(10000);
				return true;
			}
		});

		Futures.addCallback(booleanTask, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				System.out.println("BooleanTask.任务1-10s: " + result);
			}

			@Override
			public void onFailure(Throwable throwable) {
				System.out.println("BooleanTask.throwable: " + throwable);
			}
		}, service);

		ListenableFuture<String> stringTask = service.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(3000);
				return "Hello World";
			}
		});

		Futures.addCallback(stringTask, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("StringTask.任务2-3s: " + result);
			}

			@Override
			public void onFailure(Throwable t) {
			}
		}, service);

	}

}
