package com.github.liuyueyi.forum.web.admin.rest;

import com.github.liueyueyi.forum.api.model.enums.PushStatusEnum;
import com.github.liueyueyi.forum.api.model.vo.PageParam;
import com.github.liueyueyi.forum.api.model.vo.PageVo;
import com.github.liueyueyi.forum.api.model.vo.ResVo;
import com.github.liueyueyi.forum.api.model.vo.article.CategoryReq;
import com.github.liueyueyi.forum.api.model.vo.article.dto.CategoryDTO;
import com.github.liueyueyi.forum.api.model.vo.constants.StatusEnum;
import com.github.liuyueyi.forum.core.permission.Permission;
import com.github.liuyueyi.forum.core.permission.UserRole;
import com.github.liuyueyi.forum.core.util.NumUtil;
import com.github.liuyueyi.forum.service.article.service.CategorySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类后台
 *
 * @author LouZai
 * @date 2022/9/19
 */
@RestController
@Permission(role = UserRole.ADMIN)
@RequestMapping(path = "admin/category/")
public class CategorySettingRestController {

    @Autowired
    private CategorySettingService categorySettingService;

    @ResponseBody
    @PostMapping(path = "save")
    public ResVo<String> save(@RequestBody CategoryReq req) {
        categorySettingService.saveCategory(req);
        return ResVo.ok("ok");
    }

    @ResponseBody
    @GetMapping(path = "delete")
    public ResVo<String> delete(@RequestParam(name = "categoryId") Integer categoryId) {
        categorySettingService.deleteCategory(categoryId);
        return ResVo.ok("ok");
    }

    @ResponseBody
    @GetMapping(path = "operate")
    public ResVo<String> operate(@RequestParam(name = "categoryId") Integer categoryId,
                                 @RequestParam(name = "pushStatus") Integer pushStatus) {
        if (pushStatus != PushStatusEnum.OFFLINE.getCode() && pushStatus!= PushStatusEnum.ONLINE.getCode()) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS);
        }
        categorySettingService.operateCategory(categoryId, pushStatus);
        return ResVo.ok("ok");
    }

    @ResponseBody
    @GetMapping(path = "list")
    public ResVo<PageVo<CategoryDTO>> list(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                           @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        pageNumber = NumUtil.nullOrZero(pageNumber) ? 1 : pageNumber;
        pageSize = NumUtil.nullOrZero(pageSize) ? 10 : pageSize;
        PageVo<CategoryDTO> categoryDTOPageVo = categorySettingService.getCategoryList(PageParam.newPageInstance(pageNumber, pageSize));
        return ResVo.ok(categoryDTOPageVo);
    }
}
