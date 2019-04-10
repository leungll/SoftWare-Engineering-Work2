//在本页面答题
//需求：计时器、题目排版、保存用户答案、给出得分
<template>
    <el-container>
        <el-main>
            <el-card>
                <div slot="header">
                    <h1>答题卡</h1>
                    <p>用时：{{str}}</p>
                </div>
                
                <!-- <p>请在规定的答题区域答题</p> -->
                <!-- 定义数组下标index -->
                <div>
                    <div class="answerBox" v-for="(item,index) in question" v-bind:key="item.answer">
                        <span class="questionText">{{item.Exercise}}</span>
                        <span class="correntAnwer">{{item.Answer}}</span>
                        <!-- 用v-model 来获得 输入的答案 data[index] -》 数组-->
                        <!-- 当input的值改变时 改变一个比较一个 比较一个统计一个 -->
                        <input type="text" class="answerInput" v-model="data[index]" v-on:change="account(item)"/>
                    </div>
                    <div>
                        <el-button type="primary" @click="start">开始答题</el-button>
                        <el-button type="primary" @click="stop">提交</el-button>
                        <!-- <el-button type="primary" @click="reset">放弃答题</el-button> -->
                    </div>
                </div>
            </el-card>
        </el-main>
    </el-container>
</template>

<script>
export default {
    name: 'answer',
    data() {
        return {
            question:[],
            answer:[],
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
        //页面载入时调用计时器开始
        mounted(){
            this.$$nextTick(function(){
                this.start();
            })
            console.log("hello!");
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
        },
        //比较答案和统计正确个数
        account(item){
            if(this.data[this.i]==item.Answer){
                this.correntNum+=1;
            }
            this.i+=1;
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
        //放弃答题
        // reset(){

        // }
    },
    //获得后台json数据，并渲染
    created() {
        this.$http({
            method: "post",
            url: "http://localhost:8080/toCreate",
            dataType: "json",
            crossDomain: true,
            cache: false
        }).then(resolve =>{
            // 请求成功，关闭loading
            this.tranform = false;
            // this.question=resolve.data.math;
            // this.answer=resolve.data.math;
            console.log("ok",resolve);
        },
        reject=>{
            console.log(reject);
        })
    },
}
</script>

<style scoped>
    
    #time {
        position: absolute;
        right: 0;
    }
    .answerBox {
        margin: 0 0 20px 0;
    }
    .questionText {
        display: inline-block;
        width: 150px;
        /* border: 1px solid red; */
        text-align: right;
    }
    .correntAnwer{
        display: none;
    }
</style>

