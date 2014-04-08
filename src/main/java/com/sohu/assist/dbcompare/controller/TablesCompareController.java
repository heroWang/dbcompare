package com.sohu.assist.dbcompare.controller;

import com.sohu.assist.dbcompare.model.Result2JsonNode;
import com.sohu.assist.dbcompare.model.TablesCompareSetting;
import com.sohu.assist.dbcompare.model.json.JsonNode;
import com.sohu.assist.dbcompare.model.result.BaseResult;
import com.sohu.assist.dbcompare.model.result.Result;
import com.sohu.assist.dbcompare.service.SettingService;
import com.sohu.assist.dbcompare.service.TablesCompareService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TablesCompareController {
    private static final Logger LOG = Logger.getLogger(TablesCompareController.class);
    @Resource
    private TablesCompareService compareService;
    @Resource
    private SettingService settingService;

    @RequestMapping("/compare/jsontree")
    @ResponseBody
    public Object treejson(HttpServletRequest request, HttpServletResponse response) {
        List<JsonNode> tree = new ArrayList<JsonNode>();
        try {
            TablesCompareSetting setting = (TablesCompareSetting) settingService.getSetting();
            Result result = compareService.compare(setting);

            JsonNode treeNode = Result2JsonNode.result2JsonNode((BaseResult) result, getBasePath(request));

            tree.add(treeNode);
        } catch (Throwable te) {
            te.printStackTrace();
            LOG.error("生成树失败:" + te.getMessage());
            response.setStatus(500);
        }
        return tree;
    }

    private String getBasePath(HttpServletRequest request) {
        String basePath = null;
        String path = request.getContextPath();
        if (request.getServerPort() == 80) {
            basePath = request.getScheme() + "://"
                    + request.getServerName() + path;
        } else {
            basePath = request.getScheme() + "://"
                    + request.getServerName() + ":"
                    + request.getServerPort() + path;
        }
        return basePath;
    }

    @RequestMapping("/setting/save")
    public String saveSetting(TablesCompareSetting setting, boolean isChanged) {
        if (setting != null && isChanged == true) {
            settingService.updateSetting(setting);
        }
        // ModelAndView view = new ModelAndView("result");

        return "redirect:/compare/result";
    }

    @RequestMapping("/compare/result")
    public ModelAndView showResult() {
        ModelAndView view = new ModelAndView("result");

        return view;
    }

    @RequestMapping("/compare/setting")
    public ModelAndView showSetting() {
        ModelAndView view = new ModelAndView("compare");
        view.addObject("setting", settingService.getSetting());

        return view;
    }

}
