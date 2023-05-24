package com.github.novicezk.midjourney.controller;

import com.github.novicezk.midjourney.dto.NotifyDTO;
import com.github.novicezk.midjourney.service.TaskStoreService;
import com.github.novicezk.midjourney.support.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "任务查询")
@RestController
@RequestMapping("/task")
@Slf4j
@RequiredArgsConstructor
public class TaskController {
	private final TaskStoreService taskStoreService;

	@ApiOperation(value = "查询所有任务")
	@GetMapping("/list")
	public List<Task> listTask() {
		return this.taskStoreService.listTask();
	}

	@ApiOperation(value = "指定ID获取任务")
	@GetMapping("/{id}/fetch")
	public Task getTask(@ApiParam(value = "任务ID") @PathVariable String id) {
		return this.taskStoreService.getTask(id);
	}

	@ApiOperation(value = "回调")
	@PostMapping("/notify")
	public String notify(@RequestBody NotifyDTO notifyDTO) {
		log.info("-----------notifyDTO:{}", notifyDTO);
		return null;
		//return this.taskStoreService.callBack(taskId);
	}

}
