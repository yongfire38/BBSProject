package egovframework.com.cop.bbs.bbs.service;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {
	public CompletableFuture<Void> performAsyncSync();
}
