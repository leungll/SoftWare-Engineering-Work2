<template>
  <!-- 设置背景图片 -->
  <!-- <div class="background" :style="background"> -->
    <div class="hello">
      <img src="../assets/数学题.jpg" width="200px" height="200px">
      <h1>{{ msg }}</h1>
      <h2>开始你的智慧之旅</h2>
      <ul>
        <li>
          <a
            href="#"
            @click="loginShow=true"
          >
            我要登陆
          </a>
        </li>
        <li>
          <a
            href="#"
            @click="registShow=true"
          >
            我要注册
          </a>
        </li>
        <li>
          <a
            href="#"
            title="请先登录！"
            @click="loginShow=true"
          >
            我要做题
          </a>
        </li>
        <li>
          <a
            href="#"
            title="请先登录！"
            @click="loginShow=true"
          >
            我要出题
          </a>
        </li>
        <!-- 上传题库 -->
        <!-- <li> -->
          <!-- <el-upload class="upload-demo" action="#" multiple="false">
            <el-button size="small" type="primary">我要扩充题库</el-button>
          </el-upload> -->
        <!-- </li> -->
        <br>
      </ul>
      
      <!-- 登陆框 -->
      <el-dialog title="登录" :visible.sync="loginShow" :close-on-click-modal="false">
        <!-- rules表单验证 -->
        <el-form :model="login" :rules="loginRules" ref="login">
          <div class="middle">
            <el-form-item prop="Sid">
              <el-input v-model="login.Sid" type="text" placeholder="请输入学号或教师工号" suffix-icon="el-icon-edit"></el-input>
            </el-form-item>
            <el-form-item prop="psw">
              <el-input v-model="login.psw" type="password" placeholder="请输入密码"></el-input>
            </el-form-item>
          </div>
        </el-form>
        <div slot="footer">
          <a href="#" @click="loginShow=false; registShow=true">还没有账号？去注册</a>
          <el-button type="primary" @click="Sure('login')">登录</el-button>
          <el-button @click="loginShow=false; resetForm('login')">取消</el-button>
        </div>
      </el-dialog>

      <!-- 注册框 -->
      <el-dialog title="注册" :visible.sync="registShow" :close-on-click-modal="false">
        <!-- regist表单验证 -->
        <el-form :model="regist" :rules="registRules" ref="regist">
          <div class="middle">
            <el-form-item prop="Sid">
              <el-input v-model="regist.Sid" type="text" placeholder="请输入学号或教师工号" suffix-icon="el-icon-edit"></el-input>
            </el-form-item>
            <el-form-item prop="psw">
              <el-input v-model="regist.psw" type="password" placeholder="请输入密码"></el-input>
            </el-form-item>
          </div>
        </el-form>
        <div slot="footer">
          <a href="#" @click="registShow=false;loginShow=true">已注册？去登录</a>
          <el-button type="primary" @click="Sure">立即注册</el-button>
          <!-- 把regist表格当作参数传到reset函数中 -->
          <el-button type="primary" @click="resetForm('regist')">重置</el-button>
          <el-button @click="registShow=false; resetForm('regist')">取消</el-button>
        </div>
      </el-dialog>
    </div>
  <!-- </div> -->
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      //设置背景图片
      background: {
         backgroundImage: "url(" + require("../assets/数学题.jpg") + ")",
         backgroundSize: 'cover',
      },
      
      msg: '欢迎进入你的数学题分助手',
      
      //登录框
      loginShow: false,
      login: {
        Sid: '',
        psw: '',
      },
      //登录表单验证
      loginRules: {
        Sid: [
          //required：必填项
          {required:true, message:'请输入学号或教师工号！', trigger:'blur'},
        ],
        psw: [
          {required:true, message:'请输入密码！',trigger:'blur'}
        ],
      },
      //注册框
      registShow: false,
      regist: {
        Sid: '',
        pws: '',
      },
      //注册表单验证
      registRules: {
        Sid: [
          //required：必填项
          {required:true, message:'请输入学号或教师工号！', trigger:'blur'},
          {min:10, max:15, message:'长度为10到15个字符！', trigger: 'blur'}
        ],
        psw: [
          {required:true, message:'请输入密码！',trigger:'blur'},
          {min:8, message:'密码不能少于8位！',trigger:'blur'}
        ],
      },
    }
  },
  methods: {
    //判空+页面跳转
    Sure(formName){
      //登录
      this.$refs['login'].validate((valid) =>{
        //通过表单验证
        if(valid){
          this.$router.push('/login');
        }else{
          console.log('表单未填写完整');
          //阻止跳转
          return false;
        }
        //注册
        this.$refs['regist'].validate((valid) =>{
          if(valid){
            this.$router.push('/login');
          }else{
            console.log('表单未填写完整');
            //阻止跳转
            return false;
          }
        })
      })
    },
    //表单重置
    resetForm(formName){
      this.$refs['regist'].resetFields();
      this.$refs['login'].resetFields();
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* .hello {
  filter:alpha(Opacity=85);
  -moz-opacity:0.85;
  opacity: 0.85;
} */
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #409EFF;
}
.middle {
  width: 90%;
  margin: 0 auto;
}
.hello {
  margin-top: 60px;
}
</style>
