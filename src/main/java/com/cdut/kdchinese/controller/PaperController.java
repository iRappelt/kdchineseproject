package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.*;
import com.cdut.kdchinese.service.ExaminationQuestionService;
import com.cdut.kdchinese.service.PapersService;
import com.cdut.kdchinese.service.UserService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.*;

import static com.cdut.kdchinese.util.ResultCode.*;

@Controller
public class PaperController {
    @Resource
    private PapersService PS;
    @Resource
    private ExaminationQuestionService EQ;

    @RequestMapping(value = "/show_exam", method = RequestMethod.GET)
    @ResponseBody
    public Result FengYeShowExam(Integer page, Integer limit, Integer classId) throws JsonProcessingException {
        //每页展示数量
        int pageLimit = Integer.valueOf(limit);
        //页码
        int pageStart = pageLimit * (Integer.valueOf(page) - 1);
        //总数
        int count = PS.selAllCount();
        List<Papers> mps = PS.findByPage(pageStart, pageLimit);
        List<Object> JSONlist=new ArrayList<>();
        for (Papers p:mps) {
            String OM = new ObjectMapper().writeValueAsString(p);

            String[] strsplit = OM.replace("{","").replace("}","").split(",");
            String newOM="[{";
            for(String s:strsplit){
                if(s.contains("ids") || s.contains("examinationQuestions") || s.contains("class_id")){
                    continue;
                }
                newOM=newOM+s+",";


            }
            newOM=newOM.substring(0,newOM.length()-1);
            newOM=newOM+"}]";
            JSONArray json = JSONArray.fromObject(newOM);

            JSONlist.add(json.get(0));
        }
        Object[] obj = { JSONlist, count};

        return Result.success(obj);

//        if (mps.size() != 0) {
//            return Result.success(obj);
//        } else {
//            return Result.failure(ResultCode.Papers_FIND_ERROR);
//        }

    }


    /**
     * 根据id删除试卷信息记录
     *
     * @param id
     * @return 成功和失败提醒
     */

    @RequestMapping(value = "/delete_exam", method = RequestMethod.GET)
    @ResponseBody
    public Result FengYeShowExam(Integer id) {
        int result = PS.delPapersinfoById(id);
        if (result == 1) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.Paper_DELETE_ERROR);
        }

    }

    /**
     * 根据id集合批量删除试卷信息记录
     *
     * @param map
     * @return 成功或失败提醒
     */


    @RequestMapping(value = "/batch_delete_exam", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDeletePapersError(@RequestBody Map<String, Object> map) {
        List<Integer> delList=(List<Integer>)map.get("delList");
        int length = delList.size();
        Papers p = new Papers();
        p.setIds(delList);
        int result = PS.delPapersinfoBatch(p);
        if (result == length) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.Batch_Papers_DELETE_ERROR);
        }
    }


    @RequestMapping(value = "/show_question", method = RequestMethod.POST)
    @ResponseBody
    public Result showQuestionByPapersId(@RequestBody HashMap<String ,Object> paramMap) throws JsonProcessingException {
        int id = (int) paramMap.get("id");
        Map<String, Object> map = new HashMap<>();
        Papers p = PS.findQuestionByPaperId(id);
        List<Object> stringlist=new ArrayList<>();
        map.put("examTitle", p.getPaper_name());
        map.put("examDes", p.getPaper_des());
        if (p.getExaminationQuestions().size() == 0) {
            System.out.print("该试卷无题");
            return Result.failure(No_Question, map);
        } else {
            List<ExaminationQuestion> eq = p.getExaminationQuestions();
            for (ExaminationQuestion EQ:eq) {

                String EQOM = new ObjectMapper().writeValueAsString(EQ);
                String[] strsplit = EQOM.replace("{","").replace("}","").split(",");
                String newOM="[{";
                for(String s:strsplit){
                    if(s.contains("paper_id") || s.contains("ids") || s.contains("papers") ){
                        continue;
                    }
                    newOM=newOM+s+",";
                }
                newOM=newOM.substring(0,newOM.length()-1);
                newOM=newOM+"}]";

                JSONArray json =  JSONArray.fromObject(newOM);
                stringlist.add(json.get(0));
                System.out.println("miss"+json.get(0));


            }

            map.put("question", stringlist);

            return Result.success(map);

        }

    }


    @RequestMapping(value = "/publish_exam", method = RequestMethod.GET)
    @ResponseBody
    public Result modifyPublishExam(Integer id) {
        int result = PS.modifyPapersPublish(id);
        if (result == 1) {
            return Result.success();
        } else {
            return Result.failure(Papers_ADD_Error);
        }

    }


    @RequestMapping(value = "/add_exam", method = RequestMethod.POST)
    @ResponseBody
    public Result addExaminationQuestionAndPaper(@RequestBody Map<String, Object> map){
        Date now = new Date();
        DateFormat ddtf = DateFormat.getDateTimeInstance();


        HashMap<String,Object> data =(HashMap<String, Object>) map.get("data");
        String paperName=(String)data.get("exam_title");
        String paperDes=(String) data.get("exam_des");
        String time = ddtf.format(now);
        Integer publish=0;
        Integer classId=(Integer) map.get("classId");

        Papers papers=new Papers();
        papers.setPaper_name(paperName);
        papers.setPaper_des(paperDes);
        papers.setPaper_date(time);
        papers.setPublish(publish);
        papers.setClass_id(classId);

        int result1=PS.addPapersinfo(papers);
        if(result1!=1){
            return Result.failure(Papers_ADD_Error);
        }

        Integer paperId = papers.getPid();
        Integer chooseCount = (Integer)map.get("chooseCount");
        Integer fillCount = (Integer)map.get("fillCount");
        if (chooseCount!=0) {
            for (int i = 1; i <= chooseCount; i++) {
                String questionAnswer = (String) data.get("choose_correct" + i);
                String questionContent = (String) data.get("choose_question" + i);
                Integer questionScore = Integer.parseInt((String) data.get("choose_score" + i));
                String qA = (String) data.get("option_" + i + "_1");
                String qB = (String) data.get("option_" + i + "_2");
                String qC = (String) data.get("option_" + i + "_3");
                String qD = (String) data.get("option_" + i + "_4");
                Integer questionType = 1;

                ExaminationQuestion examinationQuestion = new ExaminationQuestion();
                examinationQuestion.setQuestion_content(questionContent);
                examinationQuestion.setQuestion_type(questionType);
                examinationQuestion.setQuestion_score(questionScore);
                examinationQuestion.setQuestion_answer(questionAnswer);
                examinationQuestion.setqA(qA);
                examinationQuestion.setqB(qB);
                examinationQuestion.setqC(qC);
                examinationQuestion.setqD(qD);
                examinationQuestion.setPaper_id(paperId);
                int result2 = EQ.addQuestioninfo(examinationQuestion);
                if (result2 != 1) {
                    return Result.failure(Question_ADD_Error);
                }

            }
        }
        if(fillCount!=0){
            for (int j = 1; j <= fillCount; j++) {
                String questionAnswer = (String) data.get("fill_correct" + j);
                String questionContent = (String) data.get("fill_question" + j);
                Integer questionScore = Integer.parseInt((String)data.get("fill_score" + j));
                Integer questionType = 2;

                ExaminationQuestion examinationQuestion = new ExaminationQuestion();
                examinationQuestion.setQuestion_content(questionContent);
                examinationQuestion.setQuestion_type(questionType);
                examinationQuestion.setQuestion_score(questionScore);
                examinationQuestion.setQuestion_answer(questionAnswer);
                examinationQuestion.setPaper_id(paperId);
                examinationQuestion.setqA("无");
                examinationQuestion.setqB("无");
                examinationQuestion.setqC("无");
                examinationQuestion.setqD("无");
                int result3 = EQ.addQuestioninfo(examinationQuestion);
                if (result3 != 1) {
                    return Result.failure(Question_ADD_Error);
                }

            }
        }



        return Result.success();


    }
}
