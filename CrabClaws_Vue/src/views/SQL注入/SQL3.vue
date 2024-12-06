<template>
    <div class="SQL2">
        <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
            延时盲注
        <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
        </div>
        <el-container>
            <!-- 中间内容 -->
            <el-main class="main-container">
                <!-- 表格 -->
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="msg" label="msg" width="180"></el-table-column>
                </el-table>
                <!-- 文本框 -->
                <div class="input-button-container">
                    <el-input v-model="inputValue" placeholder="id=" style="width: 120px;"></el-input>
                    <el-button @click="fetchData" type="primary">查询</el-button>
                </div>
            </el-main>
        </el-container>

      <el-drawer :visible.sync="drawerVisible" direction="rtl" size="50%" title="Java代码示例">
        <div class="code-block">
          <h3>java源码</h3>
          <pre><code class="java">{{ javaCode }}</code></pre>
        </div>
      </el-drawer>        
    </div>
</template>

<script>
//import axios from 'axios';
import qs from 'qs'; // 引入qs库，用于处理表单数据格式转换
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';


export default {
data() {
    return {
      tableData: [],
      inputValue: '',
      drawerVisible: false,
      javaCode: `
@PostMapping(value="/SQL3")
public Result SQL_sleep(@RequestParam("id") String id){
    log.info("SQL注入参数：{}",id);
    try {
    User userid =  userService.SQL_injection(id);
    return Result.success();
    } catch (Exception e) {

    }
    return Result.error("SQL语句出错");
}

UserMapper.java:
@Select("select * from user where ID = '\${ID}'")
User SQL_injection(String ID);
      `,
    };
  },
    methods: {
        fetchData() {
            const id = this.inputValue;
            this.$axios.post('api/SQL3', qs.stringify({ id: id }), {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
           .then((result) => {
                this.tableData = [result.data];
            })
           .catch((error) => {
                console.error('获取数据出错：', error);
            });
        },
        highlightCode() {
      this.$nextTick(() => {
        const codeBlocks = document.querySelectorAll('pre code');
        codeBlocks.forEach((block) => {
          hljs.highlightBlock(block);
        });
      });
    }
    },
    watch: {
    drawerVisible(newVal) {
      if (newVal) {
        this.highlightCode(); // 当抽屉打开时进行高亮显示
      }
    }
  }
};
</script>

<style scoped>
.SQL3 {
    text-align: center;
}

.main-container {
    padding: 20px;
    background-color: #ffffff;
}

.input-container {
    margin-top: 20px;
}
.input-button-container {
    display: flex;
    align-items: center;
    margin-top: 20px;
}
.code-block {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f4f4f4;
  border-radius: 8px;
}

pre {
  white-space: pre-wrap;
  word-wrap: break-word;
}

.ct-button {
  width: 120px;
  height: 40px;
  float: right;
}

.code-block {
  margin-left: 0;
  color: black;
  font-size: 16px;
  text-align: left;
}
</style>