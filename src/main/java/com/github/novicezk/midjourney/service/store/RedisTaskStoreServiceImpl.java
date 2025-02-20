package com.github.novicezk.midjourney.service.store;

import com.github.novicezk.midjourney.service.TaskStoreService;
import com.github.novicezk.midjourney.support.Task;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class RedisTaskStoreServiceImpl implements TaskStoreService {
	private static final String KEY_PREFIX = "mj-task::";

	private final Duration timeout;
	private final RedisTemplate<String, Task> redisTemplate;

	public RedisTaskStoreServiceImpl(Duration timeout, RedisTemplate<String, Task> redisTemplate) {
		this.timeout = timeout;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void saveTask(Task task) {
		this.redisTemplate.opsForValue().set(getRedisKey(task.getId()), task, this.timeout);
	}

	@Override
	public void deleteTask(String id) {
		this.redisTemplate.delete(getRedisKey(id));
	}

	@Override
	public Task getTask(String id) {
		return this.redisTemplate.opsForValue().get(getRedisKey(id));
	}

	@Override
	public List<Task> listTask() {
		Set<String> keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
			Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(KEY_PREFIX + "*").count(1000).build());
			return cursor.stream().map(String::new).collect(Collectors.toSet());
		});
		if (keys == null || keys.isEmpty()) {
			return Collections.emptyList();
		}
//		ValueOperations<String, Task> operations = this.redisTemplate.opsForValue();
//		return keys.stream().map(operations::get)
//				.filter(Objects::nonNull)
//				.toList();
		ValueOperations<String, Task> operations = this.redisTemplate.opsForValue();
		return keys.stream().map(operations::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

	}


	private String getRedisKey(String id) {
		return KEY_PREFIX + id;
	}

}
