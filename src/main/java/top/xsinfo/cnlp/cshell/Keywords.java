package top.xsinfo.cnlp.cshell;


/**
 * 关键字类
 * 在这里可以修改关键字
 * 使用之前最好是先看一下源代码
 *
 * 说明文档全程使用js当伪代码来写
 * ------------------------------------
 *             写在前面
 *       本人的语言水平实在是有限
 *           望其他能人改之
 * -------------------------------------
 */
public class Keywords {

//---------------------------变量-------------------------------

    /**
     * 声明全局变量
     *
     * 变量的一些案例
     *
     * 吾之名乃"孙悟空"也 -> 名 = "孙悟空"
     * 吾之大名同为名也 -> 大名 = 名
     *
     * 这些变量将会保存到全局变量,任何地方都能调用
     *-------------------------------
     * 吾宣师弟乃"八戒"也 -> var 师弟 = "八戒"
     * 吾宣二师弟同为师弟也 -> var 二师弟 = 师弟
     */
    public static final String KEY_var = "吾之";
    /**变量赋值**/
    public static final String KEY_var_fuzhi = "乃";
    /**变量结束语**/
    public static final String KEY_var_over = "也";
    /**变量修改为其他变量地址**/
    public static final String KEY_var_oldToNew = "同为";
    /**声明局部变量(不存储,使用后或者方法结束丢弃)**/
    public static final String KEY_mvar = "吾宣";




//---------------------------函数(方法)------------------------------

    /**
     * 声明方法
     *
     *
     * 吾宣一术名曰找师弟宣 吾宣师弟乃"八戒"也 取其师弟者也
     * function 找师弟(){
     *     var 师弟 = "八戒";
     *     return 师弟;
     * }
     *
     *
     * 吾有一术名曰师弟信息 其必有名,年龄 宣 吾宣信息乃则其名与年龄合并之也 取其信息者也
     * function 师弟信息(名,年龄){
     *     var 信息 = 名 + 年龄;
     *     return 信息
     * }
     *
     * //----调用函数-----------
     * 吾之师弟乃 执其找师弟:"八戒",年龄乎 也
     * 师弟 = 找师弟("八戒",年龄);//这里调用 年龄 这个变量取值
     *
     * 吾之师弟信息乃 执其师弟信息:"八戒","18"乎 也
     * 师弟信息 = 师弟信息("八戒","18");
     *
     * 注意:
     * 找师弟 这个方法被保存到全局变量
     * 他可以当一个变量使用
     * 值的参数必须加引号,多个参数用逗号分开
     * 所有方法必须有返回值
     * 如果没有 可以返回null
     *
     * **/
    public static final String KEY_function = "吾有一术名曰";
    /**方法參數**/
    public static final String KEY_function_pram = "其必有";
    /**方法开始**/
    public static final String KEY_function_begin = "宣";
    /**方法返回值**/
    public static final String KEY_function_return = "取其";
    /**方法执行**/
    public static final String KEY_function_run = "执其";
    /**方法执行结尾**/
    public static final String KEY_function_run_end = "乎";
    /**方法结束**/
    public static final String KEY_function_over = "者也";

//---------------------------字典-------------------------------

    /**
     * 字典
     *
     * //声明局部变量  全局变量吧 "小" 去掉
     * 吾有一小图名曰信息 宣 名:"孙悟空",年龄:"500" 也
     * Map<String,String> 信息 = new HashMap<>();
     *
     * 信息.put("名","孙悟空");
     * 信息.put("年龄","500");
     *
     *
     *
     *
     * //使用变量
     *
     * 令吾图信息增以 师弟:师弟,三师弟:"沙僧" 也
     * 信息.put("师弟",师弟);
     * 信息.put("三师弟","沙僧");
     *
     * 令吾图信息去以 三师弟 也
     * 信息.remove("三师弟");
     *
     * 令吾图信息取之"师弟"也
     * 信息.get("师弟");
     *
     * 吾宣二师弟乃令吾图信息取之"师弟"也;
     * var 二师弟 = 信息.get("师弟");
     *
     */
    public static final String KEY_data = "吾有一图名曰";
    /**开始输入 key**/
    public static final String KEY_data_begin = "宣";
    /**对图进行修改**/
    public static final String KEY_data_edit = "令吾图";
    /**增加 key**/
    public static final String KEY_data_add_new = "增以";
    /** get() **/
    public static final String KEY_data_add_get = "取之";
    /**删除 key**/
    public static final String KEY_data_remove_new = "去以";
    /**结束**/
    public static final String KEY_data_over = KEY_var_over;

//---------------------------分支结构------------------------------
    /**if 分支结构
     *
     * 判断条件为是不是null 如果是null则为false 反之为true
     *
     * 吾有一术名曰 取师弟 宣 不持 师弟 有之 吾宣师弟乃八戒也 则以 取其师弟者也
     *
     * function 取师弟(){
     *
     *     if(!师弟){
     *        var 师弟 = "八戒";
     *     }
     *
     *     return 师弟;
     * }
     *
     * 吾有一术名曰 取师弟 宣 持 二师弟 有之 吾宣师弟同于二师弟也 无之 吾宣师弟乃沙僧也 则以 取其师弟者也
     *
     * function 取师弟(){
     *
     *     if(二师弟){
     *        var 师弟 = 二师弟;
     *     }else{
     *        var 师弟 = "沙僧";
     *     }
     *
     *     return 师弟;
     * }
     *
     *
     * **/
    public static final String KEY_if = "持";
    /**如果不**/
    public static final String KEY_if_not = "不持";
    /** 如果是 **/
    public static final String KEY_if_true = "有之";
    /** 如果不是 **/
    public static final String KEY_if_false = "无之";
    /** 结束语 **/
    public static final String KEY_if_end = "则以";

//---------------------------while循环结构------------------------------
    /**while
     *
     * 判断条件为是不是null 如果是null则为false 反之为true
     * (到这里脑子都要炸了,不是想不到怎么编写程序,而是想不到用什么词...望能人改之)
     *
     * 维持 二师兄 宣 吾之二师兄乃"八戒"也 则以
     * while(二师兄){
     *     二师兄 = "八戒";
     * }
     *
     *
     * **/
    public static final String KEY_while = "维持";
    /**开始执行代码**/
    public static final String KEY_while_begin =  KEY_function_begin;
    /** 结束语 这里使用if的结束语 **/
    public static final String KEY_while_end = KEY_if_end;

//----------------------------for循环--------------------------------------
    /**开始执行代码
     *
     * 计次 "5" 宣 古人云"666啊"云云  则以
     *
     * for(int i = 0 ;i<5; i++){
     *     println("666啊");
     * }
     *
     * --------------------------------------
     * 计次 年龄 宣 古人云"666啊"云云  则以
     *
     * for(int i = 0 ;i<年龄; i++){
     *     println("666啊");
     * }
     * 如果年龄为null或者不是数字 则不循环
     * **/
    public static final String KEY_for = "计次" ;
    /**开始执行代码**/
    public static final String KEY_for_begin =  KEY_function_begin;
    /** 结束语 这里使用if的结束语 **/
    public static final String KEY_for_end = KEY_if_end;


//----------------------------内置封装函数--------------------------------
    /** 控制台输出
     * 调用的时候如果是字符串需要加引号
     * 古人云"666啊"云云
     * **/
    public static final String KEY_println = "古人云";
    /** 打印结束语 **/
    public static final String KEY_println_end = "云云";

    /** 合并字符串
     * 吾之信息乃 则其名与年龄合并之 也
     *
     * 信息 = 名 + 年龄;
     *
     * **/
    public static final String KEY_String_start = "则其";
    /** 合并字符串**/
    public static final String KEY_String_center = "与";
    /** 合并字符串 **/
    public static final String KEY_String_end = "合并之";

    /** 数字变量自增 **/
    public static final String KEY_math_begin = "自";
    /** 数字自增 **/
    public static final String KEY_math_jia = "增之";
    /** 数字自减 **/
    public static final String KEY_math_jian = "减之";

}
