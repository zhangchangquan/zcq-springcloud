package com.example.provider.mq;

import com.example.provider.entity.UserModel;
import com.example.provider.mapper.UserMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class Reception {

    @Autowired
    private UserMapper userMapper;

    //1\. @RabbitListener(queues = "myQueue") // 不能自动创建队列
    //2\. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3\. 自动创建, Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        List<UserModel> users = userMapper.selectUserList();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("用户信息");
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        HSSFRow title = sheet.createRow(0);
        String[] titledata = {"userName","password","realName","age"};
        String [][] userdata = new String[users.size()][4];
        for(int i = 0;i<users.size();i++){
            userdata[i][0] = users.get(i).getUserName();
            userdata[i][1] = users.get(i).getPassword();
            userdata[i][2] = users.get(i).getRealName();
            userdata[i][3] = users.get(i).getAge().toString();
        }
        for(int i = 0;i<titledata.length;i++){
            HSSFCell cellHead = title.createCell(i);
            cellHead.setCellStyle(cellStyle);
            cellHead.setCellValue(titledata[i]);
        }
        for(int i = 0;i<users.size();i++){
            HSSFRow row = sheet.createRow(i+1);
            for(int j = 0;j<4;j++){
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(userdata[i][j]);
            }

        }


        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream("C:/Users/zhangcq/Desktop/workbook.xls");
            wb.write(outputStream);
            outputStream.flush();
        }catch (IOException e){

        }
    }

    /**
     * 数码供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message) {
        System.out.println("computer MqReceiver: " + message);
    }

    /**
     * 水果供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruit(String message) {
        System.out.println("fruit MqReceiver: " + new Date());
    }

}


