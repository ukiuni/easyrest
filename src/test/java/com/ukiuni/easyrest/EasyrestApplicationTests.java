package com.ukiuni.easyrest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ukiuni.easyrest.entity.Todo;

import lombok.val;

public class EasyrestApplicationTests {
	private ConfigurableApplicationContext context;
	private static final String PORT = SocketUtils.findAvailableTcpPort() + "";
	private static final String BASE_URL = "http://localhost:" + PORT;

	@Before
	public void init() throws InterruptedException {
		System.setProperty("server.port", PORT);
		context = SpringApplication.run(EasyrestApplication.class, new String[] {});
	}

	@After
	public void tearDown() {
		SpringApplication.exit(context);
	}

	@Test
	public void testIndex() {
		IntStream.range(0, 100).forEach(this::crud);
		System.out.println("average :" + costs.parallelStream().mapToLong(c -> c).average());
		System.out.println("total   :" + costs.parallelStream().mapToLong(c -> c).average());
	}

	public List<Long> costs = new ArrayList<>();

	public void crud(int i) {
		long start = System.currentTimeMillis();
		val rest = new RestTemplate();
		val postedTodo = rest.postForEntity(BASE_URL + "/todos", new Todo(-1, "タイトル" + i, "値" + i, 0 != i % 2, new Date()), Todo.class).getBody();
		val getedTodo = rest.getForEntity(BASE_URL + "/todos/" + postedTodo.getId(), Todo.class).getBody();
		Assert.assertNotEquals("idが生成されている", 0, getedTodo.getId());
		String updateDescription = "更新したdescription" + i;
		getedTodo.setDescription(updateDescription);

		rest.put(BASE_URL + "/todos/" + getedTodo.getId(), getedTodo);
		Assert.assertEquals("説明が更新されている。", updateDescription, rest.getForEntity(BASE_URL + "/todos/" + getedTodo.getId(), Todo.class).getBody().getDescription());
		rest.delete(BASE_URL + "/todos/" + getedTodo.getId());
		try {
			rest.getForEntity(BASE_URL + "/todos/" + getedTodo.getId(), Todo.class);
			Assert.fail();
		} catch (HttpClientErrorException e) {
			Assert.assertEquals("データが消えている。", 404, e.getStatusCode().value());
		}
		costs.add(System.currentTimeMillis() - start);
	}

}
