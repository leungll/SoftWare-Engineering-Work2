<template>
  <el-container style="height: 100%" direction="vertical">
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="180px">
        <!-- 一列 -->
        <el-row>
          <el-menu default-active="1" style="height: 450px">
            <el-menu-item index="1">
              <span>出题</span>
            </el-menu-item>
            <el-submenu index="2">
              <!-- slot="title" => 该元素的“地位”是标题 -->
              <template slot="title">练习</template>
              <el-menu-item index="2-1">在线答题</el-menu-item>
              <el-menu-item index="2-2">
                <a :href='"http://localhost:8080/download"' download="题集.txt">下载题集</a>
              </el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title">扩充题库</template>
              <!-- 上传 -->
              <el-menu-item index="3-1" style="padding-left: 20px;">
                <el-upload  name="fileName" 
                            ref="upload" 
                            action="http://localhost:8080/fileUpload" 
                            :auto-upload="false"
                            :on-success="uploadSuccess"
                            :on-error="uploadError">
                  <el-button slot="trigger" size="small" type="primary">选择文件</el-button>
                  <el-button size="small" type="success" @click="submitUpload();start()">上传</el-button>
                </el-upload>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-row>
      </el-aside>
      
      <!-- 中间 题目设置-->
      <el-main v-if="setQuestion" id="setBox">
        <!-- 设置题目范围 -->
        <div>
          <el-card id="setQuestion">
            <!-- 模块标题 -->
            <div slot="header" :model="title">
              <h1>{{message}}</h1>
            </div>
            <!-- 给出出题限制 -->
            <!-- main用于固定主要中间位置 -->
            <div id="main">
              <el-form :model="questionLimi" label-width="120px">
                <!-- left用于固定左边限制位置 -->
                <div id="left">
                  <el-form-item label="数值下界" prop="min">
                  <!-- controls-position="right"控制箭头位置 -->
                    <el-input-number v-model="questionLimi.min" :min="1" controls-position="right"></el-input-number>
                  </el-form-item>
                  <el-form-item label="数值上界" prop="max">
                    <el-input-number v-model="questionLimi.max" :min="1" controls-position="right"></el-input-number>
                  </el-form-item>
                  <el-form-item label="题目数量" prop="queAccount">
                    <el-input v-model="questionLimi.queAccount" suffix-icon="el-icon-edit" placeholder="请输入题目数量"></el-input>
                  </el-form-item>
                  <el-form-item label="运算符上限" prop="opeAccount">
                    <el-input-number v-model="questionLimi.opeAccount" :min="3" :max="5" controls-position="right"></el-input-number>
                  </el-form-item>
                </div>
                <div id="right">
                  <!-- $event传递当前switch的值 -->
                  <el-form-item label="是否包含乘除法" prop="multiANDdivi">
                    <el-switch v-model="questionLimi.multi" active-value="1" inactive-value="0" @change="mdChange($event)"></el-switch>
                  </el-form-item>
                  <el-form-item label="是否包含括号" prop="bracket">
                    <el-switch v-model="questionLimi.bracket" active-value="1" inactive-value="0" @change="braChange($event)"></el-switch>
                  </el-form-item>
                </div>
                <div id="buttom">
                  <el-button type="primary" @click="submitData(); start()">出题</el-button>
                </div>
              </el-form>
            </div>
          </el-card>
        </div>
      </el-main>
      <!-- 中间 答题 -->
      <!-- 用v-if来控制是否可见：v-if中的值为true=>可见 否则=>不可见 -->
      <el-main v-if="answer">
        <el-card>
          <div slot="header">
            <h1>答题卡</h1>
            <p>用时：{{str}}</p>
          </div>
                
          <!-- <p>请在规定的答题区域答题</p> -->
          <!-- 定义数组下标index -->
          <div>
            <div class="answerBox" v-for="(item,index) in question" v-bind:key="item.index">
              <span class="questionText">{{item.Exercise}}=</span>
              <span class="correntAnwer">{{calculate(item.Exercise)}}</span>
              <!-- 用v-model 来获得 输入的答案 data[index] -》 数组-->
              <!-- 当input的值改变时 改变一个比较一个 比较一个统计一个 -->
              <input type="text" class="answerInput" v-model="data[index]" v-on:change="account(item,index)"/>
            </div>
            <div>
              <el-button type="primary" @click="stop">提交</el-button>
              <!-- <el-button type="primary" @click="reset">放弃答题</el-button> -->
            </div>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      // 题目限制
      //标题modle
      setQuestion: true,
      title: '',
      // 设置卡片标题
      message: '给出题目要求',
      //出题限制表单
      questionLimi: {
        // 给定数字范围
        min: '',
        max: '',
        queAccount: '', 
        opeAccount: '',
        //乘除法
        multiANDdivi: '',
        //是否包含括号
        brackets: '',
      },
      //代替multiANDdivi brackets传值
      MD: 2,
      Brac: 1,

      //答题页面
      answer: false,
      question:[],
      // answer:[],
      tranform: false,
      //计时器
      //定义时分秒
      h: 0,
      m: 0,
      s: 0,
      time: 0,
      str: '00:00:00',
      answerInput: '',
      data:[],
      models:[],
      //i用于for循环
      //correntNum用于统计正确数
      correntNum:0,
      i:0,
      rate:0,
    }
  },
  methods: {
    //上传文件
    submitUpload(){
      this.$refs.upload.submit();
    },
    uploadSuccess(response, file, fileList){
      console.log(response);
      //打开answer界面
      this.answer = true;
      //关闭setQuestion界面
      this.setQuestion = false;
      this.question = response;
    },
    uploadError(response, file, fileList){
      console.log("失败");
    },
    //改变MD =>2无乘除 4有乘除
    mdChange(mdSwitch){
      if(mdSwitch == 1){
        this.MD = 4;
      }
      else if(mdSwitch == 0){
        this.MD = 2;
      }
      console.log(this.MD);
    },
    //改变Brac =>0有括号 1无括号
    braChange(braSwitch){
      if(braSwitch == 1){
        this.Brac = 0;
      }
      else if(braSwitch == 0){
        this.Brac = 1;
      }
    },
    //传值给后端
    submitData() {
      this.$http({
        method: "post",
        // method: "get",
        // url: "../../static/questions.json",
        url: "http://localhost:8080/toCreate",
        data: {
          //算式总数
          _n: this.questionLimi.queAccount,
          _min: this.questionLimi.min,
          _max: this.questionLimi.max,
          _maxOper: this.questionLimi.opeAccount,
          _is_Bracket: this.Brac,
          _is_Mul: this.MD,
        },
        // 加"transformRequest"属性对请求数据进行格式化
        transformRequest(obj){
          var str = [];
          for(var p in obj){
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
          }
          return str.join("&");
        },
        // dataType: "json",
        crossDomain: true,
        cache: false,
      }).then(res => {
        //打开answer界面
        this.answer = true;
        //关闭setQuestion界面
        this.setQuestion = false;
        this.question = res.data;
        // this.question = res.data.math;
        console.log(this.question);
      },error => {
        console.log("失败");
      })
    },
    //计算式子的值
    calculate(data){
      // //无乘除
      // if(this.MD == 2){
      //   return eval(data);
      // }
      // //有乘除
      // else if(this.MD == 4){
      //   //调用replace方法将字符中的÷替换成/
      var str = data.replace(/÷/g,"/");
      return eval(str);
      // }
    },
    timer(){
      //秒针走动
      this.s=this.s+1;
      //秒进位分，秒归零
      if(this.s>=60){
        this.m=this.m+1;
        this.s=0;
      }
      //分进位时，分归零
      if(this.m>=60){
        this.h=this.h+1;
        this.m=0;
      }
      this.str=this.addZero(this.h)+":"+this.addZero(this.m)+":"+this.addZero(this.s);
    },
    //开始计时
    start(){
      //1秒=1000毫秒
      this.time=setInterval(this.timer,1000);
    },
    //提交答卷 时间停止 出成绩
    stop(data){
      clearInterval(this.time);
      console.log(this.str);
      //计算成绩
      this.rate=(this.correntNum/this.question.length)*100;
      alert("你的正确率为："+this.rate+"%"+"       "+"用时："+this.str);
      // 答题页面关闭，出题页面打开
      this.answer = false;
      this.setQuestion = true;
    },
    //比较答案和统计正确个数
    account(item,index){
      if(this.data[index]==this.calculate(item.Exercise)){
        this.correntNum+=1;
      }
      console.log(this.correntNum);
    },
    //补零
    addZero(t){
      if(t<10){
        return ("0"+t);
      }
      else{
        return t;
      }
    },
  },
}
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* 侧边栏 */
.el-aside {
  text-align: left;
  /* border: 1px solid red; */
}
/* 出题要求+答题卡 */
#setBox {
  padding-top: 4%;
}
h1{
  font-weight: normal;
  display: inline-block;
}
img {
  /* margin-right: 0.5%; */
  vertical-align: middle;
}
a {
  color: #409EFF;
}
/* min-width 修改el-menu-item的长度 使之与主导航长度相等 避免产生滚动条 */
.el-menu-item {
  padding: 0;
  min-width: 179px;
}
/* 出题要求 box */
#setQuestion {
  position: relative;
  width: 80%;
  margin: 0 auto;
}
#main {
  position: relative;
  /* border: 1px solid red; */
  width: 70%;
  margin: 0 auto;
}
#left {
  width: 55%;
  /* border: 1px solid black; */
}
#right {
  /* border: 1px solid yellow; */
  width: 35%;
  position: absolute;
  top: 17%;
  right: 5%;
}
/* 上传 */
#upLoad {
  position: absolute;
  top: 19%;
  right: 2%;
  height: 70px;
  border: 1px solid red;
}

/* 答题区域 */
#time {
  position: absolute;
  right: 0;
}
.answerBox {
  display: inline-block;
  /* border: 1px solid red; */
  width: 29%;
  text-align: right;
  margin: 0 4px 20px 4px;
}
.questionText {
  display: inline-block;
}
.correntAnwer {
  /* display: none; */
}
.answerInput {
  width: 60px;
}
</style>
