package com.example.carryingmedicine.Register;

import android.widget.Toast;

import com.example.carryingmedicine.PasswordChange.PasswordChangeActivity;
import com.example.carryingmedicine.PasswordChange.Password_Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCondition
{
    public static final Pattern pwPattern= Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$"); //숫자 문자 특수문자 포함 8~15자
    public static final Pattern pwPattern2 = Pattern.compile("^(\\w)\\1\\1\\1");
    public static final Pattern idPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9]).{6,12}.$");
    public static final Pattern nPattern = Pattern.compile("^[가-힣]*$");
    RegisterActivity activity; // 선언해주기
    PasswordChangeActivity changeActivity;

    public RegisterCondition(RegisterActivity inActivity)
    { //생성자 이렇게선언하기 toast 쓸려면
            activity = inActivity;
    }

   public boolean user_id_check(String user_id){
       Matcher id_matcher = idPattern.matcher(user_id);
       if(!id_matcher.matches()){
           Toast.makeText(activity.getApplicationContext(), "아이디는 영문, 숫자로 입력 가능하고 6에서 12자리로 입력해주세요",
                   Toast.LENGTH_SHORT).show();
           return false;
       }
       else { return true; }
    }

    public boolean user_name_check(String user_name)
    {
        Matcher n_matcher = nPattern.matcher(user_name);
        if(!n_matcher.matches()){
            Toast.makeText(activity.getApplicationContext(), "이름은 한글로만 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean user_pass_check(String user_pass, String user_id)
    {
        //String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z])";
        Matcher matcher = pwPattern.matcher(user_pass);
        // pwPattern = "(.)\\1\\1\\1";
        Matcher matcher2 = pwPattern2.matcher(user_pass);
        if(!matcher.matches()){
            Toast.makeText(activity.getApplicationContext(), "비밀번호는 숫자 문자 특수문자 포함 8~15자가 되어야합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(user_pass.indexOf(user_id)>-1){
            Toast.makeText(activity.getApplicationContext(), "비밀번호에 아이디형식이 들어갈 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(user_pass.contains(" ")) {
            Toast.makeText(activity.getApplicationContext(), "비밀번호엔 공백을 포함할수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }




}
