//本页面用于给定题目要求
//包含数值范围、题目数量、运算符个数上限、乘除法、括号
<template>
    <el-container>
        <el-main>
            <el-card>
                <!-- 模块标题 -->
                <div slot="header" :model="title">
                    <h1>{{message}}</h1>
                </div>
                <!-- 给出出题限制 -->
                <!-- main用于固定主要中间位置 -->
                <div id="main">
                    <el-form v-model="questionLimi" label-width="120px">
                        <!-- left用于固定左边限制位置 -->
                        <div id="left">
                            <el-form-item label="数值范围">
                                <!-- controls-position="right"控制箭头位置 -->
                                <el-input-number v-model="questionLimi.min" :min="0" controls-position="right" @change="handleChangemin"></el-input-number>
                                <el-input-number v-model="questionLimi.max" :min="0" controls-position="right" @change="handleChangemax"></el-input-number>
                            </el-form-item>
                            <el-form-item label="题目数量">
                                <el-input v-model="questionLimi.queAccount" suffix-icon="el-icon-edit" placeholder="请输入题目数量"></el-input>
                            </el-form-item>
                            <el-form-item label="运算符上限">
                                <el-input-number v-model="questionLimi.opeAccount" :min="0" controls-position="right"></el-input-number>
                            </el-form-item>
                        </div>
                        <div id="right">
                            <el-form-item label="是否包含乘除法">
                                <el-switch v-model="questionLimi.complex" active-value="1" inactive-value="0" @change="complexChange"></el-switch>
                            </el-form-item>
                            <el-form-item label="是否包含括号">
                                <el-switch v-model="questionLimi.bracket" active-value="1" inactive-value="0" @change="bracketChange"></el-switch>
                            </el-form-item>
                        </div>
                        <div id="buttom">
                            <el-button type="primary">在线答题</el-button>
                            <el-button type="primary">出题</el-button>
                            
                        </div>
                    </el-form>
                </div>
                <div id="upLoad">
                    <el-upload
                        class="upload-demo"
                        ref="upload"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :file-list="fileList"
                        :auto-upload="false">
                        <el-button slot="trigger" size="small" type="primary">扩充题库</el-button>
                        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                    </el-upload>
                </div>
            </el-card>
        </el-main>
    </el-container>
</template>

<script>
export default {
    name: 'setQuestion',
    data(){
        return {
            //标题model
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
                division: '',
                //是否包含复杂运算（* /）
                complex: '',
                //是否包含括号
                brackets: '',
            },
        }
    },
    methods: {
        handleChangemin(value) {
            // x=value+1;
            console.log("最小值："+value);
            return x;
        },
        handleChangemax(value) {
            console.log("最大值："+value);
        },
        complexChange(value) {
            console.log("是否含乘除？"+value);
        },
        bracketChange(value) {
            console.log("是否含括号？"+value);
        },
        submitUpload() {
            this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        }
    },
}
</script>

<style>
    h1 {
        font-weight: normal;
    }
    #main {
        position: relative;
        /* border: 1px solid red; */
        width: 57%;
        margin: 0 auto;
    }
    #left {
        width: 72%;
        /* border: 1px solid black; */
    }
    #right {
        /* border: 1px solid yellow; */
        width: 25%;
        position: absolute;
        top: 17%;
        right: 0;
    }
    #upLoad {
        position: absolute;
        top: 19%;
        right: 2%;
        height: 70px;
        /* border: 1px solid red; */
    }
    .el-card {
        position: relative;
    }
</style>
