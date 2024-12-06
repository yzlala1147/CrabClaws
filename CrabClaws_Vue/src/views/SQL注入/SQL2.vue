<template>
    <div class="SQL2">
      <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
        布尔盲注
      <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
      </div>
        <el-container>
            <!-- 中间内容 -->
            <el-main class="main-container">
                <!-- 表格 -->
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="data" label="data" width="180"></el-table-column>
                </el-table>
                <!-- 文本框 -->
                <div class="input-button-container">
                    <el-input v-model="inputValue" placeholder="id=" style="width: 120px;"></el-input>
                    <el-button @click="fetchData" type="primary">查询</el-button>
                </div>
            </el-main>
        </el-container>
        <!-- Drawer 抽屉 -->
        <el-drawer :visible.sync="drawerVisible" direction="rtl" size="50%" title="Java代码示例">
          <div class="code-block">
            <h3>java源码</h3>
            <pre><code class="java">{{ javaCode }}</code></pre>
          </div>
        </el-drawer>
    </div>
  </template>
  
<script>
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';


export default {
data() {
    return {
      tableData: [],
      inputValue: '',
      drawerVisible: false,
      javaCode: `
@GetMapping(value="/SQL2")
    public Result SQL_blind(@RequestParam("id") String id){
        log.info("SQL注入参数：{}",id);
        try {
        String TandF = "false";
        User user = userService.SQL_injection(id);
        if(user != null){
           TandF = "true";
       }
       return Result.success(TandF);
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
            this.$axios.get(`api/SQL2?id=${id}`)
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
  .SQL1 {
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

/* Code block styling */
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
  /* 初始宽度，可以根据实际情况调整 */
  width: 120px;
  /* 初始高度，可以根据实际情况调整 */
  height: 40px;
  /* 向右对齐，使用float或者flex布局等方式都可以实现，这里使用float */
  float: right;
}

.code-block {
  /* 使用margin-left来让整个代码块向左偏移，这里设置为0，表示紧贴左边边界，你可以根据实际需求调整合适的数值 */
  margin-left: 0;
  color: black;
  font-size: 16px;
  text-align: left;
}
  </style>