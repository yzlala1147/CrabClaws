<template>
    <div class="fast_json">
        <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
            fastjson反序列化
            <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
        </div>
        <el-container>
            <!-- 中间内容 -->
            <el-main class="main-container">
                <!-- 表格 -->
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="msg" label="msg" width="180"></el-table-column>
                    <el-table-column prop="data" label="data" width="180"></el-table-column>
                </el-table>

                <!-- 文本框 -->
                <div class="input-button-container">
                    <el-input v-model="inputValue" placeholder="输入payload" style="width: 120px;"></el-input>
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
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';

export default {
    data() {
        return {
            tableData: [],
            inputValue: '',
      drawerVisible: false,
      javaCode: `
      static {
        //开启autoType
        System.setProperty("fastjson.parser.autoTypeSupport", "true");
      } 

    @PostMapping("/fastjson")
    public Result fastjson_vul(@RequestBody String content) {
        try {
            String fastjosn = JSONObject.parseObject(content).toString();
            return Result.success(fastjosn);
       } catch (Exception e) {
           return Result.error(e.toString());
       }
    }
      `,
    };
  },
    methods: {
        fetchData() {
            const payloadData = { payload: this.inputValue };
            this.$axios.post('api/serialize/fastjson', payloadData,)
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