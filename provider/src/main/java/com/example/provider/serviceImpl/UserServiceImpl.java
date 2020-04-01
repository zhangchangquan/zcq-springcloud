package com.example.provider.serviceImpl;

import com.example.common.exception.UserOrPasswordNotNullException;
import com.example.common.util.Md5Util;
import com.example.common.vo.LoginResponse;
import com.example.common.vo.Response;
import com.example.provider.entity.UserModel;
import com.example.provider.mapper.UserMapper;
import com.example.provider.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserModel> selectUserList() {
        List<UserModel> users = userMapper.selectUserList();

        return users;
    }

    @Override
    public LoginResponse<UserModel> findUser(String userName, String password) {
        LoginResponse<UserModel> userModelResponse = new LoginResponse<>();
        try{
            if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
                throw new UserOrPasswordNotNullException("用户名或密码不能为空");
            }
            password = Md5Util.getMD5(password);
            UserModel userModel = userMapper.findUser(userName, password);
            userModelResponse.setResult(userModel);
        }catch (UserOrPasswordNotNullException e){
            userModelResponse.setCode(e.getCode());
            userModelResponse.setMessage(e.getMessage());
        }

        return userModelResponse;
    }

    @Override
    public void importUser() {
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
}
