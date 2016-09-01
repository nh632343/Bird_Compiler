# Bird-Compiler
<br>这是自己写的一个编译器,使用java.
##如何运行
用eclipse导入项目,找到analyse包,运行BasicParser.java文件,在输入框中输入程序,按确定运行程序.
##简介
##常量
包括两种类型:
<br>整形:如123   0   -46
<br>字符:如"abce0,."    "\""    (与java相同)
##变量
不需要声明类型
<br>变量名只能由字母,数字,下划线组成,且必须以字母或下划线开头
<br>变量如果能查找到,则直接操作变量;如果查找不到,则新建一个变量
##运算符
支持:   =   赋值
<br>+   -   *   /   四则运算符号
<br>%   求余
<br>>   <   ==    >=    <=    比较符号,若为真返回1,为假返回0
##语句
可以以换行分隔,也可以以；分隔
##if语句
格式:if   条件    {
<br> //程序
<br> }
例子:<br>
```
if a>=20 {
  b=1
  a=a+10
  c=3
}
```
注意:条件不需要括号括起来,后面的大括号一定要

##while语句
格式:while    条件    {
<br>  //程序
<br> }
<br>例子:<br>
```
while c>0 {
   a=a+c
}
```
##定义方法
格式:def    方法名称(形参)    {
<br>  //程序
<br>   }
<br> 方法的返回值为,最后一条语句的执行结果
<br> 方法执行时会创建一个局部环境,局部环境在开始时存放了参数.   查找变量时,先在局部环境中寻找,找不到则到外部环境中找,再找不到就会在局部环境中创建变量
<br> 如果你想定义一个局部变量,又担心与外部变量冲突时,可以在定义时,变量名前加上"@",这样会在内部环境创建变量
<br> 例如:<br>
```
@money=10
```
<br><br>例子:<br>
```
salary=0
def add(x){
   @temp=x%2
   if temp==0{
      salary=salary+x
   }
   temp
}
c=add(10)
```
上面的例子中,传入一个x,对x求余并把结果赋给局部变量temp,如果temp为0,则外部变量salary加上x,最后返回temp的值
<br>也支持闭包
<br>在BasicParser.java中,静态常量bibao控制闭包功能,true打开,false关闭
<br>例子:<br>
```
def A(){
   count=0
   def B(x){
      count=count+x
   }
   B
}

c=A()
c(2)
c(3)
```
例子中,A方法中定义了B方法,最后返回B方法
<br>c接收了B方法的一个实例,可以调用B方法
##java静态方法
在BasicParser.java中,静态常量nativeFunction控制java方法功能,true打开,false关闭
<br>使用了反射机制来调用java静态方法
<br>在nativefunc包,NativeFunctionList.java文件中可编辑支持的方法
<br>程序中直接调用即可
<br>例子:<br>
```
money=100
print(money)
```
##类
在BasicParser.java中,静态常量classFunction控制类功能,true打开,false关闭
###类的定义:
格式:class    类名    {
<br>    //简单语句或def或class
<br> }
###实例化对象
类名.new    返回一个该类的对象
<br>创建对象时,会把类定义的大括号里的程序,按顺序执行一遍.
<br>查找变量时,先内部找,找不到在外部环境中找,找不到就创建内部变量
<br>若想定义内部变量,可以在定义前加"@"
###对象操作
对象.变量名
<br>该对象的内部变量
<br>对象.方法名(参数)
<br>调用对象的内部方法
###继承
格式:class    类名    extends   父类名    {
<br>    //简单语句或def或class
<br> }
<br>在实例化对象时,会先实例化父类的对象,再实例化子类的对象,而且子类对象的外部环境为父类对象
<br>子类的方法可以访问父类对象的内部变量,内部方法
<br>子类对象可以访问父类对象的内部变量
<br>重写:子类的方法名与父类重复的,使用子类的方法
<br>例子:<br>
```
class A {
   @x=1
   def add(a){
     x=x+a
  }
}

class B extends A{
    @y=2
    def all(){
       y=y+x
    }
    def add(a,b){
       x=x+a+b
    }
}

b=B.new
print(b.x)
b.add(10)  //错误,子类的add方法覆盖了父类的add方法,只能使用子类的add方法
b.add(10,20)
b.all()
print(b.y)
```
###内部类
例子:<br>
```
class A{
    @y=1
    class B{
       y=y+1
       
    }
}
```
##说明
本编译器的词法分割使用正则表达式,语法分析使用LL(1)
<br>各包介绍:
<br>basic:分析器文件
<br>node:基本节点类,环境类
<br>analyse:拓展节点类
<br>analyse_function:方法功能的节点类
<br>analyse_class:类功能的节点类
<br>nativefunc:java方法功能
