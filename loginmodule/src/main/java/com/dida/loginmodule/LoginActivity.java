package com.dida.loginmodule;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dida.loginmodule.adapter.MyActAdapter;
import com.dida.loginmodule.model.Recipe;
import com.dida.loginmodule.model.dao.RecipeDatabase;
import com.dida.loginmodule.util.ChineseAndEnglish;
import com.dida.loginmodule.util.WordUtil;

import java.util.ArrayList;
import java.util.List;

import static com.dida.commonservicelib.path.ARouterPath.LOGIN_ACTIVITY_LOGIN;

@Route(path = LOGIN_ACTIVITY_LOGIN)
public class LoginActivity extends AppCompatActivity {
    private RecipeDatabase recipeDatabase;
    boolean working = false;
    private Recipe recipe;
    private List<String> recipeList = new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//竖屏
        setContentView(R.layout.mod_activity_login);
        recipeDatabase = RecipeDatabase.getInstance(this);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(new MyActAdapter(this));
//        setThreshold(1);
        autoCompleteTextView.setThreshold(1);
    }

    /**
     * @return 生成的学生姓名。
     * @description 随机生成学生姓名。有20种姓氏；名字长度可能有1-2位，共有6480中组合。共有129600种可能的姓名组合。
     */
    public static String getName() {
        String[] firstNameArray = {
                "李", "王", "张", "刘", "陈",
                "杨", "赵", "黄", "周", "吴",
                "徐", "孙", "胡", "朱", "高",
                "欧阳", "太史", "端木", "上官",
                "司马"
        };// 20个姓，其中5个复姓
        String[] lastNameArray = {
                "伟", "勇", "军", "磊", "涛",
                "斌", "强", "鹏", "杰", "峰",
                "超", "波", "辉", "刚", "健",
                "明", "亮", "俊", "飞", "凯",
                "浩", "华", "平", "鑫", "毅",
                "林", "洋", "宇", "敏", "宁",
                "建", "兵", "旭", "雷", "锋",
                "彬", "龙", "翔", "阳", "剑",
                "静", "敏", "燕", "艳", "丽",
                "娟", "莉", "芳", "萍", "玲",
                "娜", "丹", "洁", "红", "颖",
                "琳", "霞", "婷", "慧", "莹",
                "晶", "华", "倩", "英", "佳",
                "梅", "雪", "蕾", "琴", "璐",
                "伟", "云", "蓉", "青", "薇",
                "欣", "琼", "宁", "平", "媛"
        };// 80个常用于名字的单字
        int firstPos = (int) (20 * Math.random());
        StringBuilder name = new StringBuilder(firstNameArray[firstPos]);
        int lastLen = (int) (2 * Math.random()) + 1;
        /*
         * 为了各函数的统一性，此处也用for循环实现 int lastPos1 = (int) (80 * random()); String lastName =
         * lastNameArray[lastPos1]; if (lastLen == 2) { int lastPos2 = (int) (80 *
         * random()); lastName = lastName + lastNameArray[lastPos2]; }
         */
        for (int i = 0; i < lastLen; i++) {
            int lastPos = (int) (80 * Math.random());
            name.append(lastNameArray[lastPos]);
        }
        return name.toString();
    }

    public void doInsertDb(View view) {
        if (!working) {
            working = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    recipeDatabase.beginTransaction();  //手动设置开始事务
                    long startTime = System.currentTimeMillis();   //获取开始时间
                    for (int i = 0; i < 5; i++) {
                        recipe = new Recipe();
                        recipe.setTitle(getName());
                        recipeDatabase.daoAccess().insertOnlySingleRecipe(recipe);
                    }
                    recipeDatabase.setTransactionSuccessful();        //设置事务处理成功，不设置会自动回滚不提交
                    recipeDatabase.endTransaction();        //处理完成
                    long endTime = System.currentTimeMillis(); //获取结束时间
                    System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
                    working = false;
                }
            }).start();
        }
    }
}
