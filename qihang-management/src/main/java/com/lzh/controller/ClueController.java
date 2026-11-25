package com.lzh.controller;

import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.ClueDto;
import com.lzh.pojo.Clue;
import com.lzh.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clues")
public class ClueController {
    @Autowired
    private ClueService clueService;
    @GetMapping
    public Result findByPage(ClueDto clueDto){

        return Result.success(clueService.findByPage(clueDto));

    }
    @PostMapping
    public Result add(@RequestBody Clue clue){
        // 改1：新增用 save（原来用 saveOrUpdate，请求里一旦带 id 就会变成"更新"而不是新增）
        // 改2：按返回值给结果（原来 Result.success(...) 无论成败都返回 code=1）
        // 改3：改走 service 的 add（里面会校验手机号是否已存在，重复时返回明确提示）
        return clueService.add(clue) > 0 ? Result.success() : Result.error("添加失败");
    }
    @PutMapping("/assign/{clueId}/{userId}")
    public Result assign(@PathVariable Integer clueId, @PathVariable Integer userId){
        return clueService.assign(clueId, userId) > 0 ? Result.success() : Result.error("分配失败");
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Clue clue = clueService.findById(id);
        return Result.success(clue);
    }

    @PutMapping
    public Result follow(@RequestBody Clue clue){
        clueService.follow(clue);
        return Result.success();

    }
    @PutMapping("/false/{id}")
    public Result falseClue(@PathVariable Integer id, @RequestBody ClueDto clueDto){
        clueService.falseClue(id, clueDto);
        return Result.success();
    }
    @PutMapping("/toBusiness/{id}")
    public Result toBusiness(@PathVariable Integer id){
        clueService.toBusiness(id);
        return Result.success();
    }
    // 加：/pool 是业务上的正确拼写（商机公海池就是 /businesses/pool），
    //     原来的 /poll 是个笔误，两个都留着，前端用哪个都能查到线索池
    @GetMapping({"/poll", "/pool"})
    public Result findPoll(Clue clue){
        return Result.success(clueService.findPoll(clue));
    }

}
