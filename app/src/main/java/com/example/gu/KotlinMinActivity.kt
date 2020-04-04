package com.example.gu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
/**
 * 初次尝试kotlin开发安卓
 * created by Gu at 2020/02/06
 */
class KotlinMinActivity : AppCompatActivity() {

    // var定义变量, val定义常量
    // val对象懒加载机制
    private val textView: TextView by lazy {
        findViewById(R.id.tv_kotlin) as TextView
    }
    // var对象懒加载机制
    lateinit var text: String

    // ? 可以为空(必须声明数据类型) !!不可以为空
    var num: Int? = null
    //空指针的处理
    val v1 = num?.toInt()   //不做处理返回 null
    val v2 = num?.toInt() ?: 0   //判断为空时返回0
    val v3 = num!!.toInt()  //抛出空指针异常
    //is取代了instance of
    // .. 类似python range() 前后都包括 step指定步长
    //when取代了switch ->
    //所有类的基类Any
    //定义数据类Model用data修饰，自动实现getter/setter equals(), hashCode(), copy()
    //companion object(伴随对象) 静态属性或方法，由该类所有对象共享
    //单例 object 输入singleton自动生成
    //为已存在的类扩展方法和属性，无法用扩展去覆盖已存在的方法
    fun AppCompatActivity.showLongToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    //类有1个主构造函数和N个二级构造函数，二级构造函数必须直接或间接代理主构造函数
    //提供初始化模块init

    //函数式编程，没有可变变量，一个变量一旦被赋值，就不可更改。
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_min)

        text = "hhh,I am your bf!"
        textView.text = text
    }
}
