// 公共样式
<template>
  <div id="app">
    <el-container>
      <el-header height="65px">
        <router-link to="/hello" >
          <img src="./assets/logo2.png" width="40px" height="40px" />
          <h1>Math Helper</h1>
        </router-link>
        <p id="sign">{{msg1}}</p>
    </el-header>
    </el-container>
    <div class="hello">
      <!-- <img src="./assets/Login.jpg" width="700" height="550"> -->
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      msg1: '欢迎进入数学提分助手，让你悄悄变优秀',
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100%;
}
.hello {
  position: absolute;
  width: 100%;
  margin: 0 auto;
  opacity: 0.65;
  z-index: -1;
  /* border: 1px solid red; */
  margin-top: 2%;
}
.el-header {
  position: relative;
  line-height: 60px;
  text-align: left;
}
#sign {
  text-align: right;
  width: 40%;
  position: absolute;
  top: 0;
  right: 1.5%;
  border-bottom: 0.5px solid grey;
}
h1, h2 {
  font-weight: normal;
  display: inline-block;
}
img {
  /* margin-right: 0.5%; */
  vertical-align: middle;
}
</style>
